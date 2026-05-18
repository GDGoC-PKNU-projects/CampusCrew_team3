import { useState } from 'react'
import { useNavigate } from 'react-router-dom'
import { login } from '../../services/auth'
import styles from './styles/LoginForm.module.css'
import { Link } from 'react-router-dom'

function LoginForm() {
  const navigate = useNavigate()

  const [email, setEmail] = useState('')
  const [password, setPassword] = useState('')
  const [error, setError] = useState('')

  async function handleSubmit(e) {
    e.preventDefault()
    setError('')
    try {
      await login(email, password)
      navigate('/teams')
    } catch (err) {
      setError(err.message)
    }
  }

  return (
    <form className={styles.form} onSubmit={handleSubmit}>
      <h2 className={styles.title}>로그인</h2>
      <div className={styles.field}>
        <label className={styles.label}>이메일</label>
        <input
          className={styles.input}
          type="email"
          maxLength={100}
          value={email}
          onChange={(e) => setEmail(e.target.value)}
          placeholder="이메일을 입력하세요"
        />
      </div>

      <div>
        <label className={styles.label}>비밀번호</label>
        <input
          className={styles.input}
          type="password"
          minLength={8}
          maxLength={20}
          value={password}
          onChange={(e) => setPassword(e.target.value)}
          placeholder="비밀번호를 입력하세요"
        />
      </div>

      {error && <p className={styles.error}>{error}</p>}

      <button className={styles.button} type="submit">
        로그인
      </button>

      <p className={styles.signupText}>
        계정이 없으신가요?
        <Link to="/signup" className={styles.signupLink}>
          회원가입
        </Link>
      </p>
    </form>
  )
}

export default LoginForm