package com.example.a_gregos_capstone;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

public class PersonAdapter extends ListAdapter<APerson, PersonAdapter.PersonHolder> {


    private OnItemClickListener listener;

    public PersonAdapter() {
        super(DIFF_CALLBACK);
    }

    private static final DiffUtil.ItemCallback<APerson> DIFF_CALLBACK = new DiffUtil.ItemCallback<APerson>() {
        @Override
        public boolean areItemsTheSame(@NonNull APerson oldItem, @NonNull APerson newItem) {
            return oldItem.getPersonID() == newItem.getPersonID();
        }

        @Override
        public boolean areContentsTheSame(@NonNull APerson oldItem, @NonNull APerson newItem) {
            return oldItem.getCategoryID() == newItem.getCategoryID() &&
                    oldItem.getCareerIDs().equals(newItem.getCareerIDs()) &&
                    oldItem.getFirstName().equals(newItem.getFirstName()) &&
                    oldItem.getLastName().equals(newItem.getLastName()) &&
                    oldItem.getBirthDateID() == newItem.getBirthDateID() &&
                    oldItem.getImportantDates().equals(newItem.getImportantDates()) &&
                    oldItem.getPersonalNotes().equals(newItem.getPersonalNotes()) &&
                    oldItem.getPastTimes().equals(newItem.getPastTimes());
        }
    };

    @NonNull
    @Override
    public PersonHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.each_person, parent, false);

        return new PersonHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull PersonHolder holder, int position) {
        APerson person = getItem(position);
        holder.textViewFirstName.setText(person.getFirstName());
        holder.textViewLastName.setText(person.getLastName());
    }


    public APerson getPersonAt(int position) {
        return getItem(position);
    }

    class PersonHolder extends RecyclerView.ViewHolder {
        private TextView textViewFirstName;
        private TextView textViewLastName;


        public PersonHolder(@NonNull View itemView) {
            super(itemView);
            textViewFirstName = itemView.findViewById(R.id.person_first_name);
            textViewLastName = itemView.findViewById(R.id.person_last_name);

            itemView.setOnClickListener(v -> {
                int position = PersonHolder.this.getAdapterPosition();
                if (listener != null && position != RecyclerView.NO_POSITION) {
                    listener.onItemClick(getItem(position));
                }
            });
        }
    }

    public interface OnItemClickListener {
        void onItemClick(APerson person);
    }

    public void setOnItemClickListener(PersonAdapter.OnItemClickListener listener) {
        this.listener = listener;
    }
}