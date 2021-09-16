package com.example.a_gregos_capstone;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import static android.app.Activity.RESULT_OK;

public class PeopleFragment extends Fragment {
    private Bundle savedState;
    private PersonCategoryViewModel categoryViewModel;
    private PersonViewModel personViewModel;
    private FloatingActionButton addButton;
    private FloatingActionButton searchButton;
    private RecyclerView peopleRecycler;
    private PersonAdapter adapter;
    private int categoryID;
    private String categoryName;

    public PeopleFragment(String name) {
        this.categoryName = name;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        savedState = savedInstanceState;
        setHasOptionsMenu(true);
        View rootView = inflater.inflate(R.layout.people_recyler, container, false);
        peopleRecycler = rootView.findViewById(R.id.people_recycler_view);
        addButton = rootView.findViewById(R.id.add_person_button);
        searchButton = rootView.findViewById(R.id.search_button);

        peopleRecycler.setLayoutManager(new LinearLayoutManager(getContext()));
        peopleRecycler.setHasFixedSize(true);
        categoryViewModel = ViewModelProviders.of(this).get(PersonCategoryViewModel.class);
        personViewModel = ViewModelProviders.of(this).get(PersonViewModel.class);
        categoryID = categoryViewModel.getCategoryID(categoryName);
        addButton.setOnClickListener((View v) -> {
            Intent intent = new Intent(getContext(), AddEditPerson.class);
            startActivityForResult(intent, HelperUtility.ADD_PERSON_REQUEST, savedState);
        });
        searchButton.setOnClickListener((View v) -> {
            Intent intent = new Intent(getContext(), SearchFunctionsActivity.class);
            startActivity(intent, savedState);
        });
        adapter = new PersonAdapter();
        peopleRecycler.setAdapter(adapter);
        personViewModel.searchByCategory(categoryID).observe(getViewLifecycleOwner(), person -> adapter.submitList(person));
        adapter.setOnItemClickListener(aPerson -> {
            Intent intent = new Intent(PeopleFragment.this.getContext(), DetailedPerson.class);
            HelperUtility.setActivePerson(aPerson.getPersonID());
            PeopleFragment.this.startActivity(intent);
        });
        return rootView;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == HelperUtility.ADD_PERSON_REQUEST && resultCode == RESULT_OK) {
            int categoryID = data.getIntExtra(AddEditPerson.EXTRA_CATEGORY_ID, -1);
            String firstName = data.getStringExtra(AddEditPerson.EXTRA_FIRST_NAME);
            String lastName = data.getStringExtra(AddEditPerson.EXTRA_LAST_NAME);
            String phoneNumber = data.getStringExtra(AddEditPerson.EXTRA_PHONE_NUMBER);
            String emailAddress = data.getStringExtra(AddEditPerson.EXTRA_EMAIL_ADDRESS);
            int birthDateID = data.getIntExtra(AddEditPerson.EXTRA_BIRTHDAY, -1);
            String importantDates = data.getStringExtra(AddEditPerson.EXTRA_IMPORTANT_DATES);
            String personalNotes = data.getStringExtra(AddEditPerson.EXTRA_NOTES);
            String pastTimes = data.getStringExtra(AddEditPerson.EXTRA_PAST_TIMES);
            String careerID = data.getStringExtra(AddEditPerson.EXTRA_CAREER_ID);

            APerson person = new APerson(categoryID, firstName, lastName, phoneNumber, emailAddress, birthDateID, importantDates, personalNotes, pastTimes, careerID);
            personViewModel.insert(person);

        }
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {

        inflater.inflate(R.menu.menu_main, menu);
        menu.getItem(0).setVisible(false);
        menu.getItem(1).setVisible(false);
        menu.getItem(2).setVisible(false);
        return;
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