import { useState } from 'react'
import { useNavigate } from 'react-router-dom'
import { signup } from '../../services/auth'

function SignupForm() {
  const navigate = useNavigate()

  const [name, setName] = useState('')
  const [email, setEmail] = useState('')
  const [password, setPassword] = useState('')
  const [error, setError] = useState('')

  async function handleSubmit(e) {
    e.preventDefault()
    setError('')

    try {
      await signup(name, email, password)
      navigate('/login')
    } catch (err) {
      setError(err.message)
    }
  }

  return (
    <form onSubmit={handleSubmit}>
      <div>
        <label>이름</label>
        <input
          type="text"
          value={name}
          onChange={(e) => setName(e.target.value)}
          placeholder="이름을 입력하세요"
        />
      </div>

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

      <button type="submit">회원가입</button>
    </form>
  )
}

export default SignupForm