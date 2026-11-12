package dam.project.pss.cloud.api;

import java.util.List;

import dam.project.pss.cloud.dto.UsuarioCloudDto;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;

public interface CloudApiService {

    @POST("usuarios")
    Call<UsuarioCloudDto> createUsuario(@Body UsuarioCloudDto usuario);

    @PUT("usuarios/{id}")
    Call<UsuarioCloudDto> updateUsuario(@Path("id") int id, @Body UsuarioCloudDto usuario);

    @DELETE("usuarios/{id}")
    Call<Void> deleteUsuario(@Path("id") int id);

    @GET("usuarios/{id}")
    Call<UsuarioCloudDto> getUsuarioById(@Path("id") int id);

    @GET("usuarios")
    Call<List<UsuarioCloudDto>> getAllUsuarios();
}
