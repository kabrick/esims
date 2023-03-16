package tech.streamlinehealth.esims.adapters;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import tech.streamlinehealth.esims.R;
import tech.streamlinehealth.esims.models.Diagnoses;

public class DiagnosesAdapter extends RecyclerView.Adapter<DiagnosesAdapter.MyViewHolder>
        implements Filterable {
    private List<Diagnoses> diagnosisList;
    private List<Diagnoses> diagnosisListFiltered;
    private DiagnosesAdapterListener listener;

    class MyViewHolder extends RecyclerView.ViewHolder {
        TextView diagnosis_name;

        MyViewHolder(View view) {
            super(view);
            diagnosis_name = view.findViewById(R.id.diagnosis_name);

            view.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    listener.onDiagnosisSelected(diagnosisListFiltered.get(getAdapterPosition()));
                }
            });
        }
    }

    public DiagnosesAdapter(List<Diagnoses> diagnosisList, DiagnosesAdapterListener listener) {
        this.listener = listener;
        this.diagnosisList = diagnosisList;
        this.diagnosisListFiltered = diagnosisList;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.row_item_diagnosis, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, final int position) {
        final Diagnoses diagnosis = diagnosisListFiltered.get(position);
        holder.diagnosis_name.setText(diagnosis.getName());
    }

    @Override
    public int getItemCount() {
        return diagnosisListFiltered.size();
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                String charString = charSequence.toString();
                if (charString.isEmpty()) {
                    diagnosisListFiltered = diagnosisList;
                } else {
                    List<Diagnoses> filteredList = new ArrayList<>();
                    for (Diagnoses row : diagnosisList) {
                        if (row.getName().toLowerCase().contains(charString.toLowerCase())) {
                            filteredList.add(row);
                        }
                    }

                    diagnosisListFiltered = filteredList;
                }

                FilterResults filterResults = new FilterResults();
                filterResults.values = diagnosisListFiltered;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                diagnosisListFiltered = (ArrayList<Diagnoses>) filterResults.values;
                notifyDataSetChanged();
            }
        };
    }

    public interface DiagnosesAdapterListener {
        void onDiagnosisSelected(Diagnoses diagnosis);
    }
}
