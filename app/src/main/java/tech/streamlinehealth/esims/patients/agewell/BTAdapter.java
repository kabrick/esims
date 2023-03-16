package tech.streamlinehealth.esims.patients.agewell;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.healthcubed.ezdx.agewelllib.bluetoothHandler.data.BleDevice;
import tech.streamlinehealth.esims.R;

import java.util.List;

class BTAdapter extends RecyclerView.Adapter<BTAdapter.ViewHolder> {
    /* access modifiers changed from: private */
    public ItemClickListener mClickListener;
    /* access modifiers changed from: private */
    public List<BleDevice> mData;
    private LayoutInflater mInflater;


    public interface ItemClickListener {
        void onItemClick(BleDevice bleDevice);
    }

    BTAdapter(Context context, List<BleDevice> data) {
        this.mInflater = LayoutInflater.from(context);
        this.mData = data;
    }

    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ViewHolder(this.mInflater.inflate(R.layout.bluetooth_devices_row, parent, false));
    }

    public void onBindViewHolder(ViewHolder holder, int position) {
        holder.myTextView.setText(this.mData.get(position).getName());
    }

    public int getItemCount() {
        return this.mData.size();
    }

    public List<BleDevice> getDevices() {
        return this.mData;
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView myTextView;

        ViewHolder(View itemView) {
            super(itemView);
            this.myTextView = itemView.findViewById(R.id.tvName);
            itemView.setOnClickListener(this);
        }

        public void onClick(View view) {
            if (BTAdapter.this.mClickListener != null) {
                BTAdapter.this.mClickListener.onItemClick(BTAdapter.this.mData.get(getAdapterPosition()));
            }
        }
    }

    /* access modifiers changed from: package-private */
    public void setClickListener(ItemClickListener itemClickListener) {
        this.mClickListener = itemClickListener;
    }
}
