"""AI 실패 타입과 FastAPI 예외 핸들러. 코드 목록은 docs/FAILURE_HANDLING.md 기준."""
from fastapi import Request
from fastapi.responses import JSONResponse


class AIError(Exception):
    """AI 처리 실패. status_code와 code로 구조화 에러를 반환한다."""

    def __init__(self, code: str, message: str, status_code: int = 422):
        self.code = code
        self.message = message
        self.status_code = status_code
        super().__init__(message)


# docs/FAILURE_HANDLING.md 의 code 목록과 1:1.
def empty_response() -> AIError:
    return AIError("EMPTY_RESPONSE", "AI 응답을 받지 못했습니다. 다시 시도해주세요.")


def invalid_json() -> AIError:
    return AIError("INVALID_JSON", "AI 응답 형식이 올바르지 않습니다. 다시 시도해주세요.")


def empty_title() -> AIError:
    return AIError("EMPTY_TITLE", "추출된 할 일이 없습니다.")


def no_sources() -> AIError:
    return AIError("NO_SOURCES", "관련 문서를 찾지 못했습니다.")


def llm_timeout() -> AIError:
    return AIError("LLM_TIMEOUT", "AI 응답이 지연됩니다. 잠시 후 다시 시도해주세요.")


async def ai_error_handler(_: Request, exc: AIError) -> JSONResponse:
    return JSONResponse(
        status_code=exc.status_code,
        content={"error": {"code": exc.code, "message": exc.message}},
    )
