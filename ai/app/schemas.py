"""요청/응답 Pydantic 모델. docs/IO_CONTRACTS.md 계약과 1:1."""
import re
from enum import Enum
from typing import List, Literal, Optional

from pydantic import BaseModel, Field, field_validator

_DATE_RE = re.compile(r"^\d{4}-\d{2}-\d{2}$")


# ---------- 액션 아이템 추출 ----------
class ActionItemsRequest(BaseModel):
    teamId: int
    content: str = Field(min_length=1)


class ActionItem(BaseModel):
    title: str
    assigneeHint: Optional[str] = None
    dueDateHint: Optional[str] = None

    @field_validator("dueDateHint")
    @classmethod
    def normalize_due_date(cls, v: Optional[str]) -> Optional[str]:
        # YYYY-MM-DD 형식이 아니면 null로 강등 (없는 날짜를 못 박지 않음).
        if v is None or not _DATE_RE.match(v.strip()):
            return None
        return v.strip()

    @field_validator("title")
    @classmethod
    def strip_title(cls, v: str) -> str:
        return v.strip()

    @field_validator("assigneeHint")
    @classmethod
    def blank_assignee_to_none(cls, v: Optional[str]) -> Optional[str]:
        if v is None:
            return None
        return v.strip() or None


class ActionItemsResponse(BaseModel):
    items: List[ActionItem]


# ---------- 톤 변환 ----------
class Tone(str, Enum):
    SHORT = "SHORT"
    FORMAL = "FORMAL"
    PLAIN = "PLAIN"


class ToneRequest(BaseModel):
    teamId: int
    type: Literal["NOTICE"] = "NOTICE"
    content: str = Field(min_length=1)
    tone: Tone


class ToneResponse(BaseModel):
    content: str
