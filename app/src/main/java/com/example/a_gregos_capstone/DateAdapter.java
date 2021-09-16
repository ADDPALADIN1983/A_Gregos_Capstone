package com.example.a_gregos_capstone;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

public class DateAdapter extends ListAdapter<PersonDate, DateAdapter.DateHolder> {


    private OnItemClickListener listener;

    public DateAdapter() {
        super(DIFF_CALLBACK);
    }

    private static final DiffUtil.ItemCallback<PersonDate> DIFF_CALLBACK = new DiffUtil.ItemCallback<PersonDate>() {
        @Override
        public boolean areItemsTheSame(@NonNull PersonDate oldItem, @NonNull PersonDate newItem) {
            return oldItem.getDateID() == newItem.getDateID();
        }

        @Override
        public boolean areContentsTheSame(@NonNull PersonDate oldItem, @NonNull PersonDate newItem) {
            return oldItem.getPersonID() == newItem.getPersonID() &&
                    oldItem.getYear() == newItem.getYear() &&
                    oldItem.getMonth() == newItem.getMonth() &&
                    oldItem.getDay() == newItem.getDay() &&
                    oldItem.getDateType().equals(newItem.getDateType()) &&
                    oldItem.getDateDescription().equals(newItem.getDateDescription());
        }
    };

    @NonNull
    @Override
    public DateHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.date_item, parent, false);

        return new DateHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull DateHolder holder, int position) {
        PersonDate date = getItem(position);
        holder.textViewType.setText(date.getDateType());
        holder.textViewDate.setText(date.toString());
    }


    public PersonDate getDateAt(int position) {
        return getItem(position);
    }

    class DateHolder extends RecyclerView.ViewHolder {
        private TextView textViewType;
        private TextView textViewDate;


        public DateHolder(@NonNull View itemView) {
            super(itemView);
            textViewType = itemView.findViewById(R.id.text_view_date_type);
            textViewDate = itemView.findViewById(R.id.text_view_date);

            itemView.setOnClickListener(v -> {
                int position = DateHolder.this.getAdapterPosition();
                if (listener != null && position != RecyclerView.NO_POSITION) {
                    listener.onItemClick(getItem(position));
                }
            });
        }
    }

    public interface OnItemClickListener {
        void onItemClick(PersonDate date);
    }

    public void setOnItemClickListener(DateAdapter.OnItemClickListener listener) {
        this.listener = listener;
    }
}