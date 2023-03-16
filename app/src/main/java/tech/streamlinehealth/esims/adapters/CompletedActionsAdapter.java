package tech.streamlinehealth.esims.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import tech.streamlinehealth.esims.R;
import tech.streamlinehealth.esims.models.CompletedAction;

public class CompletedActionsAdapter extends RecyclerView.Adapter<CompletedActionsAdapter.MyViewHolder> {
    private List<CompletedAction> actionsList;

    class MyViewHolder extends RecyclerView.ViewHolder {
        TextView completed_action, completed_time;

        MyViewHolder(View view) {
            super(view);

            // Text views
            completed_action = view.findViewById(R.id.completed_action);
            completed_time = view.findViewById(R.id.completed_time);
        }
    }

    public CompletedActionsAdapter(List<CompletedAction> actionsList) {
        this.actionsList = actionsList;
    }

    @NonNull
    @Override
    public CompletedActionsAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.row_item_completed_actions, parent, false);

        return new CompletedActionsAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull CompletedActionsAdapter.MyViewHolder holder, final int position) {
        final CompletedAction action = actionsList.get(position);

        holder.completed_action.setText(action.getAction());
        holder.completed_time.setText(action.getTimestamp());
    }

    @Override
    public int getItemCount() {
        return actionsList.size();
    }
}
