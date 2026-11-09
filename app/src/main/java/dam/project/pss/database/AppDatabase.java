package dam.project.pss.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import dam.project.pss.database.dao.UsuarioDao;
import dam.project.pss.database.entity.Usuario;

@Database(entities = {Usuario.class}, version = 1, exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {

    private static final String DATABASE_NAME = "pss_database";
    private static volatile AppDatabase instance;

    public abstract UsuarioDao usuarioDao();

    public static synchronized AppDatabase getInstance(Context context) {
        if (instance == null) {
            instance = Room.databaseBuilder(
                    context.getApplicationContext(),
                    AppDatabase.class,
                    DATABASE_NAME
            ).fallbackToDestructiveMigration().build();
        }
        return instance;
    }
}
