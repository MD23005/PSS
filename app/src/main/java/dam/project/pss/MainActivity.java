package dam.project.pss;

import android.content.Intent;
import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {

    private AppCompatButton btnVendedor, btnComprador, btnAdministrador;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        btnVendedor = findViewById(R.id.btnVendedor);
        btnComprador = findViewById(R.id.btnComprador);
        btnAdministrador = findViewById(R.id.btnAdministrador);

        btnVendedor.setOnClickListener(v -> iniciarLogin("vendedor"));
        btnComprador.setOnClickListener((v -> iniciarLogin("comprador")));
        btnAdministrador.setOnClickListener(v -> iniciarLogin("admin"));
    }

    private void iniciarLogin(String rol){
        Intent login = new Intent(this, LoginActivity.class);
        login.putExtra("rol", rol);
        startActivity(login);
    }
}