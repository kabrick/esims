package tech.streamlinehealth.esims.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import java.util.List;

import tech.streamlinehealth.esims.R;
import tech.streamlinehealth.esims.clinical_data.EditOccupationActivity;
import tech.streamlinehealth.esims.models.Occupation;

public class OccupationAdapter extends RecyclerView.Adapter<OccupationAdapter.MyViewHolder> {
    private List<Occupation> occupationList;
    private Context context;

    class MyViewHolder extends RecyclerView.ViewHolder {
        TextView occupation_name;

        MyViewHolder(View view) {
            super(view);

            // Text views
            occupation_name = view.findViewById(R.id.occupation_name);
        }
    }

    public OccupationAdapter(Context context, List<Occupation> occupationList) {
        this.occupationList = occupationList;
        this.context = context;
    }

    @NonNull
    @Override
    public OccupationAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.row_view_occupations, parent, false);

        return new OccupationAdapter.MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull OccupationAdapter.MyViewHolder holder, final int position) {
        final Occupation occupation = occupationList.get(position);
        holder.occupation_name.setText(occupation.getName());
        holder.occupation_name.setOnClickListener(v ->{
            Intent edit_occupation = new Intent(v.getContext(), EditOccupationActivity.class);
            edit_occupation.putExtra("id", occupation.getId());
            edit_occupation.putExtra("name", occupation.getName());
            v.getContext().startActivity(edit_occupation);
        });
    }

    @Override
    public int getItemCount() {
        return occupationList.size();
    }
}
