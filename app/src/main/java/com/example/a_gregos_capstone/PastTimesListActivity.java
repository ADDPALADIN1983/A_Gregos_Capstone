package com.example.a_gregos_capstone;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class PastTimesListActivity extends AppCompatActivity {

    private Bundle savedState;
    private PastTimesViewModel pastTimesViewModel;
    private PersonViewModel personViewModel;
    private FloatingActionButton addButton;
    private RecyclerView recyclerView;
    private PastTimesAdapter adapter;
    private int personID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        savedState = savedInstanceState;
        setContentView(R.layout.generic_recycler_view);
        recyclerView = findViewById(R.id.generic_recycler);
        addButton = findViewById(R.id.add_button);
        adapter = new PastTimesAdapter();

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);
        pastTimesViewModel = ViewModelProviders.of(this).get(PastTimesViewModel.class);
        personViewModel = ViewModelProviders.of(this).get(PersonViewModel.class);
        personID = HelperUtility.getActivePerson();

        addButton.setOnClickListener((View v) -> {
            Intent intent = new Intent(this, AddEditPastTimes.class);
            startActivityForResult(intent, HelperUtility.ADD_PAST_TIME_REQUEST, savedState);
        });

        recyclerView.setAdapter(adapter);
        updateViews();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == HelperUtility.ADD_PAST_TIME_REQUEST && resultCode == RESULT_OK) {
            int personID = HelperUtility.getActivePerson();
            int pastTimeID = data.getIntExtra(AddEditPastTimes.EXTRA_PAST_TIMES_ID, -1);
            APerson person = personViewModel.getAPerson(personID);
            person.setPastTimes(HelperUtility.addElement(person.getPastTimes(), pastTimeID));
            personViewModel.update(person);
            HelperUtility.setActivePerson(person.getPersonID());
            updateViews();
        }
    }

    private void updateViews() {
        pastTimesViewModel.getAllPastTimes(personID).observe(this, list -> adapter.submitList(list));
        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                PastTimes selectedPastTime = adapter.getPastTimesAt(viewHolder.getAdapterPosition());
                APerson person = personViewModel.getAPerson(selectedPastTime.getPersonID());
                person.setPastTimes(HelperUtility.removeElement(person.getPastTimes(), selectedPastTime.getPastTimeID()));
                personViewModel.update(person);
                pastTimesViewModel.delete(selectedPastTime);
                Toast.makeText(PastTimesListActivity.this, "Past time info deleted.", Toast.LENGTH_SHORT).show();
                updateViews();
            }
        }).attachToRecyclerView(recyclerView);

        adapter.setOnItemClickListener(pastTimes -> {
            Intent intent = new Intent(PastTimesListActivity.this, DetailedPastTimeView.class);
            HelperUtility.setActivePerson(personID);
            HelperUtility.setActivePastTime(pastTimes.getPastTimeID());
            PastTimesListActivity.this.startActivity(intent, savedState);
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu_main, menu);
        menu.getItem(0).setVisible(false);
        menu.getItem(1).setVisible(false);
        menu.getItem(2).setVisible(false);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Intent intent;
        switch (item.getItemId()) {
            case R.id.menu_option_0:
                return true;
            case R.id.menu_option_1:
                return true;
            case R.id.menu_option_2:
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
