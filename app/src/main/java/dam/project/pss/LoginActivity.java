package dam.project.pss;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class LoginActivity extends AppCompatActivity {

    private String rol;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_container);

        if (getIntent() != null) {
            rol = getIntent().getStringExtra("rol");
        }

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomNavigationView);

        // Cargar por defecto el fragmento de Login
        if (savedInstanceState == null) {
            cambiarFragmento(LoginFragment.newInstance(rol));
        }

        // Listener para cambiar de fragmento al interactuar con el menú inferior
        bottomNavigationView.setOnItemSelectedListener(item -> {
            int itemId = item.getItemId();
            if (itemId == R.id.nav_login) {
                cambiarFragmento(LoginFragment.newInstance(rol));
                return true;
            } else if (itemId == R.id.nav_registro) {
                cambiarFragmento(RegisterFragment.newInstance());
                return true;
            }
            return false;
        });
    }

    private void cambiarFragmento(Fragment fragment) {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.fragmentContainer, fragment)
                .commit();
    }
}
