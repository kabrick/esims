package tech.streamlinehealth.esims.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import tech.streamlinehealth.esims.R;
import tech.streamlinehealth.esims.models.PatientTriage;
import tech.streamlinehealth.esims.patients.triage.PatientTriageDetailsActivity;

public class PatientTriageAdapter extends RecyclerView.Adapter<PatientTriageAdapter.MyViewHolder> {
    private List<PatientTriage> triageList;
    private Context context;

    class MyViewHolder extends RecyclerView.ViewHolder {
        TextView triage_text_row;
        ImageView triage_status_row;

        MyViewHolder(View view) {
            super(view);

            // Text views
            triage_text_row = view.findViewById(R.id.triage_text_row);
            triage_status_row = view.findViewById(R.id.triage_status_row);

            view.setOnClickListener(v -> {
                if (triageList.get(MyViewHolder.this.getAdapterPosition()).getGrade().equals("3")) {
                    Toast.makeText(context, "No data available for non urgent patients", Toast.LENGTH_SHORT).show();
                } else {
                    Intent intent = new Intent(context, PatientTriageDetailsActivity.class);
                    intent.putExtra("grade", triageList.get(MyViewHolder.this.getAdapterPosition()).getGrade());
                    intent.putExtra("checks", triageList.get(MyViewHolder.this.getAdapterPosition()).getChecks());
                    context.startActivity(intent);
                }
            });
        }
    }

    public PatientTriageAdapter(List<PatientTriage> triageList, Context context) {
        this.triageList = triageList;
        this.context = context;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.row_item_triage, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, final int position) {
        final PatientTriage triage = triageList.get(position);

        @SuppressLint("SimpleDateFormat")
        String timeAgo = new java.text.SimpleDateFormat("dd MMMM yyyy HH:mm:ss").format(new java.util.Date (Long.parseLong(triage.getTimestamp())));

        String grade_text = "";

        switch (triage.getGrade()) {
            case "1":
                grade_text = ". Emergency signs found";
                holder.triage_status_row.setImageResource(R.drawable.circle_red);
                break;
            case "2":
                grade_text = ". Priority signs found";
                holder.triage_status_row.setImageResource(R.drawable.circle_yellow);
                break;
            case "3":
                grade_text = ". Non urgent patient";
                holder.triage_status_row.setImageResource(R.drawable.circle_green);
                break;
        }

        String text = "Triage taken at " + timeAgo + grade_text;

        holder.triage_text_row.setText(text);
    }

    @Override
    public int getItemCount() {
        return triageList.size();
    }
}
