package com.example.a_gregos_capstone;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class CareerListActivity extends AppCompatActivity {
    Bundle savedState;
    private PersonViewModel personViewModel;
    private CareerViewModel careerViewModel;
    private FloatingActionButton addButton;
    private final CareerAdapter careerAdapter = new CareerAdapter();
    private RecyclerView careerRecyclerView;
    private int personID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        savedState = savedInstanceState;
        setContentView(R.layout.generic_recycler_view);

        personID = HelperUtility.getActivePerson();
        personViewModel = ViewModelProviders.of(this).get(PersonViewModel.class);
        careerViewModel = ViewModelProviders.of(this).get(CareerViewModel.class);
        addButton = findViewById(R.id.add_button);
        addButton.setOnClickListener(v -> {
            Intent intent = new Intent(CareerListActivity.this, AddEditCareer.class);
            startActivityForResult(intent, HelperUtility.ADD_CAREER_REQUEST);
        });

        careerRecyclerView = findViewById(R.id.generic_recycler);
        careerRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        careerRecyclerView.setHasFixedSize(true);
        careerRecyclerView.setAdapter(careerAdapter);
        updateViews();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == HelperUtility.ADD_CAREER_REQUEST && resultCode == RESULT_OK) {

            int careerID = data.getIntExtra(AddEditCareer.EXTRA_CAREER_ID, -1);
            APerson person = personViewModel.getAPerson(personID);
            person.setCareerIDs(HelperUtility.addElement(person.getCareerIDs(), careerID));

            personViewModel.update(person);
            HelperUtility.setActivePerson(person.getPersonID());
            updateViews();
        }
    }

    private void updateViews() {
        careerViewModel.searchByPersonID(HelperUtility.getActivePerson()).observe(this, list -> careerAdapter.submitList(list));
        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                Career selectedCareer = careerAdapter.getCareerAt(viewHolder.getAdapterPosition());
                APerson person = personViewModel.getAPerson(selectedCareer.getPersonID());
                person.setCareerIDs(HelperUtility.removeElement(person.getCareerIDs(), selectedCareer.getCareerID()));
                personViewModel.update(person);
                careerViewModel.delete(selectedCareer);
                updateViews();
                Toast.makeText(CareerListActivity.this, "Career info deleted.", Toast.LENGTH_SHORT).show();
            }
        }).attachToRecyclerView(careerRecyclerView);

        careerAdapter.setOnItemClickListener(career -> {
            HelperUtility.setActiveCareer(career.getCareerID());
            Intent intent = new Intent(CareerListActivity.this, DetailedCareerView.class);
            CareerListActivity.this.startActivity(intent, savedState);
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