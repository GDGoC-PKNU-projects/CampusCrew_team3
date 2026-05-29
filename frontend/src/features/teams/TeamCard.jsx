import styles from './styles/TeamCard.module.css'
import {Users} from "lucide-react";
import {useNavigate} from "react-router-dom";

function TeamCard({team}) {
  const navigate = useNavigate()

  function handleTeamClick() {
    navigate(`/teams/detail`)
  }

  return (
    <button type="button" className={styles.teamCard} onClick={handleTeamClick}>
      <div className={styles.iconBox}>
        <Users size={28} />
      </div>
      <div className={styles.teamInfo}>
        <h3 className={styles.teamName}>{team.name}</h3>
        <p className={styles.subjectName}>{team.courseName}</p>
      </div>

      <div className={styles.memberInfo}>
        <Users size={28} />
        <span>{team.memberCount}명</span>
      </div>
    </button>
  )
}

export default TeamCard