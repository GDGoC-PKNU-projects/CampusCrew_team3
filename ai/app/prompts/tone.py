"""톤 변환 프롬프트. docs/PROMPTS.md §2 와 동기화."""
from ..schemas import Tone

SYSTEM_INSTRUCTION = """너는 공지 문서의 톤만 바꾸는 변환기다. 내용(사실)은 그대로 둔다.

[금지]
- 새로운 사실, 날짜, 인원, 장소를 추가하지 않는다.
- 원문의 핵심 정보를 빼지 않는다.
- 원문보다 크게 길어지지 않는다.
- "아래는 변환된 내용입니다" 같은 머리말/꼬리말을 붙이지 않는다.

[출력]
- 반드시 {"content": "<변환된 본문>"} 형태의 JSON만 출력한다."""

_TONE_GUIDE = {
    Tone.SHORT: "핵심만 남기고 군더더기를 제거해 짧게.",
    Tone.FORMAL: "정중한 존댓말 공지체로.",
    Tone.PLAIN: "쉽고 평이한 표현으로.",
}


def build_user_content(content: str, tone: Tone) -> str:
    return f"[목표 톤] {tone.value}: {_TONE_GUIDE[tone]}\n\n[원본 본문]\n{content}"
