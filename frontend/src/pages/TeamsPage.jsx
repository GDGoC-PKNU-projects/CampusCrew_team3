import TeamCard from "../features/teams/TeamCard.jsx";
import CreateTeamCard from "../features/teams/CreateTeamCard.jsx";
import JoinTeamCard from "../features/teams/JoinTeamCard.jsx";
import TeamsHeader from "../features/teams/TeamsHeader.jsx";
import styles from './styles/TeamsPage.module.css'
import {useEffect, useState} from "react";
import {getTeams} from "../services/teams.js";
import { Outlet } from 'react-router-dom'

function TeamsPage() {
  const [teams, setTeams] = useState([]);
  const [error, setError] = useState('')

  useEffect(() => {
    async function fetchTeams() {
      try {
        const teams = await getTeams();
        const mappedTeams = teams.map((team) => ({
          id: team.id,
          name: team.name,
          courseName: team.courseName,
          description: team.description,
          memberCount: team.memberCount,
        }));

        setTeams(mappedTeams);
      } catch (err) {
        setError(err.message)
      }
    }

    fetchTeams();
  }, []);


return (
  <div className={styles.page}>
    <TeamsHeader />

    <main className={styles.container}>
      <section className={styles.titleSection}>
        <h2 className={styles.title}>내 팀</h2>
        <p className={styles.description}>
          참여 중인 팀 프로젝트를 확인하세요
        </p>
      </section>

      <section className={styles.teamGrid}>
        {teams.map((team) => (
          <TeamCard key={team.id} team={team} />
        ))}
      </section>

      <section className={styles.actionGrid}>
        <CreateTeamCard />
        <JoinTeamCard />
      </section>

      <Outlet />
    </main>
  </div>
)
}

export default TeamsPage