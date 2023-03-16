package tech.streamlinehealth.esims.users;

import android.content.Intent;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.appcompat.widget.SearchView;

import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import tech.streamlinehealth.esims.R;
import tech.streamlinehealth.esims.adapters.UsersAdapter;
import tech.streamlinehealth.esims.helpers.DataRouter;
import tech.streamlinehealth.esims.helpers.HelperFunctions;
import tech.streamlinehealth.esims.models.Users;

public class ViewUsersActivity extends AppCompatActivity implements UsersAdapter.UsersAdapterListener {

    private List<Users> usersList;
    DataRouter router;
    private UsersAdapter mAdapter;
    RecyclerView recyclerView;
    HelperFunctions helperFunctions;
    FloatingActionButton fab_add_user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_users);

        // set the back navigation on the action bar
        Objects.requireNonNull(getSupportActionBar()).setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        recyclerView = findViewById(R.id.recycler_active_users);
        usersList = new ArrayList<>();
        mAdapter = new UsersAdapter(usersList, this);

        helperFunctions = new HelperFunctions(this);

        router = new DataRouter(this);

        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(mAdapter);

        SearchView searchView = findViewById(R.id.search_active_users);

        searchView.setQueryHint("Search users...");

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                // filter recycler view when query submitted
                mAdapter.getFilter().filter(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String query) {
                // filter recycler view when text is changed
                mAdapter.getFilter().filter(query);
                return false;
            }
        });

        fab_add_user = findViewById(R.id.fab_add_user);

        fab_add_user.setOnClickListener(view -> {
            startActivity(new Intent(ViewUsersActivity.this, CreateUserActivity.class));
        });

        fetchUsers();
    }

    private void fetchUsers() {
        helperFunctions.genericProgressBar("Fetching active users...");
        String url = router.ip_address + "sims_patients/view_active_users";

        JsonArrayRequest request = new JsonArrayRequest(url,
                response -> {
                    helperFunctions.stopProgressBar();

                    for (int i = 0; i < response.length(); i++) {
                        try {
                            JSONObject jsonobject = response.getJSONObject(i);
                            usersList.add(new Users(jsonobject.getString("id"), jsonobject.getString("first_name"), jsonobject.getString("last_name"), jsonobject.getString("phone")));
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }

                    // refreshing recycler view
                    mAdapter.notifyDataSetChanged();
                }, error -> {
                    helperFunctions.stopProgressBar();
                    helperFunctions.genericDialog("Something went wrong. Please try again later");
                });

        Volley.newRequestQueue(this).add(request);
    }

    @Override
    public void onFormSelected(Users user) {
        Intent intent = new Intent(ViewUsersActivity.this, ViewUserDetailsActivity.class);
        intent.putExtra("id", user.getId());
        startActivity(intent);
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

}
