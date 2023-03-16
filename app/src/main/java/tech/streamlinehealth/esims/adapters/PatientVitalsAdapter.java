package tech.streamlinehealth.esims.adapters;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.text.format.DateUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.NetworkError;
import com.android.volley.TimeoutError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.List;

import tech.streamlinehealth.esims.R;
import tech.streamlinehealth.esims.helpers.DataRouter;
import tech.streamlinehealth.esims.helpers.HelperFunctions;
import tech.streamlinehealth.esims.models.PatientVitals;
import tech.streamlinehealth.esims.patients.EditPatientVitalsActivity;
import tech.streamlinehealth.esims.patients.ViewPatientVitalsActivity;

public class PatientVitalsAdapter extends RecyclerView.Adapter<PatientVitalsAdapter.MyViewHolder> {
    private List<PatientVitals> vitalsList;
    String id;

    class MyViewHolder extends RecyclerView.ViewHolder {
        TextView vitals_dbp, vitals_sbp, vitals_hr, vitals_rr, vitals_temp, vitals_spo, vitals_avpu, vitals_timestamp;
        ImageButton edit_vitals, delete_vitals;

        MyViewHolder(View view) {
            super(view);

            // Text views
            vitals_dbp = view.findViewById(R.id.vitals_dbp);
            vitals_sbp = view.findViewById(R.id.vitals_sbp);
            vitals_hr = view.findViewById(R.id.vitals_hr);
            vitals_rr = view.findViewById(R.id.vitals_rr);
            vitals_temp = view.findViewById(R.id.vitals_temp);
            vitals_spo = view.findViewById(R.id.vitals_spo);
            vitals_avpu = view.findViewById(R.id.vitals_avpu);
            vitals_timestamp = view.findViewById(R.id.vitals_timestamp);

            // Image Buttons
            edit_vitals = view.findViewById(R.id.edit_vitals);
            delete_vitals = view.findViewById(R.id.delete_vitals);

        }
    }

    public PatientVitalsAdapter(List<PatientVitals> vitalsList) {
        this.vitalsList = vitalsList;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.row_item_vitals, parent, false);

        return new MyViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, final int position) {
        final PatientVitals vital = vitalsList.get(position);

        holder.vitals_dbp.setText(vital.getDiastolicBp());
        holder.vitals_sbp.setText(vital.getSystolicBp());
        holder.vitals_hr.setText(vital.getHeartRate());
        holder.vitals_rr.setText(vital.getRespRate());
        holder.vitals_temp.setText(vital.getTemp());
        holder.vitals_spo.setText(vital.getSpo());

        String avpu = "AVPU - " + vital.getAvpu();
        id = vital.getId();

        holder.vitals_avpu.setText(avpu);
        holder.edit_vitals.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent edit_action = new Intent(v.getContext(), EditPatientVitalsActivity.class);
                edit_action.putExtra("systolic_bp", vital.getSystolicBp());
                edit_action.putExtra("diastolic_bp", vital.getDiastolicBp());
                edit_action.putExtra("heart_rate", vital.getHeartRate());
                edit_action.putExtra("resp_rate", vital.getRespRate());
                edit_action.putExtra("temperature", vital.getTemp());
                edit_action.putExtra("oxygen_sat", vital.getSpo());
                edit_action.putExtra("avpu", vital.getAvpu());
                edit_action.putExtra("timestamp", String.valueOf(System.currentTimeMillis()));
                v.getContext().startActivity(edit_action);
            }
        });


        holder.delete_vitals.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                delete(v.getContext());
            }
        });

//        covert timestamp to readable format
        CharSequence timeAgo = DateUtils.getRelativeTimeSpanString(
                Long.parseLong(vital.getTimestamp()),
                System.currentTimeMillis(), DateUtils.SECOND_IN_MILLIS);


        String timestamp = "Taken " + timeAgo + " by " + vital.getCreatedBy();

        holder.vitals_timestamp.setText(timestamp);
    }

    @Override
    public int getItemCount() {
        return vitalsList.size();
    }

    public void delete(Context context){
        HelperFunctions helperFunctions = new HelperFunctions(context);

        AlertDialog.Builder alert1 = new AlertDialog.Builder(context);

        alert1.setMessage("Are you sure you want to delete ")
                .setPositiveButton("Yes", (dialogInterface1, i1) -> {
                    helperFunctions.genericProgressBar("Deleting Vitals..");
                    String network_address = new DataRouter(context).ip_address + "sims_patients/delete_vitals/" + id;

                    // Request a string response from the provided URL
                    StringRequest request = new StringRequest(network_address,
                            response -> {
                                helperFunctions.stopProgressBar();

                                if(response.equals("1")){

                                    AlertDialog.Builder alert = new AlertDialog.Builder(context);

                                    alert.setMessage("Vitals has been deleted").setPositiveButton("Okay", (dialogInterface11, i11) -> {
                                        Intent delete_intent = new Intent(context, ViewPatientVitalsActivity.class);
                                        context.startActivity(delete_intent);
                                    }).show();
                                } else {
                                    Toast.makeText(context, "Unable to delete the ward. Please try again", Toast.LENGTH_LONG).show();
                                }

                            }, error -> {
                        helperFunctions.stopProgressBar();

                        if (error instanceof TimeoutError || error instanceof NetworkError) {
                            helperFunctions.genericDialog("Something went wrong. Please make sure you are connected to a working internet connection.");
                        } else {
                            helperFunctions.genericDialog("Something went wrong. Please try again later");
                        }
                    });

                    Volley.newRequestQueue(context).add(request);
                }).show();
    }

}
