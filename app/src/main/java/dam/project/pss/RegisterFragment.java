package dam.project.pss;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.viewpager2.widget.ViewPager2;

import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

public class RegisterFragment extends Fragment {

    private ViewPager2 viewPager;
    private RegistrationViewModel viewModel;

    public RegisterFragment() {
        // Required empty public constructor
    }

    public static RegisterFragment newInstance() {
        return new RegisterFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.register_activity, container, false);

        viewModel = new ViewModelProvider(this).get(RegistrationViewModel.class);

        if (getActivity() != null && getActivity().getIntent() != null) {
            String rolExtra = getActivity().getIntent().getStringExtra("rol");
            if (rolExtra != null && !rolExtra.isEmpty()) {
                viewModel.setRol(rolExtra);
            }
        }

        TabLayout tabLayout = view.findViewById(R.id.tabLayoutSteps);
        viewPager = view.findViewById(R.id.viewPager);

        StepPagerAdapter adapter = new StepPagerAdapter(this);
        viewPager.setAdapter(adapter);

        new TabLayoutMediator(tabLayout, viewPager, (tab, position) -> {
            switch (position) {
                case 0:
                    tab.setText("Paso 1");
                    break;
                case 1:
                    tab.setText("Paso 2");
                    break;
                case 2:
                    tab.setText("Paso 3");
                    break;
                case 3:
                    tab.setText("Paso 4");
                    break;
            }
        }).attach();

        return view;
    }

    public RegistrationViewModel getViewModel() {
        return viewModel;
    }

    public void navigateToStep(int stepIndex) {
        if (viewPager != null && stepIndex >= 0 && stepIndex < 4) {
            viewPager.setCurrentItem(stepIndex, true);
        }
    }
}
