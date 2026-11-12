package dam.project.pss;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.andrognito.pinlockview.IndicatorDots;
import com.andrognito.pinlockview.PinLockListener;
import com.andrognito.pinlockview.PinLockView;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import dam.project.pss.database.entity.Usuario;

public class Step4Fragment extends Fragment {

    private String currentPin = "";
    private RegistrationViewModel viewModel;

    public Step4Fragment() {
        // Required empty public constructor
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.step4_fragment, container, false);

        if (getParentFragment() instanceof RegisterFragment) {
            viewModel = ((RegisterFragment) getParentFragment()).getViewModel();
        }

        PinLockView pinLockView = view.findViewById(R.id.pin_lock_view);
        IndicatorDots indicatorDots = view.findViewById(R.id.indicator_dots);
        FloatingActionButton fabAtras = view.findViewById(R.id.fabAtrasPaso4);
        MaterialButton btnRegistrar = view.findViewById(R.id.btnRegistrarUsuario);

        if (pinLockView != null && indicatorDots != null) {
            pinLockView.attachIndicatorDots(indicatorDots);
            pinLockView.setPinLockListener(new PinLockListener() {
                @Override
                public void onComplete(String pin) {
                    currentPin = pin;
                }

                @Override
                public void onEmpty() {
                    currentPin = "";
                }

                @Override
                public void onPinChange(int pinLength, String intermediatePin) {
                    currentPin = intermediatePin;
                }
            });
        }

        if (fabAtras != null) {
            fabAtras.setOnClickListener(v -> {
                if (getParentFragment() instanceof RegisterFragment) {
                    ((RegisterFragment) getParentFragment()).navigateToStep(2);
                }
            });
        }

        if (btnRegistrar != null) {
            btnRegistrar.setOnClickListener(v -> {
                if (currentPin == null || currentPin.length() < 4) {
                    Toast.makeText(getContext(), "Por favor ingrese un PIN de 4 dígitos", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (viewModel != null) {
                    viewModel.setPin(currentPin);
                    Toast.makeText(getContext(), "Procesando registro...", Toast.LENGTH_SHORT).show();

                    viewModel.registrarUsuario(getContext(), new RegistrationViewModel.RegistrationCallback() {
                        @Override
                        public void onSuccess(Usuario usuario) {
                            Toast.makeText(getContext(), "¡Usuario " + usuario.getNombres() + " registrado exitosamente en Local y en Neon Nube!", Toast.LENGTH_LONG).show();
                            if (getActivity() != null) {
                                getActivity().getOnBackPressedDispatcher().onBackPressed();
                            }
                        }

                        @Override
                        public void onError(String message) {
                            Toast.makeText(getContext(), "Error: " + message, Toast.LENGTH_LONG).show();
                        }
                    });
                }
            });
        }

        return view;
    }
}
