from fastapi.testclient import TestClient

from app import gemini_client
from app.errors import empty_response
from app.main import app

client = TestClient(app)


def test_tone_success(monkeypatch):
    monkeypatch.setattr(
        gemini_client,
        "generate_json",
        lambda **kw: '{"content": "내일 오후 3시에 회의를 진행합니다."}',
    )
    res = client.post(
        "/internal/documents/tone",
        json={"teamId": 3, "type": "NOTICE", "content": "내일 3시 회의함", "tone": "FORMAL"},
    )
    assert res.status_code == 200
    assert res.json() == {"content": "내일 오후 3시에 회의를 진행합니다."}


def test_tone_invalid_enum():
    res = client.post(
        "/internal/documents/tone",
        json={"teamId": 3, "type": "NOTICE", "content": "x", "tone": "LOUD"},
    )
    assert res.status_code == 422  # 요청 검증 단계에서 거절


def test_tone_invalid_json_from_model(monkeypatch):
    monkeypatch.setattr(gemini_client, "generate_json", lambda **kw: "not json at all")
    res = client.post(
        "/internal/documents/tone",
        json={"teamId": 3, "type": "NOTICE", "content": "x", "tone": "SHORT"},
    )
    assert res.status_code == 422
    assert res.json()["error"]["code"] == "INVALID_JSON"


def test_tone_empty_response(monkeypatch):
    def _raise(**kw):
        raise empty_response()

    monkeypatch.setattr(gemini_client, "generate_json", _raise)
    res = client.post(
        "/internal/documents/tone",
        json={"teamId": 3, "type": "NOTICE", "content": "x", "tone": "PLAIN"},
    )
    assert res.status_code == 422
    assert res.json()["error"]["code"] == "EMPTY_RESPONSE"
