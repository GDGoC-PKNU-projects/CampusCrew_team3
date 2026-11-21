import styles from './styles/PageHeader.module.css'

function PageHeader({ title, description }) {

  return (
    <header className={styles.pageHeader}>
      <div>
        <h1 className={styles.title}>{title}</h1>
        <p className={styles.description}>{description}</p>
      </div>
    </header>
  )
}

export default PageHeader