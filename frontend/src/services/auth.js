export async function login(email, password) {

  validateLogin(email, password)

  const BASE_URL = import.meta.env.VITE_LOCAL_BASE_API_URL

  const fakeToken = 'test-access-token'

  localStorage.setItem('accessToken', fakeToken)

  return {
    accessToken: fakeToken,
    user: {
      email,
      name: '테스트 사용자',
    },
  }

  function validateLogin(email, password) {
    const normalizedEmail = email.trim()

    const emailRegex = /^[^\s@]+@[^\s@]+\.[^\s@]+$/

    if (!normalizedEmail || !password) {
      throw new Error('이메일과 비밀번호를 입력해주세요.')
    }

    if (normalizedEmail.length > 100) {
      throw new Error('이메일은 100자 이하로 입력해주세요.')
    }

    if (!emailRegex.test(normalizedEmail)) {
      throw new Error('올바른 이메일 형식이 아닙니다.')
    }

    if (password.length < 8 || password.length > 20) {
      throw new Error('비밀번호는 8자 이상 20자 이하로 입력해주세요.')
    }
  }

  // API 생성 후 적용 예정(임시 주석 처리)
  /*
  const response = await fetch(
    `${BASE_URL}/api/auth/login`,
    {
      method: 'POST',
      headers: {
        'Content-Type' : 'application/json'
      },
      body: JSON.stringify({
        email,
        password
      })
    }
  )

  const result = await response.json()

  if (!response.success) {
    throw new Error('로그인에 실패했습니다.')
  }

  const accessToken = result.data.accessToken

  localStorage.setItem('accessToken', accessToken)

  return result.data
   */
}


export async function signup(name, studentId, email, password) {

  validateSignup(name, studentId, email, password)

  return {
    name,
    email,
  }

  // API 생성 후 적용 예정(임시 주석 처리)
  /*
  const BASE_URL = import.meta.env.VITE_LOCAL_BASE_API_URL

  const response = await fetch(`${BASE_URL}/api/auth/signup`, {
    method: 'POST',
    headers: {
      'Content-Type': 'application/json'
    },
    body : JSON.stringify({
      name,
      studentId,
      email,
      password
    }),
  })

  const data = await response.json()

  if (response.success) {
    throw new Error('회원가입에 실패했습니다.')
  }

  return data
  */
}

function validateSignup(name, studentId, email, password) {
  const normalizedName = name.trim()
  const normalizedEmail = email.trim()

  const emailRegex = /^[^\s@]+@[^\s@]+\.[^\s@]+$/
  const passwordRegex = /^(?=.*[A-Za-z])(?=.*\d).+$/

  if (!normalizedName || !studentId || !normalizedEmail || !password) {
    throw new Error('모든 값을 입력해주세요.')
  }

  if (normalizedName.length < 2 || normalizedName.length > 20) {
    throw new Error('이름은 2자 이상 20자 이하로 입력해주세요.')
  }

  if (studentId.length < 8 || studentId.length > 10) {
    throw new Error('학번은 8자리 이상 10자리 이하로 입력해주세요.')
  }

  if (normalizedEmail.length > 100) {
    throw new Error('이메일은 100자 이하로 입력해주세요.')
  }

  if (!emailRegex.test(normalizedEmail)) {
    throw new Error('올바른 이메일 형식이 아닙니다.')
  }

  if (password.length < 8 || password.length > 20) {
    throw new Error('비밀번호는 8자 이상 20자 이하로 입력해주세요.')
  }

  if (!passwordRegex.test(password)) {
    throw new Error('비밀번호는 영문과 숫자를 포함해야 합니다.')
  }
}

export function logout() {
localStorage.removeItem('accessToken')
}

export function getAccessToken() {
return localStorage.getItem('accessToken')
}