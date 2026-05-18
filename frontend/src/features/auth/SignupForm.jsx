import { useState } from 'react'
import { useNavigate } from 'react-router-dom'
import { signup } from '../../services/auth'
import styles from './styles/SignupForm.module.css'
import { Link } from 'react-router-dom'

function SignupForm() {
  const navigate = useNavigate()

  const [name, setName] = useState('')
  const [studentId, setStudentId] = useState('')
  const [email, setEmail] = useState('')
  const [password, setPassword] = useState('')
  const [error, setError] = useState('')

  async function handleSubmit(e) {
    e.preventDefault()
    setError('')

    try {
      await signup(name, studentId, email, password)
      navigate('/login')
    } catch (err) {
      setError(err.message)
    }
  }

  return (
    <form className={styles.form} onSubmit={handleSubmit}>

      <h2 className={styles.title}>회원가입</h2>

      <div className={styles.field}>
        <label>이름</label>
        <input
          className={styles.input}
          type="text"
          minLength={2}
          maxLength={20}
          value={name}
          onChange={(e) => setName(e.target.value)}
          placeholder="이름을 입력하세요"
        />
      </div>

      <div className={styles.field}>
        <label>학번</label>
        <input
          className={styles.input}
          type="text"
          minLength={8}
          maxLength={10}
          value={studentId}
          onChange={(e) =>
            setStudentId(e.target.value.replace(/[^0-9]/g, ''))
          }
          placeholder="학번을 입력하세요"
        />
      </div>

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

      <div className={styles.field}>
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

      <button className={styles.button} type="submit">회원가입</button>

      <p className={styles.loginText}>
        이미 계정이 있으신가요?
        <Link to="/login" className={styles.loginLink}>
          로그인
        </Link>
      </p>

    </form>
  )
}

export default SignupForm