package tech.streamlinehealth.esims.adapters;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import tech.streamlinehealth.esims.R;
import tech.streamlinehealth.esims.models.Users;

public class UsersAdapter extends RecyclerView.Adapter<UsersAdapter.MyViewHolder>
        implements Filterable {
    private List<Users> userList;
    private List<Users> userListFiltered;
    private UsersAdapter.UsersAdapterListener listener;

    class MyViewHolder extends RecyclerView.ViewHolder {
        TextView user_name_feed, user_contact_feed;

        MyViewHolder(View view) {
            super(view);
            user_name_feed = view.findViewById(R.id.user_name_feed);
            user_contact_feed = view.findViewById(R.id.user_contact_feed);

            view.setOnClickListener(view1 -> listener.onFormSelected(userListFiltered.get(getAdapterPosition())));
        }
    }

    public UsersAdapter(List<Users> userList, UsersAdapter.UsersAdapterListener listener) {
        this.listener = listener;
        this.userList = userList;
        this.userListFiltered = userList;
    }

    @NonNull
    @Override
    public UsersAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.row_item_users, parent, false);

        return new UsersAdapter.MyViewHolder(itemView);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull UsersAdapter.MyViewHolder holder, final int position) {
        final Users user = userListFiltered.get(position);
        String text = user.getFirstName() + " " + user.getLastName();
        holder.user_name_feed.setText(text);
        holder.user_contact_feed.setText("Contact: " + user.getPhone());
    }

    @Override
    public int getItemCount() {
        return userListFiltered.size();
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                String charString = charSequence.toString();
                if (charString.isEmpty()) {
                    userListFiltered = userList;
                } else {
                    List<Users> filteredList = new ArrayList<>();
                    for (Users user : userList) {
                        if (user.getFirstName().toLowerCase().contains(charString.toLowerCase()) || user.getLastName().toLowerCase().contains(charString.toLowerCase())) {
                            filteredList.add(user);
                        }
                    }

                    userListFiltered = filteredList;
                }

                FilterResults filterResults = new FilterResults();
                filterResults.values = userListFiltered;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                userListFiltered = (ArrayList<Users>) filterResults.values;
                notifyDataSetChanged();
            }
        };
    }

    public interface UsersAdapterListener {
        void onFormSelected(Users form);
    }
}
