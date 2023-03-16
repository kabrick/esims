package tech.streamlinehealth.esims.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;
import android.text.format.DateUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.ArrayList;
import java.util.List;

import tech.streamlinehealth.esims.R;
import tech.streamlinehealth.esims.helpers.DataRouter;
import tech.streamlinehealth.esims.helpers.HelperFunctions;
import tech.streamlinehealth.esims.helpers.PreferenceManager;
import tech.streamlinehealth.esims.models.Patient;
import tech.streamlinehealth.esims.patients.PatientEpisodesActivity;
import tech.streamlinehealth.esims.patients.ViewPatientsActivity;

public class PatientsAdapter extends RecyclerView.Adapter<PatientsAdapter.MyViewHolder> implements Filterable {

    private final List<Patient> patientsList;
    private List<Patient> patientsListFiltered;
    private final Context context;

    class MyViewHolder extends RecyclerView.ViewHolder {
        TextView active_patient_details, active_patient_ward, active_patient_timestamp;
        ImageView patient_status_feed;

        MyViewHolder(View view) {
            super(view);

            // Text views
            active_patient_details = view.findViewById(R.id.active_patient_details);
            active_patient_ward = view.findViewById(R.id.active_patient_ward);
            active_patient_timestamp = view.findViewById(R.id.active_patient_timestamp);
            patient_status_feed = view.findViewById(R.id.patient_status_feed);

            view.setOnClickListener(v -> {
                Patient patient = patientsList.get(MyViewHolder.this.getAdapterPosition());
                String patient_id = patient.getId();

                if (patient.isDeleted()){
                    HelperFunctions helperFunctions = new HelperFunctions(context);
                    DataRouter router = new DataRouter(context);

                    AlertDialog.Builder alert1 = new AlertDialog.Builder(context);

                    alert1.setMessage("This patient record was deleted. Do you want to restore it?").setPositiveButton("Yes", (dialogInterface, i) -> {
                        helperFunctions.genericProgressBar("Restoring patient record...");

                        // check if patient has already been assigned a doctor
                        String network_address = router.ip_address + "sims_patients/activate_patient/" + patient.getId();

                        StringRequest request = new StringRequest(Request.Method.GET, network_address,
                                response -> {
                                    helperFunctions.stopProgressBar();

                                    if (!response.equals("0")){
                                        AlertDialog.Builder alert = new AlertDialog.Builder(context);

                                        alert.setMessage("Patient record has been restored")
                                                .setPositiveButton("Okay, view Record", (dialogInterface1, i1) -> context.startActivity(new Intent(context, ViewPatientsActivity.class)))
                                                .setNegativeButton("Cancel", null).show();
                                    } else {
                                        helperFunctions.genericDialog("Something went wrong. Please try again later.");
                                    }
                                }, error -> {
                                    helperFunctions.stopProgressBar();
                                    helperFunctions.genericDialog("Something went wrong. Please try again later.");
                                });

                        Volley.newRequestQueue(context).add(request);
                    }).setNegativeButton("Cancel", null).show();
                } else {
                    // set the patient id in the prefs
                    new PreferenceManager(context).setPatientId(patient_id);

                    Intent intent = new Intent(context, PatientEpisodesActivity.class);
                    context.startActivity(intent);
                }
            });
        }
    }

    public PatientsAdapter(Context context, List<Patient> patientsList) {
        this.patientsList = patientsList;
        this.patientsListFiltered = patientsList;
        this.context = context;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.row_item_active_patients, parent, false);

        return new MyViewHolder(itemView);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, final int position) {
        final Patient patient = patientsListFiltered.get(position);
        holder.active_patient_ward.setText("Ward: " + patient.getWard());

        HelperFunctions helperFunctions = new HelperFunctions(context);

        String text = helperFunctions.capitalize_first_letter(patient.getName()) + " (" + patient.getDate_of_birth() + "), " + patient.getGender();
        holder.active_patient_details.setText(text);

        switch (patient.getTriageGrade()){
            case "1":
                holder.patient_status_feed.setImageResource(R.drawable.circle_red);
                break;
            case "2":
                holder.patient_status_feed.setImageResource(R.drawable.circle_yellow);
                break;
            case "3":
                holder.patient_status_feed.setImageResource(R.drawable.circle_green);
                break;
            case "0":
            default:
                holder.patient_status_feed.setImageResource(R.drawable.circle_white);
                break;
        }

        String timestamp = "";

        if (patient.isDeleted()) {
            timestamp = "Patient Record Deleted";
        } else if (patient.getDischarge_status().equals("0")){
            //covert timestamp to readable format
            CharSequence timeAgo = DateUtils.getRelativeTimeSpanString(
                    Long.parseLong(patient.getTimestamp()),
                    System.currentTimeMillis(), DateUtils.SECOND_IN_MILLIS);

            timestamp = "Admitted " + timeAgo;
        } else if (patient.getDischarge_status().equals("1")){
            //covert timestamp to readable format
            CharSequence timeAgo = DateUtils.getRelativeTimeSpanString(
                    Long.parseLong(patient.getDischarge_timestamp()),
                    System.currentTimeMillis(), DateUtils.SECOND_IN_MILLIS);

            timestamp = "Discharged " + timeAgo;
        }

        holder.active_patient_timestamp.setText(timestamp);
    }

    @Override
    public int getItemCount() {
        return patientsListFiltered.size();
    }

    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                String charString = charSequence.toString();
                if (charString.isEmpty()) {
                    patientsListFiltered = patientsList;
                } else {
                    List<Patient> filteredList = new ArrayList<>();
                    for (Patient patient : patientsList) {
                        if (patient.getName().toLowerCase().contains(charString.toLowerCase()) || patient.getWard().toLowerCase().contains(charString.toLowerCase())) {
                            filteredList.add(patient);
                        }
                    }

                    patientsListFiltered = filteredList;
                }

                FilterResults filterResults = new FilterResults();
                filterResults.values = patientsListFiltered;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                patientsListFiltered = (ArrayList<Patient>) filterResults.values;
                notifyDataSetChanged();
            }
        };
    }

}
