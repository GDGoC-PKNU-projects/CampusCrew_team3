import styles from './styles/CreateTeamModal.module.css'
import { X } from 'lucide-react'
import {useNavigate} from "react-router-dom";
import {useState} from "react";
import {createTeam} from "../../services/teams.js";

function CreateTeamModal() {
  const navigate = useNavigate()
  const [error, setError] = useState('')
  const [name, setName] = useState('')
  const [courseName, setCourseName] = useState('')
  const [description, setDescription] = useState('')

  function handleClose() {
    navigate('/teams')
  }

  async function handleSubmit(e) {
    e.preventDefault()
    setError('')
    try {
      await createTeam(name, courseName, description)
      navigate('/teams')
    } catch (err) {
      setError(err.message)
    }
  }

  return (
    <section className={styles.overlay}>
      <form className={styles.modal} onSubmit={handleSubmit}>
        <button
          type="button"
          className={styles.closeButton}
          onClick={handleClose}
        >
          <X size={22} />
        </button>

        <div className={styles.titleBox}>
          <h2 className={styles.title}>새 팀 만들기</h2>
          <p className={styles.description}>팀 프로젝트 정보를 입력하세요</p>
        </div>

        <div className={styles.inputBox}>
          <label className={styles.label} htmlFor="name">
            팀 이름
          </label>

          <input
            id="name"
            className={styles.input}
            type="text"
            value={name}
            onChange={(e) => setName(e.target.value)}
            placeholder="예: 웹 프로그래밍 팀 프로젝트"
            minLength={2}
            maxLength={30}
            required
          />
        </div>

        <div className={styles.inputBox}>
          <label className={styles.label} htmlFor="courseName">
            과목명
          </label>

          <input
            id="courseName"
            className={styles.input}
            type="text"
            value={courseName}
            onChange={(e) => setCourseName(e.target.value)}
            placeholder="예: 웹 프로그래밍"
            minLength={2}
            maxLength={30}
            required
          />
        </div>

        <div className={styles.inputBox}>
          <label className={styles.label} htmlFor="description">
            팀 소개
          </label>

          <textarea
            id="description"
            className={styles.textarea}
            value={description}
            onChange={(e) => setCourseName(e.target.value)}
            placeholder="예: 함께 웹 프로젝트를 진행할 팀입니다."
            maxLength={500}
          />
        </div>

        <button type="submit" className={styles.submitButton}>
          생성하기
        </button>
      </form>
    </section>
  )
}

export default CreateTeamModal