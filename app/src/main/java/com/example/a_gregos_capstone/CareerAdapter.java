package com.example.a_gregos_capstone;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

public class CareerAdapter extends ListAdapter<Career, CareerAdapter.CareerHolder> {

    private OnItemClickListener listener;

    public CareerAdapter() {
        super(DIFF_CALLBACK);
    }

    private static final DiffUtil.ItemCallback<Career> DIFF_CALLBACK = new DiffUtil.ItemCallback<Career>() {
        @Override
        public boolean areItemsTheSame(@NonNull Career oldItem, @NonNull Career newItem) {
            return oldItem.getCareerID() == newItem.getCareerID();
        }

        @Override
        public boolean areContentsTheSame(@NonNull Career oldItem, @NonNull Career newItem) {
            return oldItem.getPersonID() == newItem.getPersonID()
                    && oldItem.getCareerCategory().equals(newItem.getCareerCategory())
                    && oldItem.getCareerSpecialization().equals(newItem.getCareerSpecialization())
                    && oldItem.getCompanyName().equals(newItem.getCompanyName());
        }
    };

    @NonNull
    @Override
    public CareerHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.career_item, parent, false);

        return new CareerHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull CareerHolder holder, int position) {
        Career currentCareer = getItem(position);
        holder.textViewCompanyName.setText(currentCareer.getCompanyName());
        holder.textViewCareerCategory.setText(currentCareer.getCareerCategory());
        holder.textViewSpecialization.setText(currentCareer.getCareerSpecialization());

    }

    public Career getCareerAt(int position) {
        return getItem(position);
    }

    class CareerHolder extends RecyclerView.ViewHolder {
        private TextView textViewCompanyName;
        private TextView textViewCareerCategory;
        private TextView textViewSpecialization;


        public CareerHolder(@NonNull View itemView) {
            super(itemView);
            textViewCompanyName = itemView.findViewById(R.id.text_view_company_name);
            textViewCareerCategory = itemView.findViewById(R.id.text_view_career_category);
            textViewSpecialization = itemView.findViewById(R.id.text_view_specialization);

            itemView.setOnClickListener(v -> {
                int position = CareerHolder.this.getAdapterPosition();
                if (listener != null && position != RecyclerView.NO_POSITION) {
                    listener.onItemClick(getItem(position));
                }
            });
        }
    }

    public interface OnItemClickListener {
        void onItemClick(Career career);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }
}
