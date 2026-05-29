import styles from './styles/TeamsHeader.module.css'
import {Users} from "lucide-react";
import {LogOut} from "lucide-react";

function TeamsHeader() {
  return (
    <header className={styles.header}>
      <div className={styles.headerInner}>
        <div className={styles.logoBox}>
          <div className={styles.logoIcon}>
            <Users size={28} />
          </div>

          <h1 className={styles.logoText}>CampusCrew</h1>
        </div>

        <button
          type="button"
          className={styles.logoutButton}
        >
          <LogOut size={18} />
          <span>로그아웃</span>
        </button>
      </div>
    </header>
  )
}

export default TeamsHeader