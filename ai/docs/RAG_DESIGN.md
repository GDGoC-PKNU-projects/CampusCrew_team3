# RAG 설계 메모 (RAG_DESIGN)

> **STEP4 구현 대상. 이 문서는 설계만 고정한다. 코드/인덱스는 STEP4에서 만든다.**

팀 문서 Q&A에만 RAG를 적용한다. 근거: `doc/ai/AI_SPEC.md` §6, `doc/backend/ERD.md` §3.8~3.9, `doc/common/DEPLOYMENT.md` §7~8.

## 1. 수집 대상

- 공지(`NOTICE`), 일정(`SCHEDULE`), 할 일 설명(`TASK`), 회의록 본문(`MEETING_NOTE`).
- 그 외 데이터는 임베딩 대상 아님.

## 2. 저장 구조 (PostgreSQL + pgvector)

ERD 고정 스키마:
- `rag_documents`: `id, team_id, source_type, source_id, title, content, created_at, updated_at`. `(team_id, source_type, source_id)` UNIQUE.
- `rag_chunks`: `id, document_id, team_id, chunk_index, chunk_text, embedding(vector), created_at`. `(document_id, chunk_index)` UNIQUE.
- 검색은 `rag_chunks.embedding` 벡터 인덱스 사용.

## 3. 청크 기준 (초안)

- 문서를 의미 단위로 분할. 기준 길이 후보: 300~500자, 약간 overlap(예: 50자).
- `chunk_index`는 0부터 순서대로.
- 짧은 문서(공지/일정)는 1청크로 처리 가능.
- 최종 청크 길이는 STEP4에서 실제 문서 길이 분포 보고 확정.

## 4. 임베딩 모델 (미정 — 후보)

- 조건: pgvector에 저장 가능한 **고정 차원** 벡터 반환(AI_SPEC §10).
- 후보: `text-embedding-004`(768dim) 등 Gemini 임베딩. 질의/문서 동일 모델 사용.
- 차원 결정 후 `vector(N)` 컬럼 차원 고정. STEP4 시작 시 1개로 확정.

## 5. 검색 흐름

1. 질문 임베딩 생성.
2. `team_id` 범위로만 `rag_chunks` 유사도 검색(팀 밖 문서 절대 미참조).
3. 상위 **최대 3개** 청크만 컨텍스트로 사용.
4. 컨텍스트 + 질문으로 답변 생성.
5. 사용한 청크의 원본 문서에서 `sources` 구성.

## 6. 출처 규칙

- `sources` 최대 3개.
- 각 출처 필수 필드: `type`, `id`, `title`.
- `type` 허용값: `NOTICE`, `SCHEDULE`, `TASK`, `MEETING_NOTE`.
- 출처가 0개면 실패(FAILURE_HANDLING의 `NO_SOURCES`).
- 근거 없으면 "모른다"로 답하고 추측 금지.

## 7. 동기화(재생성) 규칙

- 원본(공지/일정/할일/회의록) **수정** → 대응 `rag_documents` + `rag_chunks` **전체 재생성**.
- 원본 **삭제** → 연결된 `rag_documents`, `rag_chunks` 함께 삭제.
- 팀 삭제 → 팀 하위 RAG 데이터 함께 삭제.

## 8. 운영

- RAG 인덱스/검색 책임은 같은 인스턴스 안 AI 서버 프로세스.
- DB 포트 외부 비공개, Docker 네트워크 내부만 접근.
