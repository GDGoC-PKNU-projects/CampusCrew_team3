"""환경 설정. 민감 정보(GEMINI_API_KEY)는 환경 변수로만 주입한다."""
from functools import lru_cache

from pydantic_settings import BaseSettings, SettingsConfigDict


class Settings(BaseSettings):
    model_config = SettingsConfigDict(env_file=".env", extra="ignore")

    # 외부 LLM 모델은 gemini-2.5-flash-lite로 고정 (AI_SPEC §3).
    gemini_api_key: str = ""
    model: str = "gemini-2.5-flash-lite"

    # LLM 호출 타임아웃(ms)과 빈응답/JSON오류 재시도 횟수.
    request_timeout_ms: int = 20000
    max_retries: int = 1


@lru_cache
def get_settings() -> Settings:
    return Settings()
