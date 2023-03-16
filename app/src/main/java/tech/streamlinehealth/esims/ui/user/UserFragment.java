package tech.streamlinehealth.esims.ui.user;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;

import com.android.volley.Request;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;

import tech.streamlinehealth.esims.R;
import tech.streamlinehealth.esims.helpers.DataRouter;
import tech.streamlinehealth.esims.helpers.HelperFunctions;
import tech.streamlinehealth.esims.helpers.PreferenceManager;

public class UserFragment extends Fragment {
    private UserViewModel userViewModel;
    String id;
    PreferenceManager preferenceManager;
    HelperFunctions helperFunctions;
    String first_name, last_name, username, phone, email;
    TextView firstname, lastname, user_name, phone_, email_;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        userViewModel =
                ViewModelProviders.of(this).get(UserViewModel.class);
        View root = inflater.inflate(R.layout.fragment_user, container, false);
        firstname = root.findViewById(R.id.first_name);
        lastname = root.findViewById(R.id.last_name);
        user_name = root.findViewById(R.id.username);
        phone_ = root.findViewById(R.id.phone);
        email_ = root.findViewById(R.id.email);

        userViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                firstname.setText(s);
            }
        });
        helperFunctions = new HelperFunctions(getActivity());
        preferenceManager = new PreferenceManager(getActivity());
        id = preferenceManager.getUserId();
        DataRouter router = new DataRouter(getActivity());
        String network_address = router.ip_address + "sims_patients/get_user_details/" + id;

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, network_address, null,
                response -> {

                    try {
                        first_name = response.getString("first_name");
                        last_name = response.getString("last_name");
                        username = response.getString("username");
                        phone = response.getString("phone");
                        email = response.getString("email");

                        firstname.setText(first_name);
                        lastname.setText(last_name);
                        user_name.setText(username);
                        phone_.setText(phone);
                        email_.setText(email);

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }, error -> {
            helperFunctions.genericDialog("Something went wrong. Please try again later.");
        });

        Volley.newRequestQueue(getActivity()).add(request);

        return root;
    }
}
