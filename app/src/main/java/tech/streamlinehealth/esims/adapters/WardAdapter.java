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
import tech.streamlinehealth.esims.clinical_data.EditWardActivity;
import tech.streamlinehealth.esims.models.Ward;

public class WardAdapter extends RecyclerView.Adapter<WardAdapter.MyViewHolder> {
    private List<Ward> wardList;
    private Context context;

    class MyViewHolder extends RecyclerView.ViewHolder {
        TextView ward_name;

        MyViewHolder(View view) {
            super(view);

            // Text views
            ward_name = view.findViewById(R.id.ward_name);

        }
    }

    public WardAdapter(Context context, List<Ward> wardList) {
        this.wardList = wardList;
        this.context = context;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.row_view_wards, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, final int position) {
        final Ward ward = wardList.get(position);
        holder.ward_name.setText(ward.getName());
        holder.ward_name.setOnClickListener(v -> {
            Intent intent = new Intent(v.getContext(), EditWardActivity.class);
            intent.putExtra("id", ward.getId());
            intent.putExtra("name", ward.getName());
            v.getContext().startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return wardList.size();
    }
}
