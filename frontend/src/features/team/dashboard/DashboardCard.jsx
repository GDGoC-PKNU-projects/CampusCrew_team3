import styles from './styles/DashboardCard.module.css'

function DashboardCard({ icon, title, items, buttonText }) {

  return (
    <article className={styles.card}>
      <div className={styles.header}>
        <h2 className={styles.title}>
          {icon}
          <span>{title}</span>
        </h2>
      </div>

      <div className={styles.list}>
        {items.slice(0, 2).map((item) => (
          <div key={item.id} className={styles.item}>
            <div className={styles.itemContent}>
              <strong className={styles.itemTitle}>
                {item.title}
              </strong>

              <p className={styles.itemMeta}>
                {item.meta}
              </p>
            </div>

            {item.badge && (
              <span className={styles.badge}>
                {item.badge}
              </span>
            )}
          </div>
        ))}
      </div>

      <button type="button" className={styles.button}>
        {buttonText}
      </button>
    </article>
  )
}

export default DashboardCard