# CampusCrew 공통 성공코드 명세서

## 1. 문서 목적

이 문서는 CampusCrew의 모든 API에서 사용하는 성공 응답의 HTTP 상태 코드, 메시지, 응답 구조를 고정한다.  
프론트엔드, 백엔드 모두 이 문서를 기준으로 성공 응답 처리를 통일한다.

## 2. 공통 성공 응답 형식

모든 성공 응답은 아래 구조를 따른다.

```json
{
  "success": true,
  "data": {},
  "message": "성공 메시지"
}
```

- `success`는 항상 `true`다.
- `data`는 요청에 따른 응답 데이터다. 조회 시 객체 또는 배열, 삭제 시 `null`이다.
- `message`는 사용자에게 보여줄 수 있는 한국어 문장이다. 조회 API는 `null`을 사용한다.
- 성공 응답 구조는 도메인에 관계없이 동일하다.

## 3. HTTP 상태 코드 기준

| 상태 코드 | 의미 | 사용 기준 |
| --- | --- | --- |
| `200` | OK | 조회, 수정, 삭제 등 일반적인 성공 응답 |
| `201` | Created | 새로운 리소스가 생성된 경우 |

## 4. 성공코드 상세

### 4.1 인증 API

| 코드 | HTTP | API | 메시지 | data 구조 |
| --- | --- | --- | --- | --- |
| `SUCCESS_AUTH_001` | `201` | `POST /api/auth/signup` | 회원가입이 완료되었습니다. | `{ id, name, email }` |
| `SUCCESS_AUTH_002` | `200` | `POST /api/auth/login` | 로그인에 성공했습니다. | `{ accessToken, user: { id, name, email } }` |
| `SUCCESS_AUTH_003` | `200` | `GET /api/auth/me` | `null` | `{ id, name, email, studentId }` |

### 4.2 팀 API

| 코드 | HTTP | API | 메시지 | data 구조 |
| --- | --- | --- | --- | --- |
| `SUCCESS_TEAM_001` | `200` | `GET /api/teams` | `null` | `[ { id, name, courseName, description, memberCount } ]` |
| `SUCCESS_TEAM_002` | `201` | `POST /api/teams` | 팀이 생성되었습니다. | `{ id, name, courseName, description, joinCode }` |
| `SUCCESS_TEAM_003` | `200` | `GET /api/teams/{teamId}` | `null` | `{ id, name, courseName, description, joinCode, ownerId, memberCount }` |
| `SUCCESS_TEAM_004` | `200` | `POST /api/teams/join` | 팀에 참여했습니다. | `{ teamId, teamName }` |

### 4.3 대시보드 API

| 코드 | HTTP | API | 메시지 | data 구조 |
| --- | --- | --- | --- | --- |
| `SUCCESS_DASH_001` | `200` | `GET /api/teams/{teamId}/dashboard` | `null` | `{ team, recentNotices, upcomingSchedules, myTasks, recentNotes }` |

### 4.4 공지 API

| 코드 | HTTP | API | 메시지 | data 구조 |
| --- | --- | --- | --- | --- |
| `SUCCESS_NOTICE_001` | `200` | `GET /api/teams/{teamId}/notices` | `null` | `[ { id, title, content, authorId, authorName, createdAt } ]` |
| `SUCCESS_NOTICE_002` | `201` | `POST /api/teams/{teamId}/notices` | 공지가 생성되었습니다. | `{ id, title, content, authorId, authorName, createdAt, updatedAt }` |
| `SUCCESS_NOTICE_003` | `200` | `GET /api/teams/{teamId}/notices/{noticeId}` | `null` | `{ id, title, content, authorId, authorName, createdAt, updatedAt }` |
| `SUCCESS_NOTICE_004` | `200` | `PUT /api/teams/{teamId}/notices/{noticeId}` | 공지가 수정되었습니다. | `{ id, title, content, authorId, authorName, createdAt, updatedAt }` |
| `SUCCESS_NOTICE_005` | `200` | `DELETE /api/teams/{teamId}/notices/{noticeId}` | 공지가 삭제되었습니다. | `null` |

### 4.5 일정 API

| 코드 | HTTP | API | 메시지 | data 구조 |
| --- | --- | --- | --- | --- |
| `SUCCESS_SCHED_001` | `200` | `GET /api/teams/{teamId}/schedules` | `null` | `[ { id, title, description, startDateTime, endDateTime, location, authorId, authorName, createdAt, updatedAt } ]` |
| `SUCCESS_SCHED_002` | `201` | `POST /api/teams/{teamId}/schedules` | 일정이 생성되었습니다. | `{ id, title, description, startDateTime, endDateTime, location, authorId, authorName, createdAt, updatedAt }` |
| `SUCCESS_SCHED_003` | `200` | `GET /api/teams/{teamId}/schedules/{scheduleId}` | `null` | 생성 응답과 동일 |
| `SUCCESS_SCHED_004` | `200` | `PUT /api/teams/{teamId}/schedules/{scheduleId}` | 일정이 수정되었습니다. | 생성 응답과 동일 |
| `SUCCESS_SCHED_005` | `200` | `DELETE /api/teams/{teamId}/schedules/{scheduleId}` | 일정이 삭제되었습니다. | `null` |

### 4.6 할 일 API

| 코드 | HTTP | API | 메시지 | data 구조 |
| --- | --- | --- | --- | --- |
| `SUCCESS_TASK_001` | `200` | `GET /api/teams/{teamId}/tasks` | `null` | `[ { id, title, description, assigneeId, assigneeName, authorId, authorName, dueDate, status, createdAt, updatedAt } ]` |
| `SUCCESS_TASK_002` | `201` | `POST /api/teams/{teamId}/tasks` | 할 일이 생성되었습니다. | `{ id, title, description, assigneeId, assigneeName, dueDate, status }` |
| `SUCCESS_TASK_003` | `200` | `GET /api/teams/{teamId}/tasks/{taskId}` | `null` | 목록 항목 구조와 동일 |
| `SUCCESS_TASK_004` | `200` | `PUT /api/teams/{teamId}/tasks/{taskId}` | 할 일이 수정되었습니다. | 생성 응답에 `authorId`, `authorName`, `createdAt`, `updatedAt` 포함 |
| `SUCCESS_TASK_005` | `200` | `PATCH /api/teams/{teamId}/tasks/{taskId}/status` | 할 일 상태가 변경되었습니다. | `{ id, status }` |
| `SUCCESS_TASK_006` | `200` | `DELETE /api/teams/{teamId}/tasks/{taskId}` | 할 일이 삭제되었습니다. | `null` |

### 4.7 회의록 API

| 코드 | HTTP | API | 메시지 | data 구조 |
| --- | --- | --- | --- | --- |
| `SUCCESS_NOTE_001` | `200` | `GET /api/teams/{teamId}/notes` | `null` | `[ { id, title, content, meetingDate, authorId, authorName, createdAt, updatedAt } ]` |
| `SUCCESS_NOTE_002` | `201` | `POST /api/teams/{teamId}/notes` | 회의록이 생성되었습니다. | `{ id, title, content, meetingDate, authorId, authorName, createdAt, updatedAt }` |
| `SUCCESS_NOTE_003` | `200` | `GET /api/teams/{teamId}/notes/{noteId}` | `null` | 생성 응답과 동일 |
| `SUCCESS_NOTE_004` | `200` | `PUT /api/teams/{teamId}/notes/{noteId}` | 회의록이 수정되었습니다. | 생성 응답과 동일 |
| `SUCCESS_NOTE_005` | `200` | `DELETE /api/teams/{teamId}/notes/{noteId}` | 회의록이 삭제되었습니다. | `null` |

### 4.8 AI API

| 코드 | HTTP | API | 메시지 | data 구조 |
| --- | --- | --- | --- | --- |
| `SUCCESS_AI_001` | `200` | `POST /api/ai/meeting-notes/action-items` | `null` | `{ items: [ { title, assigneeHint, dueDateHint } ] }` |
| `SUCCESS_AI_002` | `200` | `POST /api/ai/documents/tone` | `null` | `{ content }` |
| `SUCCESS_AI_003` | `200` | `POST /api/ai/teams/progress-summary` | `null` | `{ summary }` |
| `SUCCESS_AI_004` | `200` | `POST /api/ai/teams/qna` | `null` | `{ answer, sources: [ { type, id, title } ] }` |

## 5. 삭제 응답 공통 형식

모든 삭제 API는 아래 구조를 따른다.

```json
{
  "success": true,
  "data": null,
  "message": "삭제되었습니다."
}
```

- `message`는 도메인에 따라 `공지가 삭제되었습니다.`, `일정이 삭제되었습니다.` 등으로 구체화한다.