package dam.project.pss;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

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

        String selectedRol = null;
        if (getArguments() != null) {
            selectedRol = getArguments().getString(ARG_ROL);
        }
        if (selectedRol == null && getActivity() != null && getActivity().getIntent() != null) {
            selectedRol = getActivity().getIntent().getStringExtra("rol");
        }

        if (selectedRol != null && tvRol != null) {
            tvRol.setText(selectedRol);
        }

        return view;
    }
}
