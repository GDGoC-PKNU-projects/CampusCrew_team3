"""액션 아이템 추출 기능: 프롬프트 구성 -> LLM 호출 -> 검증 -> title 정리."""
from pydantic import ValidationError

from .. import gemini_client
from ..errors import empty_title, invalid_json
from ..prompts import action_items as action_items_prompt
from ..schemas import ActionItemsRequest, ActionItemsResponse


def run(req: ActionItemsRequest) -> ActionItemsResponse:
    raw = gemini_client.generate_json(
        system_instruction=action_items_prompt.SYSTEM_INSTRUCTION,
        user_content=action_items_prompt.build_user_content(req.content),
        response_schema=ActionItemsResponse,
    )
    try:
        result = ActionItemsResponse.model_validate_json(raw)
    except ValidationError as exc:
        raise invalid_json() from exc

    # title 이 빈 항목은 제거 (schema validator가 빈 title을 None으로 강등).
    result.items = [item for item in result.items if item.title]

    # 추출 결과가 있었는데 전부 title 누락으로 사라지면 실패 처리.
    if not result.items:
        raise empty_title()
    return result
