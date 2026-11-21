import { BrowserRouter, Routes, Route, Navigate } from 'react-router-dom'
import LoginPage from './pages/LoginPage'
import SignupPage from './pages/SignupPage'
import TeamsPage from './pages/TeamsPage'
import PrivateRoute from './components/PrivateRoute'
import CreateTeamModal from "./features/teams/CreateTeamModal.jsx";
import AppLayout from "./components/layout/AppLayout.jsx";

function App() {
  return (
    <BrowserRouter>
      <Routes>
        <Route path="/login" element={<LoginPage />} />
        <Route path="/signup" element={<SignupPage />} />

        <Route
          path="/teams"
          element={
            <PrivateRoute>
              <TeamsPage />
            </PrivateRoute>
          }
        >
          <Route path="new" element={<CreateTeamModal />} />
        </Route>

        <Route path="/teams/detail" element={<AppLayout />} />

        <Route path="/" element={<Navigate to="/login" replace />} />
      </Routes>
    </BrowserRouter>
  )
}

export default App