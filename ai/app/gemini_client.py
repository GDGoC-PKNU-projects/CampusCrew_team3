"""Gemini 호출 래퍼.

- structured output(response_mime_type=application/json + response_schema)으로 형식 강제.
- 빈 응답/예외는 1회 재시도, 그래도 비면 AIError(EMPTY_RESPONSE).
- 타임아웃은 AIError(LLM_TIMEOUT).
- 반환은 원문 텍스트(JSON 문자열). 스키마 검증은 feature 계층이 Pydantic으로 수행한다.

테스트는 generate_json 을 monkeypatch 한다 (실 API 키 불필요).
"""
from typing import Any, Optional

from google import genai
from google.genai import types

from .config import get_settings
from .errors import empty_response, llm_timeout

_client: Optional[genai.Client] = None


def _get_client() -> genai.Client:
    global _client
    if _client is None:
        settings = get_settings()
        _client = genai.Client(
            api_key=settings.gemini_api_key,
            http_options=types.HttpOptions(timeout=settings.request_timeout_ms),
        )
    return _client


def generate_json(
    system_instruction: str,
    user_content: str,
    response_schema: Optional[Any] = None,
) -> str:
    """LLM을 호출해 JSON 문자열을 돌려준다."""
    settings = get_settings()
    config = types.GenerateContentConfig(
        system_instruction=system_instruction,
        response_mime_type="application/json",
        response_schema=response_schema,
    )

    last_text = ""
    for attempt in range(settings.max_retries + 1):
        try:
            response = _get_client().models.generate_content(
                model=settings.model,
                contents=user_content,
                config=config,
            )
        except TimeoutError as exc:
            raise llm_timeout() from exc

        last_text = (response.text or "").strip()
        if last_text:
            return last_text

    # 재시도해도 빈 응답.
    raise empty_response()
