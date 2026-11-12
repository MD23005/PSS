package dam.project.pss;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputEditText;

public class Step2Fragment extends Fragment {

    private RegistrationViewModel viewModel;

    public Step2Fragment() {
        // Required empty public constructor
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.step2_fragment, container, false);

        if (getParentFragment() instanceof RegisterFragment) {
            viewModel = ((RegisterFragment) getParentFragment()).getViewModel();
        }

        TextInputEditText edtTelefono = view.findViewById(R.id.edtTelefono);
        TextInputEditText edtEmail = view.findViewById(R.id.edtEmail);
        AutoCompleteTextView actvDepartamento = view.findViewById(R.id.actvDepartamento);
        FloatingActionButton fabAtras = view.findViewById(R.id.fabAtrasPaso2);
        FloatingActionButton fabSiguiente = view.findViewById(R.id.fabSiguientePaso3);

        if (actvDepartamento != null && getContext() != null) {
            ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
                    getContext(),
                    R.array.departamentos_el_salvador,
                    android.R.layout.simple_dropdown_item_1line
            );
            actvDepartamento.setAdapter(adapter);

            if (viewModel != null && viewModel.getDepartamento() != null && !viewModel.getDepartamento().isEmpty()) {
                actvDepartamento.setText(viewModel.getDepartamento(), false);
            } else {
                CharSequence itemDefault = adapter.getItem(9); // San Salvador
                if (itemDefault != null) {
                    actvDepartamento.setText(itemDefault.toString(), false);
                }
            }
        }

        if (viewModel != null) {
            if (edtTelefono != null) edtTelefono.setText(viewModel.getTelefono());
            if (edtEmail != null) edtEmail.setText(viewModel.getEmail());
        }

        if (fabAtras != null) {
            fabAtras.setOnClickListener(v -> {
                guardarDatosFormulario(edtTelefono, edtEmail, actvDepartamento);
                if (getParentFragment() instanceof RegisterFragment) {
                    ((RegisterFragment) getParentFragment()).navigateToStep(0);
                }
            });
        }

        if (fabSiguiente != null) {
            fabSiguiente.setOnClickListener(v -> {
                guardarDatosFormulario(edtTelefono, edtEmail, actvDepartamento);
                if (getParentFragment() instanceof RegisterFragment) {
                    ((RegisterFragment) getParentFragment()).navigateToStep(2);
                }
            });
        }

        return view;
    }

    private void guardarDatosFormulario(TextInputEditText edtTelefono, TextInputEditText edtEmail, AutoCompleteTextView actvDepartamento) {
        if (viewModel != null) {
            if (edtTelefono != null && edtTelefono.getText() != null) {
                viewModel.setTelefono(edtTelefono.getText().toString());
            }
            if (edtEmail != null && edtEmail.getText() != null) {
                viewModel.setEmail(edtEmail.getText().toString());
            }
            if (actvDepartamento != null && actvDepartamento.getText() != null) {
                viewModel.setDepartamento(actvDepartamento.getText().toString());
            }
        }
    }
}
