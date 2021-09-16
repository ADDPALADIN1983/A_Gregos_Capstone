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


public class ImportantDateListActivity extends AppCompatActivity {

    private Bundle savedState;
    private PersonDateViewModel dateViewModel;
    private PersonViewModel personViewModel;
    private FloatingActionButton addButton;
    private RecyclerView recyclerView;
    private DateAdapter adapter;
    private int personID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        savedState = savedInstanceState;
        setContentView(R.layout.generic_recycler_view);
        recyclerView = findViewById(R.id.generic_recycler);
        addButton = findViewById(R.id.add_button);
        adapter = new DateAdapter();

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);
        dateViewModel = ViewModelProviders.of(this).get(PersonDateViewModel.class);
        personViewModel = ViewModelProviders.of(this).get(PersonViewModel.class);
        personID = HelperUtility.getActivePerson();

        addButton.setOnClickListener((View v) -> {
            Intent intent = new Intent(this, AddEditDate.class);
            startActivityForResult(intent, HelperUtility.ADD_DATE_REQUEST, savedState);
        });

        recyclerView.setAdapter(adapter);
        updateViews();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == HelperUtility.ADD_DATE_REQUEST && resultCode == RESULT_OK) {
            int personID = HelperUtility.getActivePerson();
            int dateID = data.getIntExtra(AddEditDate.EXTRA_DATE_ID, -1);
            APerson person = personViewModel.getAPerson(personID);
            person.setImportantDates(HelperUtility.addElement(person.getImportantDates(), dateID));
            personViewModel.update(person);
            HelperUtility.setActivePerson(person.getPersonID());
            updateViews();
        }
    }

    private void updateViews() {
        dateViewModel.getAllDates(personID).observe(this, date -> adapter.submitList(date));
        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                PersonDate selectedDate = adapter.getDateAt(viewHolder.getAdapterPosition());
                APerson person = personViewModel.getAPerson(selectedDate.getPersonID());
                person.setImportantDates(HelperUtility.removeElement(person.getImportantDates(), selectedDate.getDateID()));
                if (selectedDate.getDateType().equalsIgnoreCase("Birthday")) {
                    person.setBirthDateID(-1);
                }
                personViewModel.update(person);
                dateViewModel.delete(selectedDate);
                updateViews();
                Toast.makeText(ImportantDateListActivity.this, "Date info deleted.", Toast.LENGTH_SHORT).show();
            }
        }).attachToRecyclerView(recyclerView);

        adapter.setOnItemClickListener(personDate -> {
            Intent intent = new Intent(ImportantDateListActivity.this, DetailedDateView.class);
            HelperUtility.setActivePerson(personID);
            HelperUtility.setActiveDate(personDate.getDateID());
            ImportantDateListActivity.this.startActivity(intent, savedState);
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
