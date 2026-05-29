import styles from './styles/TeamSidebar.module.css'
import {useNavigate} from "react-router-dom";

function TeamSidebar() {
  const navigate = useNavigate()
  function handleMoveTeamList() {
    navigate('/teams')
  }

  return (
    <aside className={styles.sidebar}>
      <div className={styles.teamArea}>
        <button
          type="button"
          className={styles.backLink}
          onClick={handleMoveTeamList}
        >
          팀 목록으로
        </button>

        <div className={styles.teamInfo}>
          <div className={styles.teamIcon} />

          <div>
            <h2 className={styles.teamName}>웹 프로그래밍</h2>
            <p className={styles.teamType}>팀 프로젝트</p>
          </div>
        </div>
      </div>

      <div className={styles.nav}>
        <div className={styles.navItem}>
          대시보드
        </div>

        <div className={styles.navItem}>
          공지
        </div>

        <div className={styles.navItem}>
          일정
        </div>

        <div className={styles.navItem}>
          할 일
        </div>

        <div className={styles.navItem}>
          회의록
        </div>

        <div className={styles.navItem}>
          팀 정보
        </div>
      </div>
    </aside>
  )
}

export default TeamSidebar