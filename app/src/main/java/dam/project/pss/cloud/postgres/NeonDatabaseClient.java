package dam.project.pss.cloud.postgres;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class NeonDatabaseClient {

    static {
        try {
            // Cargar el driver JDBC de PostgreSQL
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    /**
     * Obtiene una conexión directa a la base de datos PostgreSQL alojada en Neon.
     */
    public static Connection getConnection() throws SQLException {
        Properties props = new Properties();
        props.setProperty("user", NeonPostgresConfig.USER);
        props.setProperty("password", NeonPostgresConfig.PASSWORD);
        props.setProperty("sslmode", NeonPostgresConfig.SSL_MODE);

        return DriverManager.getConnection(NeonPostgresConfig.getJdbcUrl(), props);
    }
}
