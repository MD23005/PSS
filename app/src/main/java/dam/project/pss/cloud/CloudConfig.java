package dam.project.pss.cloud;

public class CloudConfig {

    // URL base de tu API o servicio en la nube (ejemplo: Firebase, Supabase, AWS, API REST propia)
    public static final String BASE_URL = "https://api.tu-servidor-nube.com/";

    // Credenciales y Tokens de autenticación (Reemplaza con tus claves)
    public static final String API_KEY = "TU_API_KEY_AQUI";
    public static final String AUTH_TOKEN = "Bearer TU_TOKEN_DE_AUTENTICACION";

    // Configuración de tiempos de espera (en segundos)
    public static final long CONNECT_TIMEOUT_SECONDS = 30;
    public static final long READ_TIMEOUT_SECONDS = 30;

    // Habilitar o deshabilitar sincronización automática en la nube
    public static final boolean AUTO_SYNC_ENABLED = true;
}
