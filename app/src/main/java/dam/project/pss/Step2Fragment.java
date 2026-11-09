package dam.project.pss;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class Step2Fragment extends Fragment {

    public Step2Fragment() {
        // Required empty public constructor
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.step2_fragment, container, false);

        FloatingActionButton fabAtras = view.findViewById(R.id.fabAtrasPaso2);
        FloatingActionButton fabSiguiente = view.findViewById(R.id.fabSiguientePaso3);

        if (fabAtras != null) {
            fabAtras.setOnClickListener(v -> {
                if (getParentFragment() instanceof RegisterFragment) {
                    ((RegisterFragment) getParentFragment()).navigateToStep(0);
                }
            });
        }

        if (fabSiguiente != null) {
            fabSiguiente.setOnClickListener(v -> {
                if (getParentFragment() instanceof RegisterFragment) {
                    ((RegisterFragment) getParentFragment()).navigateToStep(2);
                }
            });
        }

        return view;
    }
}
