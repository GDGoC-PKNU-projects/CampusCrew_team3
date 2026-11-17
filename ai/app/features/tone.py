"""톤 변환 기능: 프롬프트 구성 -> LLM 호출 -> 응답 검증."""
from pydantic import ValidationError

from .. import gemini_client
from ..errors import invalid_json
from ..prompts import tone as tone_prompt
from ..schemas import ToneRequest, ToneResponse


def run(req: ToneRequest) -> ToneResponse:
    raw = gemini_client.generate_json(
        system_instruction=tone_prompt.SYSTEM_INSTRUCTION,
        user_content=tone_prompt.build_user_content(req.content, req.tone),
        response_schema=ToneResponse,
    )
    try:
        result = ToneResponse.model_validate_json(raw)
    except ValidationError as exc:
        raise invalid_json() from exc

    if not result.content.strip():
        raise invalid_json()
    return result
