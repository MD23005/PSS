package dam.project.pss;

import android.content.Context;

import androidx.lifecycle.ViewModel;

import dam.project.pss.cloud.postgres.UsuarioNeonSyncRepository;
import dam.project.pss.database.entity.Usuario;

public class RegistrationViewModel extends ViewModel {

    private String nombres = "";
    private String apellidos = "";
    private String dui = "";
    private String telefono = "";
    private String email = "";
    private String password = "";
    private String departamento = "San Salvador";
    private String pin = "";
    private String rol = "comprador";
    private String imagenDuiUri = "";

    public interface RegistrationCallback {
        void onSuccess(Usuario usuario);
        void onError(String message);
    }

    // Getters y Setters
    public String getNombres() { return nombres; }
    public void setNombres(String nombres) { this.nombres = nombres; }

    public String getApellidos() { return apellidos; }
    public void setApellidos(String apellidos) { this.apellidos = apellidos; }

    public String getDui() { return dui; }
    public void setDui(String dui) { this.dui = dui; }

    public String getTelefono() { return telefono; }
    public void setTelefono(String telefono) { this.telefono = telefono; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }

    public String getDepartamento() { return departamento; }
    public void setDepartamento(String departamento) { this.departamento = departamento; }

    public String getPin() { return pin; }
    public void setPin(String pin) { this.pin = pin; }

    public String getRol() { return rol; }
    public void setRol(String rol) { this.rol = rol; }

    public String getImagenDuiUri() { return imagenDuiUri; }
    public void setImagenDuiUri(String imagenDuiUri) { this.imagenDuiUri = imagenDuiUri; }

    /**
     * Realiza el registro completo guardando en Room (local) y sincronizando con Neon (nube).
     */
    public void registrarUsuario(Context context, RegistrationCallback callback) {
        if (nombres.trim().isEmpty() || apellidos.trim().isEmpty()) {
            if (callback != null) callback.onError("Por favor complete sus nombres y apellidos en el Paso 1");
            return;
        }

        if (dui.trim().isEmpty()) {
            if (callback != null) callback.onError("Por favor ingrese su número de DUI en el Paso 1");
            return;
        }

        if (password.trim().isEmpty()) {
            if (callback != null) callback.onError("Por favor ingrese una contraseña en el Paso 1");
            return;
        }

        if (telefono.trim().isEmpty() || email.trim().isEmpty()) {
            if (callback != null) callback.onError("Por favor complete su teléfono y correo en el Paso 2");
            return;
        }

        if (pin == null || pin.length() < 4) {
            if (callback != null) callback.onError("Por favor defina un PIN de 4 dígitos en el Paso 4");
            return;
        }

        // Construir entidad de Usuario
        Usuario usuario = new Usuario(
                nombres.trim(),
                apellidos.trim(),
                dui.trim(),
                telefono.trim(),
                email.trim(),
                password.trim(),
                departamento,
                rol,
                imagenDuiUri
        );

        UsuarioNeonSyncRepository syncRepository = new UsuarioNeonSyncRepository(context);

        // Crear la tabla si no existe e insertar/sincronizar el usuario
        syncRepository.crearTablaSiNoExiste();
        syncRepository.sincronizarUsuarioANeon(usuario, new UsuarioNeonSyncRepository.SyncCallback() {
            @Override
            public void onSuccess(Usuario usuarioSincronizado) {
                if (callback != null) {
                    callback.onSuccess(usuarioSincronizado);
                }
            }

            @Override
            public void onError(String errorMessage) {
                if (callback != null) {
                    callback.onError(errorMessage);
                }
            }
        });
    }
}
