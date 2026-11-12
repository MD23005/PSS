package dam.project.pss.cloud.dto;

import com.google.gson.annotations.SerializedName;

import dam.project.pss.database.entity.Usuario;

public class UsuarioCloudDto {

    @SerializedName("id")
    private int id;

    @SerializedName("nombres")
    private String nombres;

    @SerializedName("apellidos")
    private String apellidos;

    @SerializedName("dui")
    private String dui;

    @SerializedName("telefono")
    private String telefono;

    @SerializedName("email")
    private String email;

    @SerializedName("password")
    private String password;

    @SerializedName("departamento")
    private String departamento;

    @SerializedName("rol")
    private String rol;

    @SerializedName("imagen_dui_uri")
    private String imagenDuiUri;

    public UsuarioCloudDto() {
    }

    public UsuarioCloudDto(Usuario usuario) {
        if (usuario != null) {
            this.id = usuario.getId();
            this.nombres = usuario.getNombres();
            this.apellidos = usuario.getApellidos();
            this.dui = usuario.getDui();
            this.telefono = usuario.getTelefono();
            this.email = usuario.getEmail();
            this.password = usuario.getPassword();
            this.departamento = usuario.getDepartamento();
            this.rol = usuario.getRol();
            this.imagenDuiUri = usuario.getImagenDuiUri();
        }
    }

    public Usuario toEntity() {
        Usuario usuario = new Usuario();
        usuario.setId(this.id);
        usuario.setNombres(this.nombres);
        usuario.setApellidos(this.apellidos);
        usuario.setDui(this.dui);
        usuario.setTelefono(this.telefono);
        usuario.setEmail(this.email);
        usuario.setPassword(this.password);
        usuario.setDepartamento(this.departamento);
        usuario.setRol(this.rol);
        usuario.setImagenDuiUri(this.imagenDuiUri);
        return usuario;
    }

    // Getters y Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

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

    public String getRol() { return rol; }
    public void setRol(String rol) { this.rol = rol; }

    public String getImagenDuiUri() { return imagenDuiUri; }
    public void setImagenDuiUri(String imagenDuiUri) { this.imagenDuiUri = imagenDuiUri; }
}
