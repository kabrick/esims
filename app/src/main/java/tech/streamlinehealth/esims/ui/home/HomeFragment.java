package tech.streamlinehealth.esims.ui.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;

import java.util.Objects;

import tech.streamlinehealth.esims.R;
import tech.streamlinehealth.esims.helpers.PreferenceManager;

public class HomeFragment extends Fragment {
    private HomeViewModel homeViewModel;
    CardView clinical_data_card, users_card;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        homeViewModel =
                ViewModelProviders.of(this).get(HomeViewModel.class);
        View root = inflater.inflate(R.layout.fragment_home, container, false);
//        final TextView textView = root.findViewById(R.id.text_home);
//        homeViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
//            @Override
////            public void onChanged(@Nullable String s) {
////                textView.setText(s);
////            }
//        });
        clinical_data_card = root.findViewById(R.id.clinical_data_card);
        users_card = root.findViewById(R.id.users_card);

        if (new PreferenceManager(Objects.requireNonNull(getContext())).isUserAdmin()){
            clinical_data_card.setVisibility(View.VISIBLE);
            users_card.setVisibility(View.VISIBLE);
        }
        return root;
    }

}
