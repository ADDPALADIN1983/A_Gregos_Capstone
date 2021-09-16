package com.example.a_gregos_capstone;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

public class PastTimesAdapter extends ListAdapter<PastTimes, PastTimesAdapter.PastTimesHolder> {

    private OnItemClickListener listener;

    public PastTimesAdapter() {
        super(DIFF_CALLBACK);
    }

    private static final DiffUtil.ItemCallback<PastTimes> DIFF_CALLBACK = new DiffUtil.ItemCallback<PastTimes>() {
        @Override
        public boolean areItemsTheSame(@NonNull PastTimes oldItem, @NonNull PastTimes newItem) {
            return oldItem.getPastTimeID() == newItem.getPastTimeID();
        }

        @Override
        public boolean areContentsTheSame(@NonNull PastTimes oldItem, @NonNull PastTimes newItem) {
            return oldItem.getPersonID() == newItem.getPersonID()
                    && oldItem.getPastTimeName().equals(newItem.getPastTimeName())
                    && oldItem.getPastTimeDetails().equals(newItem.getPastTimeDetails());
        }
    };

    @NonNull
    @Override
    public PastTimesHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.past_time_item, parent, false);

        return new PastTimesHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull PastTimesHolder holder, int position) {
        PastTimes currentPastTime = getItem(position);
        holder.textViewPastTimeName.setText(currentPastTime.getPastTimeName());
        holder.textViewPastTimeDetails.setText(currentPastTime.getPastTimeDetails());

    }

    public PastTimes getPastTimesAt(int position) {
        return getItem(position);
    }

    class PastTimesHolder extends RecyclerView.ViewHolder {
        private TextView textViewPastTimeName;
        private TextView textViewPastTimeDetails;

        public PastTimesHolder(@NonNull View itemView) {
            super(itemView);
            textViewPastTimeName = itemView.findViewById(R.id.text_view_past_time_name);
            textViewPastTimeDetails = itemView.findViewById(R.id.text_view_past_time_details);

            itemView.setOnClickListener(v -> {
                int position = PastTimesHolder.this.getAdapterPosition();
                if (listener != null && position != RecyclerView.NO_POSITION) {
                    listener.onItemClick(getItem(position));
                }
            });
        }
    }

    public interface OnItemClickListener {
        void onItemClick(PastTimes career);
    }

    public void setOnItemClickListener(PastTimesAdapter.OnItemClickListener listener) {
        this.listener = listener;
    }
}
