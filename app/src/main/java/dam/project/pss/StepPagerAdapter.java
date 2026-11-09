package dam.project.pss;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.adapter.FragmentStateAdapter;

public class StepPagerAdapter extends FragmentStateAdapter {

    public StepPagerAdapter(@NonNull Fragment fragment) {
        super(fragment);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position) {
            case 1:
                return new Step2Fragment();
            case 2:
                return new Step3Fragment();
            case 0:
            default:
                return new Step1Fragment();
        }
    }

    @Override
    public int getItemCount() {
        return 3;
    }
}
