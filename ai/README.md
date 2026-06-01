# CampusCrew AI 서버

CampusCrew의 AI 기능을 처리하는 **내부 전용** 프로세스. 외부에 노출하지 않고, Spring 백엔드가 Docker 네트워크 안에서만 호출한다.

- 모델: `gemini-2.5-flash-lite` (고정)
- 프레임워크: Python + FastAPI
- 구조/계약: [docs/](./docs/) 참고

## 구현 범위 (STEP3까지)

| 기능 | 내부 엔드포인트 | 상태 |
| --- | --- | --- |
| 톤 변환 | `POST /internal/documents/tone` | 구현됨 |
| 액션 아이템 추출 | `POST /internal/meeting-notes/action-items` | 구현됨 |
| 진행 상황 요약 | `POST /internal/teams/progress-summary` | STEP4 |
| 팀 문서 Q&A (RAG) | `POST /internal/teams/qna` | STEP4 |

## 폴더 구조

```
ai/
  docs/      설계 문서 6종 (IO_CONTRACTS, PROMPTS, FAILURE_HANDLING, RAG_DESIGN, EVALUATION, INTERNAL_API)
  app/
    main.py          FastAPI 앱 + 라우트
    config.py        환경 설정
    gemini_client.py Gemini 호출 래퍼 (structured output + 재시도)
    schemas.py       요청/응답 Pydantic 모델
    errors.py        AI 실패 타입 + 예외 핸들러
    prompts/         기능별 프롬프트 (역할/입력/출력/금지)
    features/        기능별 처리 (구성 -> 호출 -> 검증)
  tests/     pytest (Gemini mock)
```

## 로컬 실행

```bash
cd ai
python -m venv .venv && source .venv/bin/activate   # Windows: .venv\Scripts\activate
pip install -r requirements.txt
cp .env.example .env   # GEMINI_API_KEY 채우기
uvicorn app.main:app --reload
```

- 헬스체크: `GET http://localhost:8000/health` → `{"status":"ok"}`

### 호출 예시

```bash
# 톤 변환
curl -X POST http://localhost:8000/internal/documents/tone \
  -H "Content-Type: application/json" \
  -d '{"teamId":3,"type":"NOTICE","content":"내일 3시 회의함","tone":"FORMAL"}'

# 액션 아이템 추출
curl -X POST http://localhost:8000/internal/meeting-notes/action-items \
  -H "Content-Type: application/json" \
  -d '{"teamId":3,"content":"로그인 API는 백엔드A가 검증하기로 했다"}'
```

## 테스트

```bash
cd ai
pytest
```

Gemini 호출은 mock 처리되므로 API 키 없이 실행된다.

## 배포

Docker 컨테이너로 단일 `e2-standard-2` 인스턴스에 백엔드와 함께 올린다. 내부 포트(8000)는 외부에 열지 않는다. 자세한 경계는 [docs/INTERNAL_API.md](./docs/INTERNAL_API.md), 배포 기준은 `doc/common/DEPLOYMENT.md`.
