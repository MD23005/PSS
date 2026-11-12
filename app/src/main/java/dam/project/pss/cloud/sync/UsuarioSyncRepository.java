package dam.project.pss.cloud.sync;

import android.content.Context;

import dam.project.pss.cloud.CloudConfig;
import dam.project.pss.cloud.api.RetrofitClient;
import dam.project.pss.cloud.dto.UsuarioCloudDto;
import dam.project.pss.database.AppDatabase;
import dam.project.pss.database.AppExecutors;
import dam.project.pss.database.entity.Usuario;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UsuarioSyncRepository {

    private final AppDatabase db;

    public interface SyncCallback {
        void onSuccess(Usuario usuario);
        void onError(String errorMessage);
    }

    public UsuarioSyncRepository(Context context) {
        this.db = AppDatabase.getInstance(context);
    }

    /**
     * Guarda o actualiza un usuario en la BD local Room y lo sincroniza con la nube.
     */
    public void guardarONavegarLocalYNube(Usuario usuario, SyncCallback callback) {
        AppExecutors.getInstance().diskIO().execute(() -> {
            if (usuario.getId() > 0) {
                db.usuarioDao().update(usuario);
            } else {
                long newId = db.usuarioDao().insert(usuario);
                usuario.setId((int) newId);
            }

            AppExecutors.getInstance().mainThread().execute(() -> {
                if (callback != null) {
                    callback.onSuccess(usuario);
                }
            });

            if (CloudConfig.AUTO_SYNC_ENABLED) {
                sincronizarConNube(usuario);
            }
        });
    }

    /**
     * Ejemplo de cómo actualizar únicamente el nombre/apellido en local y sincronizarlo con la nube.
     */
    public void actualizarNombreUsuario(int usuarioId, String nuevoNombre, String nuevoApellido, SyncCallback callback) {
        AppExecutors.getInstance().diskIO().execute(() -> {
            Usuario usuario = db.usuarioDao().getById(usuarioId);
            if (usuario != null) {
                usuario.setNombres(nuevoNombre);
                usuario.setApellidos(nuevoApellido);

                // 1. Guardar primero en Room local
                db.usuarioDao().update(usuario);

                AppExecutors.getInstance().mainThread().execute(() -> {
                    if (callback != null) {
                        callback.onSuccess(usuario);
                    }
                });

                // 2. Sincronizar en segundo plano con la nube
                if (CloudConfig.AUTO_SYNC_ENABLED) {
                    sincronizarConNube(usuario);
                }
            } else {
                AppExecutors.getInstance().mainThread().execute(() -> {
                    if (callback != null) {
                        callback.onError("Usuario no encontrado en la base de datos local");
                    }
                });
            }
        });
    }

    /**
     * Envía los datos del usuario a la API en la nube (POST o PUT).
     */
    private void sincronizarConNube(Usuario usuario) {
        UsuarioCloudDto dto = new UsuarioCloudDto(usuario);
        Call<UsuarioCloudDto> call = RetrofitClient.getApiService().updateUsuario(usuario.getId(), dto);

        call.enqueue(new Callback<UsuarioCloudDto>() {
            @Override
            public void onResponse(Call<UsuarioCloudDto> call, Response<UsuarioCloudDto> response) {
                if (response.isSuccessful() && response.body() != null) {
                    // Éxito al sincronizar con la nube
                } else {
                    // Manejar error HTTP si es necesario
                }
            }

            @Override
            public void onFailure(Call<UsuarioCloudDto> call, Throwable t) {
                // Si no hay conexión o falla la red, la BD local mantiene los cambios seguros
            }
        });
    }

    /**
     * Descarga los datos de la nube y actualiza Room local.
     */
    public void descargarYSincronizarDeNube(int usuarioId, SyncCallback callback) {
        RetrofitClient.getApiService().getUsuarioById(usuarioId).enqueue(new Callback<UsuarioCloudDto>() {
            @Override
            public void onResponse(Call<UsuarioCloudDto> call, Response<UsuarioCloudDto> response) {
                if (response.isSuccessful() && response.body() != null) {
                    Usuario usuarioCloud = response.body().toEntity();
                    AppExecutors.getInstance().diskIO().execute(() -> {
                        db.usuarioDao().update(usuarioCloud);
                        AppExecutors.getInstance().mainThread().execute(() -> {
                            if (callback != null) {
                                callback.onSuccess(usuarioCloud);
                            }
                        });
                    });
                }
            }

            @Override
            public void onFailure(Call<UsuarioCloudDto> call, Throwable t) {
                if (callback != null) {
                    callback.onError("Error al descargar de la nube: " + t.getMessage());
                }
            }
        });
    }
}
