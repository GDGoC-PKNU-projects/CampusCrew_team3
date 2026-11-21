import styles from './styles/JoinTeamCard.module.css'
import {LogIn} from "lucide-react";
import {useState} from "react";
import {joinTeam} from "../../services/teams.js";

function JoinTeamCard() {
  const [error, setError] = useState('')
  const [joinCode, setJoinCode] = useState('')


  async function handleSubmit(e) {
    e.preventDefault()
    setError('')
    try {
      await joinTeam(joinCode)
      setJoinCode('');
    } catch (err) {
      setError(err.message)
    }
  }

  return (
    <form className={styles.joinTeamCard} onSubmit={handleSubmit}>
      <div className={styles.titleBox}>
        <h2 className={styles.title}>
          <LogIn size={22} />
          팀 참여하기
        </h2>

        <p className={styles.description}>
          초대 코드로 기존 팀에 참여하세요
        </p>
      </div>

      <div className={styles.inputBox}>
        <label className={styles.label} htmlFor="joinCode">
          초대 코드
        </label>

        <input
          id="joinCode"
          className={styles.input}
          type="text"
          value={joinCode}
          onChange={(e) => setJoinCode(e.target.value)}
          placeholder="ABC-DEF-123"
          required
        />
      </div>

      <button type="submit" className={styles.joinButton}>
        참여하기
      </button>
    </form>
  )
}

export default JoinTeamCard