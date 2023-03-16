package tech.streamlinehealth.esims.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import tech.streamlinehealth.esims.R;
import tech.streamlinehealth.esims.clinical_data.EditTaskActivity;
import tech.streamlinehealth.esims.models.Task;
import tech.streamlinehealth.esims.workload_assessment.RatingScaleActivity;

public class TaskAdapter extends RecyclerView.Adapter<TaskAdapter.MyViewHolder> {
    private final List<Task> taskList;
    private final Context context;
    private final boolean is_from_workload_assessment;

    static class MyViewHolder extends RecyclerView.ViewHolder {
        TextView task_name, task_description;
        LinearLayout linearLayout;

        MyViewHolder(View view) {
            super(view);

            // Text views
            task_name = view.findViewById(R.id.task_name);
            task_description = view.findViewById(R.id.task_description);
            linearLayout = view.findViewById(R.id.linearlayout);
        }
    }

    public TaskAdapter(Context context, List<Task> taskList, boolean is_from_workload_assessment) {
        this.taskList = taskList;
        this.context = context;
        this.is_from_workload_assessment = is_from_workload_assessment;
    }

    @NonNull
    @Override
    public TaskAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(context)
                .inflate(R.layout.row_view_tasks, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull TaskAdapter.MyViewHolder holder, final int position) {
        final Task task = taskList.get(position);
        holder.task_name.setText(task.getName());
        holder.task_description.setText(task.getDescription());

        holder.linearLayout.setOnClickListener(v -> {

            Intent intent;

            if (is_from_workload_assessment) {
                intent = new Intent(context, RatingScaleActivity.class);
            } else {
                intent = new Intent(context, EditTaskActivity.class);
            }

            intent.putExtra("id", task.getId());
            intent.putExtra("name", task.getName());
            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return taskList.size();
    }
}
