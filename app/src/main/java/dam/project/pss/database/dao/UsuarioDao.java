package dam.project.pss.database.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import dam.project.pss.database.entity.Usuario;

@Dao
public interface UsuarioDao {

    @Insert
    long insert(Usuario usuario);

    @Update
    void update(Usuario usuario);

    @Delete
    void delete(Usuario usuario);

    @Query("SELECT * FROM usuarios WHERE id = :id")
    Usuario getById(int id);

    @Query("SELECT * FROM usuarios WHERE email = :email AND password = :password LIMIT 1")
    Usuario login(String email, String password);

    @Query("SELECT * FROM usuarios WHERE email = :email LIMIT 1")
    Usuario getByEmail(String email);

    @Query("SELECT * FROM usuarios ORDER BY id DESC")
    List<Usuario> getAllUsuarios();
}
