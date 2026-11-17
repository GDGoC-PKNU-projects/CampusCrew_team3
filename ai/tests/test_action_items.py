from fastapi.testclient import TestClient

from app import gemini_client
from app.main import app

client = TestClient(app)


def _post(content="회의 내용"):
    return client.post(
        "/internal/meeting-notes/action-items",
        json={"teamId": 3, "content": content},
    )


def test_action_items_success(monkeypatch):
    monkeypatch.setattr(
        gemini_client,
        "generate_json",
        lambda **kw: '{"items": [{"title": "로그인 API 검증", "assigneeHint": "백엔드A", "dueDateHint": "2026-04-10"}]}',
    )
    res = _post()
    assert res.status_code == 200
    item = res.json()["items"][0]
    assert item["title"] == "로그인 API 검증"
    assert item["dueDateHint"] == "2026-04-10"


def test_action_items_due_date_normalized_to_null(monkeypatch):
    # 날짜로 환산 불가능한 값은 null 로 강등.
    monkeypatch.setattr(
        gemini_client,
        "generate_json",
        lambda **kw: '{"items": [{"title": "디자인 논의", "assigneeHint": null, "dueDateHint": "다음 주"}]}',
    )
    res = _post()
    assert res.status_code == 200
    item = res.json()["items"][0]
    assert item["dueDateHint"] is None
    assert item["assigneeHint"] is None


def test_action_items_empty_title_fails(monkeypatch):
    monkeypatch.setattr(
        gemini_client,
        "generate_json",
        lambda **kw: '{"items": [{"title": "  ", "assigneeHint": null, "dueDateHint": null}]}',
    )
    res = _post()
    assert res.status_code == 422
    assert res.json()["error"]["code"] == "EMPTY_TITLE"


def test_action_items_invalid_json(monkeypatch):
    monkeypatch.setattr(gemini_client, "generate_json", lambda **kw: "{broken")
    res = _post()
    assert res.status_code == 422
    assert res.json()["error"]["code"] == "INVALID_JSON"
