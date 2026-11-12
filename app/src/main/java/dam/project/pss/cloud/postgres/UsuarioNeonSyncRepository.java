package dam.project.pss.cloud.postgres;

import android.content.Context;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

import dam.project.pss.database.AppDatabase;
import dam.project.pss.database.AppExecutors;
import dam.project.pss.database.entity.Usuario;

public class UsuarioNeonSyncRepository {

    private final AppDatabase db;

    public interface SyncCallback {
        void onSuccess(Usuario usuario);
        void onError(String errorMessage);
    }

    public UsuarioNeonSyncRepository(Context context) {
        this.db = AppDatabase.getInstance(context);
    }

    /**
     * Crea la tabla de usuarios en PostgreSQL Neon si aún no existe.
     */
    public void crearTablaSiNoExiste() {
        AppExecutors.getInstance().diskIO().execute(() -> {
            String sql = "CREATE TABLE IF NOT EXISTS usuarios (" +
                    "id INT PRIMARY KEY, " +
                    "nombres VARCHAR(255), " +
                    "apellidos VARCHAR(255), " +
                    "dui VARCHAR(50), " +
                    "telefono VARCHAR(50), " +
                    "email VARCHAR(255), " +
                    "password VARCHAR(255), " +
                    "departamento VARCHAR(100), " +
                    "rol VARCHAR(50), " +
                    "imagen_dui_uri TEXT);";

            try (Connection conn = NeonDatabaseClient.getConnection();
                 Statement stmt = conn.createStatement()) {
                stmt.execute(sql);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    /**
     * Guarda/actualiza el usuario localmente en Room y lo sincroniza directamente con Neon PostgreSQL.
     */
    public void sincronizarUsuarioANeon(Usuario usuario, SyncCallback callback) {
        AppExecutors.getInstance().diskIO().execute(() -> {
            // 1. Guardar en la base de datos local Room
            if (usuario.getId() > 0) {
                db.usuarioDao().update(usuario);
            } else {
                long newId = db.usuarioDao().insert(usuario);
                usuario.setId((int) newId);
            }

            // Notificar éxito local
            AppExecutors.getInstance().mainThread().execute(() -> {
                if (callback != null) {
                    callback.onSuccess(usuario);
                }
            });

            // 2. Sincronizar directamente en Neon PostgreSQL (UPSERT)
            String sql = "INSERT INTO usuarios (id, nombres, apellidos, dui, telefono, email, password, departamento, rol, imagen_dui_uri) " +
                    "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?) " +
                    "ON CONFLICT (id) DO UPDATE SET " +
                    "nombres = EXCLUDED.nombres, " +
                    "apellidos = EXCLUDED.apellidos, " +
                    "dui = EXCLUDED.dui, " +
                    "telefono = EXCLUDED.telefono, " +
                    "email = EXCLUDED.email, " +
                    "password = EXCLUDED.password, " +
                    "departamento = EXCLUDED.departamento, " +
                    "rol = EXCLUDED.rol, " +
                    "imagen_dui_uri = EXCLUDED.imagen_dui_uri;";

            try (Connection conn = NeonDatabaseClient.getConnection();
                 PreparedStatement pstmt = conn.prepareStatement(sql)) {

                pstmt.setInt(1, usuario.getId());
                pstmt.setString(2, usuario.getNombres());
                pstmt.setString(3, usuario.getApellidos());
                pstmt.setString(4, usuario.getDui());
                pstmt.setString(5, usuario.getTelefono());
                pstmt.setString(6, usuario.getEmail());
                pstmt.setString(7, usuario.getPassword());
                pstmt.setString(8, usuario.getDepartamento());
                pstmt.setString(9, usuario.getRol());
                pstmt.setString(10, usuario.getImagenDuiUri());

                pstmt.executeUpdate();
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    /**
     * Actualiza el nombre/apellido en Room local y refleja el cambio en la base PostgreSQL en Neon.
     */
    public void actualizarNombreEnNeon(int usuarioId, String nuevoNombre, String nuevoApellido, SyncCallback callback) {
        AppExecutors.getInstance().diskIO().execute(() -> {
            Usuario usuario = db.usuarioDao().getById(usuarioId);
            if (usuario == null) {
                AppExecutors.getInstance().mainThread().execute(() -> {
                    if (callback != null) callback.onError("Usuario no encontrado localmente");
                });
                return;
            }

            // Actualizar objeto y Room local
            usuario.setNombres(nuevoNombre);
            usuario.setApellidos(nuevoApellido);
            db.usuarioDao().update(usuario);

            AppExecutors.getInstance().mainThread().execute(() -> {
                if (callback != null) callback.onSuccess(usuario);
            });

            // Reflejar cambio en la nube Neon PostgreSQL
            String sql = "UPDATE usuarios SET nombres = ?, apellidos = ? WHERE id = ?;";

            try (Connection conn = NeonDatabaseClient.getConnection();
                 PreparedStatement pstmt = conn.prepareStatement(sql)) {

                pstmt.setString(1, nuevoNombre);
                pstmt.setString(2, nuevoApellido);
                pstmt.setInt(3, usuarioId);

                pstmt.executeUpdate();
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    /**
     * Descarga los datos de Neon PostgreSQL y actualiza Room local.
     */
    public void descargarDeNeon(int usuarioId, SyncCallback callback) {
        AppExecutors.getInstance().diskIO().execute(() -> {
            String sql = "SELECT * FROM usuarios WHERE id = ?;";

            try (Connection conn = NeonDatabaseClient.getConnection();
                 PreparedStatement pstmt = conn.prepareStatement(sql)) {

                pstmt.setInt(1, usuarioId);
                ResultSet rs = pstmt.executeQuery();

                if (rs.next()) {
                    Usuario usuario = new Usuario();
                    usuario.setId(rs.getInt("id"));
                    usuario.setNombres(rs.getString("nombres"));
                    usuario.setApellidos(rs.getString("apellidos"));
                    usuario.setDui(rs.getString("dui"));
                    usuario.setTelefono(rs.getString("telefono"));
                    usuario.setEmail(rs.getString("email"));
                    usuario.setPassword(rs.getString("password"));
                    usuario.setDepartamento(rs.getString("departamento"));
                    usuario.setRol(rs.getString("rol"));
                    usuario.setImagenDuiUri(rs.getString("imagen_dui_uri"));

                    // Actualizar en Room local
                    db.usuarioDao().update(usuario);

                    AppExecutors.getInstance().mainThread().execute(() -> {
                        if (callback != null) callback.onSuccess(usuario);
                    });
                } else {
                    AppExecutors.getInstance().mainThread().execute(() -> {
                        if (callback != null) callback.onError("Usuario no encontrado en Neon PostgreSQL");
                    });
                }

            } catch (Exception e) {
                AppExecutors.getInstance().mainThread().execute(() -> {
                    if (callback != null) callback.onError("Error al conectar con Neon: " + e.getMessage());
                });
            }
        });
    }
}
