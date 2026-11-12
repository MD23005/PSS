package dam.project.pss;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatButton;
import androidx.fragment.app.Fragment;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputEditText;

public class Step1Fragment extends Fragment {

    private RegistrationViewModel viewModel;
    private ImageView imgDuiPreview;

    private final ActivityResultLauncher<String> selectImageLauncher =
            registerForActivityResult(new ActivityResultContracts.GetContent(), uri -> {
                if (uri != null) {
                    if (getContext() != null) {
                        try {
                            getContext().getContentResolver().takePersistableUriPermission(
                                    uri, Intent.FLAG_GRANT_READ_URI_PERMISSION
                            );
                        } catch (Exception ignored) {
                        }
                    }

                    if (viewModel != null) {
                        viewModel.setImagenDuiUri(uri.toString());
                    }

                    if (imgDuiPreview != null) {
                        imgDuiPreview.setVisibility(View.VISIBLE);
                        imgDuiPreview.setImageURI(uri);
                    }

                    Toast.makeText(getContext(), "Imagen del DUI seleccionada", Toast.LENGTH_SHORT).show();
                }
            });

    public Step1Fragment() {
        // Required empty public constructor
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.step1_fragment, container, false);

        if (getParentFragment() instanceof RegisterFragment) {
            viewModel = ((RegisterFragment) getParentFragment()).getViewModel();
        }

        TextInputEditText edtNombres = view.findViewById(R.id.edtNombres);
        TextInputEditText edtApellidos = view.findViewById(R.id.edtApellidos);
        TextInputEditText edtDui = view.findViewById(R.id.edtDui);
        TextInputEditText edtPass = view.findViewById(R.id.edtPass);
        AppCompatButton btnSubirDui = view.findViewById(R.id.btnSubirDui);
        imgDuiPreview = view.findViewById(R.id.imgDuiPreview);
        FloatingActionButton fabSiguiente = view.findViewById(R.id.fabSiguientePaso2);

        if (viewModel != null) {
            if (edtNombres != null) edtNombres.setText(viewModel.getNombres());
            if (edtApellidos != null) edtApellidos.setText(viewModel.getApellidos());
            if (edtDui != null) edtDui.setText(viewModel.getDui());
            if (edtPass != null) edtPass.setText(viewModel.getPassword());

            if (viewModel.getImagenDuiUri() != null && !viewModel.getImagenDuiUri().isEmpty() && imgDuiPreview != null) {
                imgDuiPreview.setVisibility(View.VISIBLE);
                imgDuiPreview.setImageURI(Uri.parse(viewModel.getImagenDuiUri()));
            }
        }

        if (btnSubirDui != null) {
            btnSubirDui.setOnClickListener(v -> selectImageLauncher.launch("image/*"));
        }

        if (fabSiguiente != null) {
            fabSiguiente.setOnClickListener(v -> {
                if (viewModel != null) {
                    if (edtNombres != null && edtNombres.getText() != null) {
                        viewModel.setNombres(edtNombres.getText().toString());
                    }
                    if (edtApellidos != null && edtApellidos.getText() != null) {
                        viewModel.setApellidos(edtApellidos.getText().toString());
                    }
                    if (edtDui != null && edtDui.getText() != null) {
                        viewModel.setDui(edtDui.getText().toString());
                    }
                    if (edtPass != null && edtPass.getText() != null) {
                        viewModel.setPassword(edtPass.getText().toString());
                    }
                }

                if (getParentFragment() instanceof RegisterFragment) {
                    ((RegisterFragment) getParentFragment()).navigateToStep(1);
                }
            });
        }

        return view;
    }
}
