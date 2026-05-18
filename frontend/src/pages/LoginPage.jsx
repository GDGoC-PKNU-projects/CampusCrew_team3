import LoginForm from '../features/auth/LoginForm'
import styles from './styles/LoginPage.module.css'
import { Users } from 'lucide-react'

function LoginPage() {
  return (
    <main className={styles.page}>
      <section className={styles.container}>
        <div className={styles.brandSection}>

          <div className={styles.iconWrapper}>
            <Users size={34} />
          </div>

          <h1 className={styles.title}>
            CampusCrew
          </h1>

          <p className={styles.description}>
            대학생 팀 프로젝트 협업 워크스페이스
          </p>

        </div>
        <LoginForm />
      </section>
    </main>
  )
}

export default LoginPage