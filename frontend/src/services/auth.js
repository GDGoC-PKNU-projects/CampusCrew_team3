export async function login(email, password) {
  if (!email || !password) {
    throw new Error('이메일과 비밀번호를 입력해주세요.')
  }

  const fakeToken = 'test-access-token'

  localStorage.setItem('accessToken', fakeToken)

  return {
    accessToken: fakeToken,
    user: {
      email,
      name: '테스트 사용자',
    },
  }
}

export async function signup(name, email, password) {
  if (!name || !email || !password) {
    throw new Error('모든 값을 입력해주세요.')
  }

  return {
    name,
    email,
  }
}

export function logout() {
  localStorage.removeItem('accessToken')
}

export function getAccessToken() {
  return localStorage.getItem('accessToken')
}