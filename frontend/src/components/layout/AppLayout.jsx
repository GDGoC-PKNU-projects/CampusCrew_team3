import TeamSidebar from "./TeamSidebar.jsx";
import DashboardPage from "../../pages/team/DashboardPage.jsx";
import styles from './styles/AppLayout.module.css'

function AppLayout() {

  return (
    <div className={styles.layout}>
      <TeamSidebar />

      <main className={styles.content}>
        <DashboardPage />
      </main>
    </div>
  )
}

export default AppLayout