package tech.streamlinehealth.esims.ui.settings;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import tech.streamlinehealth.esims.R;
import tech.streamlinehealth.esims.helpers.DataRouter;
import tech.streamlinehealth.esims.helpers.HelperFunctions;
import tech.streamlinehealth.esims.helpers.PreferenceManager;

public class SettingFragment extends Fragment {
    String id, oldPwd, newPwd, confirmPwd;
    private SettingViewModel settingViewModel;
    PreferenceManager preferenceManager;
    HelperFunctions helperFunctions;
    EditText old_pwd, new_pwd, confirm_pwd;
    Button change_pwd;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        settingViewModel =
                ViewModelProviders.of(this).get(SettingViewModel.class);
        View root = inflater.inflate(R.layout.fragment_setting, container, false);
        final TextView textView = root.findViewById(R.id.text_slideshow);
        settingViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });

        old_pwd = root.findViewById(R.id.old_password);
        new_pwd = root.findViewById(R.id.new_password);
        confirm_pwd = root.findViewById(R.id.confirm_password);

        helperFunctions = new HelperFunctions(getActivity());
        preferenceManager = new PreferenceManager(getActivity());
        id = preferenceManager.getUserId();
        DataRouter router = new DataRouter(getActivity());

        change_pwd = root.findViewById(R.id.reset);
        change_pwd.setOnClickListener(v -> {
            oldPwd = old_pwd.getText().toString();
            newPwd = new_pwd.getText().toString();
            confirmPwd = confirm_pwd.getText().toString();


            if(oldPwd.isEmpty()){
                helperFunctions.genericDialog("Please fill in your current password");
                return;
            }

            if(newPwd.isEmpty()){
                helperFunctions.genericDialog("Please fill in your new password");
                return;
            }

            if(newPwd.equals(confirmPwd)){
                router.changePassword(id, newPwd, oldPwd);
            }else{
                helperFunctions.genericDialog("Passwords do not match");
                return;
            }

        });
        return root;
    }
}
