"""AI 서버 진입점. 내부 전용 API (docs/INTERNAL_API.md).

외부 비노출. Spring 백엔드가 Docker 네트워크 내부에서만 호출한다.
응답은 data payload만 반환하고, 성공 envelope는 Spring이 붙인다.
"""
from fastapi import FastAPI

from .errors import AIError, ai_error_handler
from .features import action_items as action_items_feature
from .features import tone as tone_feature
from .schemas import (
    ActionItemsRequest,
    ActionItemsResponse,
    ToneRequest,
    ToneResponse,
)

app = FastAPI(title="CampusCrew AI Server", docs_url=None, redoc_url=None)
app.add_exception_handler(AIError, ai_error_handler)


@app.get("/health")
def health() -> dict:
    return {"status": "ok"}


@app.post("/internal/meeting-notes/action-items", response_model=ActionItemsResponse)
def extract_action_items(req: ActionItemsRequest) -> ActionItemsResponse:
    return action_items_feature.run(req)


@app.post("/internal/documents/tone", response_model=ToneResponse)
def convert_tone(req: ToneRequest) -> ToneResponse:
    return tone_feature.run(req)
