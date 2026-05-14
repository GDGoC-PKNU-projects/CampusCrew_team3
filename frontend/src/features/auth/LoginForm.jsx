import { useState } from 'react'
import { useNavigate } from 'react-router-dom'
import { login } from '../../services/auth'

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
    <form onSubmit={handleSubmit}>
      <div>
        <label>이메일</label>
        <input
          type="email"
          value={email}
          onChange={(e) => setEmail(e.target.value)}
          placeholder="이메일을 입력하세요"
        />
      </div>

      <div>
        <label>비밀번호</label>
        <input
          type="password"
          value={password}
          onChange={(e) => setPassword(e.target.value)}
          placeholder="비밀번호를 입력하세요"
        />
      </div>

      {error && <p>{error}</p>}

      <button type="submit">로그인</button>
    </form>
  )
}

export default LoginForm