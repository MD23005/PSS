package dam.project.pss.database.entity;

import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity(tableName = "usuarios")
public class Usuario {

    @PrimaryKey(autoGenerate = true)
    private int id;

    private String nombres;
    private String apellidos;
    private String dui;
    private String telefono;
    private String email;
    private String password;
    private String departamento;
    private String rol;
    private String imagenDuiUri;

    // Constructor por defecto requerido por Room
    public Usuario() {
    }

    @Ignore
    public Usuario(String nombres, String apellidos, String dui, String telefono, String email, String password, String departamento, String rol, String imagenDuiUri) {
        this.nombres = nombres;
        this.apellidos = apellidos;
        this.dui = dui;
        this.telefono = telefono;
        this.email = email;
        this.password = password;
        this.departamento = departamento;
        this.rol = rol;
        this.imagenDuiUri = imagenDuiUri;
    }

    // Getters y Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombres() {
        return nombres;
    }

    public void setNombres(String nombres) {
        this.nombres = nombres;
    }

    public String getApellidos() {
        return apellidos;
    }

    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }

    public String getDui() {
        return dui;
    }

    public void setDui(String dui) {
        this.dui = dui;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getDepartamento() {
        return departamento;
    }

    public void setDepartamento(String departamento) {
        this.departamento = departamento;
    }

    public String getRol() {
        return rol;
    }

    public void setRol(String rol) {
        this.rol = rol;
    }

    public String getImagenDuiUri() {
        return imagenDuiUri;
    }

    public void setImagenDuiUri(String imagenDuiUri) {
        this.imagenDuiUri = imagenDuiUri;
    }
}
