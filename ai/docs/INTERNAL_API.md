# 내부 API 계약 (INTERNAL_API)

Spring 백엔드 ↔ AI 서버 프로세스 간 내부 요청 스키마. BE-A 협의용. 근거: `doc/common/DEPLOYMENT.md` §4, §8 / `doc/ai/AI_SPEC.md` §4.

## 경계 구조

```
FE → Spring /api/ai/*  (인증 + 팀 권한 검증 + ApiResponse 래핑)
        → (Docker network 내부 호출) → AI 서버 /internal/*  (인증 없음, 외부 비노출)
            → Gemini gemini-2.5-flash-lite / (STEP4) pgvector RAG
```

- AI 서버는 외부 노출 안 함. 같은 Docker 네트워크 안에서 백엔드만 호출.
- AI 서버는 **data payload만** 반환. 성공 envelope(`success/message`)는 Spring이 붙임.
- AI 서버 실패는 구조화 에러(`{error:{code,message}}`)로 반환. 코드 목록은 [FAILURE_HANDLING.md](./FAILURE_HANDLING.md).
- Docker 서비스명/포트 기준 내부 호출 주소는 BE-A가 docker-compose에서 확정(예: `http://ai:8000`).

## 엔드포인트 (이번 범위: 2개)

### POST /internal/meeting-notes/action-items
Request
```json
{ "teamId": 3, "content": "회의 원문 텍스트" }
```
Response (200)
```json
{ "items": [ { "title": "로그인 API 검증", "assigneeHint": "백엔드 A", "dueDateHint": "2026-04-10" } ] }
```

### POST /internal/documents/tone
Request
```json
{ "teamId": 3, "type": "NOTICE", "content": "원본 본문", "tone": "SHORT" }
```
Response (200)
```json
{ "content": "톤이 변환된 본문" }
```

### GET /health
```json
{ "status": "ok" }
```

## STEP4 추가 예정 (계약만 기록)

- `POST /internal/teams/progress-summary` → `{ "summary": "..." }`
- `POST /internal/teams/qna` → `{ "answer": "...", "sources": [ { "type":"MEETING_NOTE", "id":12, "title":"..." } ] }`

## 에러 응답 (공통)

```json
{ "error": { "code": "INVALID_JSON", "message": "AI 응답 형식이 올바르지 않습니다." } }
```
- HTTP 400: 입력 검증 실패(예: tone 허용값 밖).
- HTTP 422: AI 처리 실패(빈 응답/JSON 오류/title 없음/출처 없음/타임아웃).
- HTTP 500: 예기치 못한 서버 오류.

## 변경 규칙

- 내부 스키마 변경 시 이 문서 먼저 갱신 후 코드 반영. 문서 없이 스키마 변경 금지(AI_GUIDE STEP5).
