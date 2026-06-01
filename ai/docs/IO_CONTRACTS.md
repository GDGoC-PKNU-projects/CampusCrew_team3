# AI 기능 입출력 계약 (IO_CONTRACTS)

CampusCrew AI 4기능의 입력/출력 필드를 고정한다. 근거: `doc/ai/AI_SPEC.md` §5, §8 / `doc/backend/API_SPEC.md` §11.

> 공개 경로는 Spring `/api/ai/*`. AI 서버는 같은 payload를 내부 `/internal/*`로 처리하고 `data` 객체만 반환한다. Spring이 `ApiResponse{success,data,message}`로 감싼다. 내부 스키마는 [INTERNAL_API.md](./INTERNAL_API.md) 참고.

---

## 1. 회의록 액션 아이템 추출

- 공개: `POST /api/ai/meeting-notes/action-items`
- 연결 화면: 회의록 작성

| 구분 | 필드 | 타입 | 규칙 |
| --- | --- | --- | --- |
| 입력 | `teamId` | number | 필수 |
| 입력 | `content` | string | 필수, 회의록 본문 |
| 출력 | `items[]` | array | 0개 이상 |
| 출력 | `items[].title` | string | **필수**, 비면 실패 처리 |
| 출력 | `items[].assigneeHint` | string \| null | 없으면 `null` |
| 출력 | `items[].dueDateHint` | string \| null | `YYYY-MM-DD` 또는 `null` |

성공 예시:
```json
{ "items": [ { "title": "로그인 API 검증", "assigneeHint": "백엔드 A", "dueDateHint": "2026-04-10" } ] }
```

규칙: 회의록 **요약 아님**. 실행 가능한 할 일 후보만. 담당자/마감일은 확정값 아닌 hint. 없는 정보 생성 금지.

---

## 2. 문서 톤 변환

- 공개: `POST /api/ai/documents/tone`
- 연결 화면: 공지 작성

| 구분 | 필드 | 타입 | 규칙 |
| --- | --- | --- | --- |
| 입력 | `teamId` | number | 필수 |
| 입력 | `type` | string | `NOTICE` 고정 |
| 입력 | `content` | string | 필수, 원본 본문 |
| 입력 | `tone` | enum | `SHORT` \| `FORMAL` \| `PLAIN` |
| 출력 | `content` | string | 변환된 본문 |

성공 예시:
```json
{ "content": "톤이 변환된 문서 본문" }
```

규칙: 의미 보존, 표현만 변경. 새 사실 추가 금지. 장황 금지.

---

## 3. 진행 상황 요약 (STEP4 — 이번 범위 제외, 계약만 기록)

- 공개: `POST /api/ai/teams/progress-summary`
- 연결 화면: 팀 대시보드

| 구분 | 필드 | 타입 | 규칙 |
| --- | --- | --- | --- |
| 입력 | `teamId` | number | 필수 |
| 출력 | `summary` | string | 요약 텍스트 |

규칙: 공지/일정/할일/회의록 기반. 핵심 변화 중심, 짧은 보고체. RAG 미사용.

---

## 4. 팀 문서 Q&A (STEP4 — 이번 범위 제외, 계약만 기록)

- 공개: `POST /api/ai/teams/qna`
- 연결 화면: 팀 대시보드

| 구분 | 필드 | 타입 | 규칙 |
| --- | --- | --- | --- |
| 입력 | `teamId` | number | 필수 |
| 입력 | `question` | string | 필수 |
| 출력 | `answer` | string | 답변 |
| 출력 | `sources[]` | array | **최대 3개**, 비면 실패 |
| 출력 | `sources[].type` | enum | `NOTICE` \| `SCHEDULE` \| `TASK` \| `MEETING_NOTE` |
| 출력 | `sources[].id` | number | 원본 문서 id |
| 출력 | `sources[].title` | string | 원본 제목 |

규칙: 같은 팀 문서만 검색(RAG). 근거 없으면 "모른다", 추측 금지. 설계는 [RAG_DESIGN.md](./RAG_DESIGN.md).

---

## 공통 규칙 (AI_SPEC §3)

- 사용자가 직접 실행할 때만 동작.
- AI 결과 자동 저장 안 함.
- 응답은 항상 고정 JSON 구조.
- 모델 `gemini-2.5-flash-lite` 고정.
- AI 실패가 일반 CRUD 기능을 막지 않음.
