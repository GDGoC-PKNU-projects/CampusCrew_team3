import PageHeader from "../../components/common/PageHeader.jsx";
import styles from './styles/DashboardPage.module.css'
import DashboardCard from "../../features/team/dashboard/DashboardCard.jsx";
import {Bell, CalendarDays, CheckSquare, FileText} from "lucide-react";

function DashboardPage() {

  const noticeItems = [
    {
      id: 1,
      title: '중간 발표 일정 안내',
      meta: '김민수 · 2026-03-28',
    },
    {
      id: 2,
      title: '다음 회의 시간 변경',
      meta: '이지은 · 2026-03-27',
    },
  ]

  const scheduleItems = [
    {
      id: 1,
      title: '중간 발표',
      meta: '2026-04-05 · 공학관 301호',
    },
    {
      id: 2,
      title: '팀 회의',
      meta: '2026-04-02 · 도서관 3층',
    },
  ]

  const todoItems = [
    {
      id: 1,
      title: 'UI 디자인 완료',
      meta: '마감: 2026-04-01',
      badge: '진행중',
    },
    {
      id: 2,
      title: 'API 문서 작성',
      meta: '마감: 2026-04-03',
      badge: 'TODO',
    },
  ]

  const meetingNoteItems = [
    {
      id: 1,
      title: '3차 팀 회의록',
      meta: '2026-03-26 · 액션 아이템 3개',
    },
    {
      id: 2,
      title: '2차 팀 회의록',
      meta: '2026-03-19 · 액션 아이템 5개',
    },
  ]

  return (
    <main className={styles.dashboardPage}>
      <PageHeader
        title="대시보드"
        description="팀 프로젝트 현황을 한눈에 확인하세요"
      />

      <section className={styles.cardGrid}>
        <DashboardCard
          icon={<Bell size={20} />}
          title="최근 공지"
          items={noticeItems}
          buttonText="모든 공지 보기"
        />

        <DashboardCard
          icon={<CalendarDays size={20} />}
          title="다가오는 일정"
          items={scheduleItems}
          buttonText="모든 일정 보기"
        />

        <DashboardCard
          icon={<CheckSquare size={20} />}
          title="내 할 일"
          items={todoItems}
          buttonText="모든 할 일 보기"
        />

        <DashboardCard
          icon={<FileText size={20} />}
          title="최근 회의록"
          items={meetingNoteItems}
          buttonText="모든 회의록 보기"
        />
      </section>
    </main>
  )
}

export default DashboardPage