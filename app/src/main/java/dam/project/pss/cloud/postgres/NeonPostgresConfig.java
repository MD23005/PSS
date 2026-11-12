package dam.project.pss.cloud.postgres;

public class NeonPostgresConfig {

    // Configura tus credenciales obtenidas de la consola de Neon (https://neon.tech)
    public static final String HOST = "ep-still-star-bcazmr0y-pooler.c-16.us-east-1.aws.neon.tech";
    public static final String PORT = "5432";
    public static final String DATABASE = "neondb";
    public static final String USER = "neondb_owner";
    public static final String PASSWORD = "npg_dWoycV6BSxf4";
    public static final String SSL_MODE = "require";

    // Genera la URL de conexión JDBC oficial para PostgreSQL / Neon
    public static String getJdbcUrl() {
        return String.format("jdbc:postgresql://%s:%s/%s?sslmode=%s", HOST, PORT, DATABASE, SSL_MODE);
    }
}
