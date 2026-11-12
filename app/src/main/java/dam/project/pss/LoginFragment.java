package dam.project.pss;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatButton;
import androidx.fragment.app.Fragment;

import com.google.android.material.textfield.TextInputEditText;

import dam.project.pss.database.AppDatabase;
import dam.project.pss.database.AppExecutors;
import dam.project.pss.database.entity.Usuario;

public class LoginFragment extends Fragment {

    private static final String ARG_ROL = "rol";

    public LoginFragment() {
        // Required empty public constructor
    }

    public static LoginFragment newInstance(String rol) {
        LoginFragment fragment = new LoginFragment();
        Bundle args = new Bundle();
        args.putString(ARG_ROL, rol);
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.login_activty, container, false);

        TextView tvRol = view.findViewById(R.id.tvRol);
        TextInputEditText edtEmail = view.findViewById(R.id.edtEmail);
        TextInputEditText edtPass = view.findViewById(R.id.edtPass);
        CheckBox chkBot = view.findViewById(R.id.chkBot);
        AppCompatButton btnLogin = view.findViewById(R.id.btnLogin);

        String selectedRol = getArguments() != null ? getArguments().getString(ARG_ROL) : null;
        if (selectedRol == null && getActivity() != null && getActivity().getIntent() != null) {
            selectedRol = getActivity().getIntent().getStringExtra("rol");
        }

        if (selectedRol != null && tvRol != null) {
            tvRol.setText(selectedRol);
        }

        if (btnLogin != null) {
            btnLogin.setOnClickListener(v -> {
                if (chkBot != null && !chkBot.isChecked()) {
                    Toast.makeText(getContext(), "Por favor confirme que no es un robot", Toast.LENGTH_SHORT).show();
                    return;
                }

                String email = edtEmail != null && edtEmail.getText() != null ? edtEmail.getText().toString().trim() : "";
                String password = edtPass != null && edtPass.getText() != null ? edtPass.getText().toString().trim() : "";

                if (email.isEmpty() || password.isEmpty()) {
                    Toast.makeText(getContext(), "Por favor ingrese su correo y contraseña", Toast.LENGTH_SHORT).show();
                    return;
                }

                Toast.makeText(getContext(), "Verificando credenciales...", Toast.LENGTH_SHORT).show();

                // Consultar únicamente la base de datos local Room
                AppExecutors.getInstance().diskIO().execute(() -> {
                    if (getContext() == null) return;
                    AppDatabase db = AppDatabase.getInstance(getContext());
                    Usuario usuario = db.usuarioDao().login(email, password);

                    AppExecutors.getInstance().mainThread().execute(() -> {
                        if (usuario != null) {
                            Toast.makeText(getContext(), "¡Bienvenido/a " + usuario.getNombres() + " " + usuario.getApellidos() + "!", Toast.LENGTH_LONG).show();
                        } else {
                            Toast.makeText(getContext(), "Credenciales incorrectas. Verifique correo y contraseña.", Toast.LENGTH_LONG).show();
                        }
                    });
                });
            });
        }

        return view;
    }
}
