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

import java.util.List;

import tech.streamlinehealth.esims.R;
import tech.streamlinehealth.esims.helpers.PreferenceManager;
import tech.streamlinehealth.esims.models.PatientEpisode;
import tech.streamlinehealth.esims.patients.PatientHomeActivity;

public class PatientEpisodeAdapter extends RecyclerView.Adapter<PatientEpisodeAdapter.MyViewHolder> {
    private List<PatientEpisode> episodesList;
    private Context context;

    class MyViewHolder extends RecyclerView.ViewHolder {
        TextView episode_timestamp, episode_ward, episode_admission_status;
        ImageView episode_status;

        MyViewHolder(View view) {
            super(view);

            episode_timestamp = view.findViewById(R.id.episode_timestamp);
            episode_ward = view.findViewById(R.id.episode_ward);
            episode_status = view.findViewById(R.id.episode_status);
            episode_admission_status = view.findViewById(R.id.episode_admission_status);

            view.setOnClickListener(v -> {
                PatientEpisode episode = episodesList.get(MyViewHolder.this.getAdapterPosition());

                PreferenceManager pref = new PreferenceManager(context);
                pref.setEpisodeId(episode.getId());
                pref.setEpisodeDischargeStatus(episode.getDischargeStatus());

                Intent intent = new Intent(context, PatientHomeActivity.class);
                context.startActivity(intent);
            });
        }
    }

    public PatientEpisodeAdapter(List<PatientEpisode> episodesList, Context context) {
        this.episodesList = episodesList;
        this.context = context;
    }

    @NonNull
    @Override
    public PatientEpisodeAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.row_item_patient_episodes, parent, false);

        return new PatientEpisodeAdapter.MyViewHolder(itemView);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull PatientEpisodeAdapter.MyViewHolder holder, final int position) {
        final PatientEpisode episode = episodesList.get(position);

        switch (episode.getTriageGrade()){
            case "1":
                holder.episode_status.setImageResource(R.drawable.circle_red);
                break;
            case "2":
                holder.episode_status.setImageResource(R.drawable.circle_yellow);
                break;
            case "3":
                holder.episode_status.setImageResource(R.drawable.circle_green);
                break;
            case "0":
            default:
                holder.episode_status.setImageResource(R.drawable.circle_white);
                break;
        }

        String ward = "Ward: " + episode.getWard();
        holder.episode_ward.setText(ward);

        if (episode.getDischargeStatus().equals("0")){
            holder.episode_admission_status.setText("Patient still admitted");
        } else {
            holder.episode_admission_status.setText("Patient discharged");
        }

        @SuppressLint("SimpleDateFormat")
        String timeAgo = new java.text.SimpleDateFormat("dd MMMM yyyy 'at' HH:mm:ss").format(new java.util.Date (Long.parseLong(episode.getAdmissionTimestamp())));
        String timestamp = "Episode created " + timeAgo;
        holder.episode_timestamp.setText(timestamp);
    }

    @Override
    public int getItemCount() {
        return episodesList.size();
    }
}
