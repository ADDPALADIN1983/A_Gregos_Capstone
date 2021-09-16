package com.example.a_gregos_capstone;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import androidx.appcompat.app.AppCompatActivity;

public class SearchFunctionsActivity extends AppCompatActivity {
    Bundle savedState;
    private int personID;
    private PersonViewModel personViewModel;
    private CareerViewModel careerViewModel;
    private PastTimesViewModel pastTimesViewModel;
    private ListView resultListView;
    private Spinner primaryFilter;
    private Spinner secondarySpinner;
    private EditText editTextSearchBox;
    private EditText editTextSearchBoxTwo;
    private TextView negativeResults;
    private Button searchButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        savedState = savedInstanceState;
        setContentView(R.layout.search_functions);

        resultListView = findViewById(R.id.result_recycler_view);
        primaryFilter = findViewById(R.id.search_category_primary_spinner);
        secondarySpinner = findViewById(R.id.secondary_dropdown_spinner);
        editTextSearchBox = findViewById(R.id.search_string_primary);
        editTextSearchBoxTwo = findViewById(R.id.search_string_secondary);
        negativeResults = findViewById(R.id.negative_results);

        personViewModel = new PersonViewModel(getApplication());
        careerViewModel = new CareerViewModel(getApplication());
        pastTimesViewModel = new PastTimesViewModel(getApplication());

        searchButton = findViewById(R.id.start_search);
        editTextSearchBox.setVisibility(View.GONE);
        editTextSearchBoxTwo.setVisibility(View.GONE);
        secondarySpinner.setVisibility(View.GONE);
        negativeResults.setVisibility(View.GONE);

        ArrayList<String> searchCategories = new ArrayList<>();
        searchCategories.add(0, "Company name");
        searchCategories.add(1, "First name");
        searchCategories.add(2, "Last name");
        searchCategories.add(3, "Career category");
        searchCategories.add(4, "Career specialization");
        searchCategories.add(5, "Phone number");
        searchCategories.add(6, "Past times");
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, searchCategories);
        ArrayList<String> careerCategories = HelperUtility.careerCategories();
        ArrayAdapter<String> secondaryAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, careerCategories);
        primaryFilter.setAdapter(adapter);
        primaryFilter.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                negativeResults.setVisibility(View.GONE);
                resultListView.setVisibility(View.GONE);
                switch (position) {
                    case 0:
                        editTextSearchBox.setVisibility(View.VISIBLE);
                        editTextSearchBox.setHint("Enter company name");
                        editTextSearchBoxTwo.setVisibility(View.GONE);
                        secondarySpinner.setVisibility(View.GONE);
                        break;
                    case 1:
                        editTextSearchBox.setVisibility(View.VISIBLE);
                        editTextSearchBox.setHint("First name");
                        editTextSearchBoxTwo.setVisibility(View.GONE);
                        secondarySpinner.setVisibility(View.GONE);
                        break;
                    case 2:
                        editTextSearchBox.setVisibility(View.VISIBLE);
                        editTextSearchBox.setHint("Last name");
                        editTextSearchBoxTwo.setVisibility(View.GONE);
                        secondarySpinner.setVisibility(View.GONE);
                        break;
                    case 3:
                        editTextSearchBox.setVisibility(View.GONE);
                        editTextSearchBoxTwo.setVisibility(View.GONE);
                        secondarySpinner.setVisibility(View.VISIBLE);
                        secondarySpinner.setAdapter(secondaryAdapter);
                        break;
                    case 4:
                        editTextSearchBox.setVisibility(View.VISIBLE);
                        editTextSearchBox.setHint("Career specialization");
                        editTextSearchBoxTwo.setVisibility(View.GONE);
                        secondarySpinner.setVisibility(View.GONE);
                        break;
                    case 5:
                        editTextSearchBox.setVisibility(View.VISIBLE);
                        editTextSearchBox.setHint("Phone number");
                        editTextSearchBoxTwo.setVisibility(View.GONE);
                        secondarySpinner.setVisibility(View.GONE);
                        break;
                    case 6:
                        editTextSearchBox.setVisibility(View.VISIBLE);
                        editTextSearchBox.setHint("Past time name");
                        editTextSearchBoxTwo.setVisibility(View.GONE);
                        secondarySpinner.setVisibility(View.GONE);
                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        searchButton.setOnClickListener(view -> SearchFunctionsActivity.this.updateResultViews());

    }

    private void updateResultViews() {
        int position = primaryFilter.getSelectedItemPosition();
        ArrayList<CompositeEntry> results = new ArrayList<>();
        List<APerson> people;
        List<Career> careers;
        List<PastTimes> pastTime;
        String searchThread;
        switch (position) {
            case 0:
                if (editTextSearchBox.getText().toString().trim().isEmpty()) {
                    Toast.makeText(this, "Please enter a company name.", Toast.LENGTH_SHORT).show();
                } else {
                    searchThread = editTextSearchBox.getText().toString();
                    careers = careerViewModel.searchCompanyName(searchThread);
                    if (careers.isEmpty()) {
                        resultListView.setVisibility(View.GONE);
                        negativeResults.setVisibility(View.VISIBLE);
                    } else {
                        resultListView.setVisibility(View.VISIBLE);
                        negativeResults.setVisibility(View.GONE);
                        for (int i = 0; i < careers.size(); i++) {
                            Career c = careers.get(i);
                            CompositeEntry entry = new CompositeEntry(c.getPersonID(), c.getCareerCategory(), c.getCareerSpecialization(), c.getCompanyName());
                            APerson person = personViewModel.getAPerson(c.getPersonID());
                            entry.setFirstName(person.getFirstName());
                            entry.setLastName(person.getLastName());
                            results.add(entry);
                        }
                    }
                }
                break;
            case 1:
                if (editTextSearchBox.getText().toString().trim().isEmpty()) {
                    Toast.makeText(this, "Please enter a first name.", Toast.LENGTH_SHORT).show();
                } else {
                    searchThread = editTextSearchBox.getText().toString();
                    people = personViewModel.searchFirstName(searchThread);
                    if (people.isEmpty()) {
                        resultListView.setVisibility(View.GONE);
                        negativeResults.setVisibility(View.VISIBLE);
                    } else {
                        resultListView.setVisibility(View.VISIBLE);
                        negativeResults.setVisibility(View.GONE);
                        for (int i = 0; i < people.size(); i++) {
                            APerson person = people.get(i);
                            CompositeEntry entry = new CompositeEntry(person.getPersonID(), "Email: " + person.getEmailAddress(), "Phone: " + person.getPhoneNumber());
                            entry.setFirstName(person.getFirstName());
                            entry.setLastName(person.getLastName());
                            results.add(entry);
                        }
                    }
                }
                break;
            case 2:
                if (editTextSearchBox.getText().toString().trim().isEmpty()) {
                    Toast.makeText(this, "Please enter a last name.", Toast.LENGTH_SHORT).show();
                } else {
                    searchThread = editTextSearchBox.getText().toString();
                    people = personViewModel.searchLastName(searchThread);
                    if (people.isEmpty()) {
                        resultListView.setVisibility(View.GONE);
                        negativeResults.setVisibility(View.VISIBLE);
                    } else {
                        resultListView.setVisibility(View.VISIBLE);
                        negativeResults.setVisibility(View.GONE);
                        for (int i = 0; i < people.size(); i++) {
                            APerson person = people.get(i);
                            CompositeEntry entry = new CompositeEntry(person.getPersonID(), "Email: " + person.getEmailAddress(), "Phone: " + person.getPhoneNumber());
                            entry.setFirstName(person.getFirstName());
                            entry.setLastName(person.getLastName());
                            results.add(entry);
                        }
                    }
                }
                break;
            case 3:
                searchThread = secondarySpinner.getSelectedItem().toString();
                careers = careerViewModel.searchByCareerCategoryName(searchThread);
                if (careers.isEmpty()) {
                    resultListView.setVisibility(View.GONE);
                    negativeResults.setVisibility(View.VISIBLE);
                } else {
                    resultListView.setVisibility(View.VISIBLE);
                    negativeResults.setVisibility(View.GONE);
                    for (int i = 0; i < careers.size(); i++) {
                        Career c = careers.get(i);
                        CompositeEntry entry = new CompositeEntry(c.getPersonID(), c.getCareerCategory(), c.getCareerSpecialization(), c.getCompanyName());
                        APerson person = personViewModel.getAPerson(c.getPersonID());
                        entry.setFirstName(person.getFirstName());
                        entry.setLastName(person.getLastName());
                        results.add(entry);
                    }
                }
                break;
            case 4:
                if (editTextSearchBox.getText().toString().trim().isEmpty()) {
                    Toast.makeText(this, "Please enter a career specialization.", Toast.LENGTH_SHORT).show();
                } else {
                    searchThread = editTextSearchBox.getText().toString();
                    careers = careerViewModel.searchByCareerSpecialization(searchThread);
                    if (careers.isEmpty()) {
                        resultListView.setVisibility(View.GONE);
                        negativeResults.setVisibility(View.VISIBLE);
                    } else {
                        resultListView.setVisibility(View.VISIBLE);
                        negativeResults.setVisibility(View.GONE);
                        for (int i = 0; i < careers.size(); i++) {
                            Career c = careers.get(i);
                            CompositeEntry entry = new CompositeEntry(c.getPersonID(), c.getCareerCategory(), c.getCareerSpecialization(), c.getCompanyName());
                            APerson person = personViewModel.getAPerson(c.getPersonID());
                            entry.setFirstName(person.getFirstName());
                            entry.setLastName(person.getLastName());
                            results.add(entry);
                        }
                    }
                }
                break;
            case 5:
                if (editTextSearchBox.getText().toString().trim().isEmpty()) {
                    Toast.makeText(this, "Please enter a partial phone number.", Toast.LENGTH_SHORT).show();
                } else {
                    searchThread = editTextSearchBox.getText().toString();
                    people = personViewModel.searchByPartialPhone(searchThread);
                    if (people.isEmpty()) {
                        resultListView.setVisibility(View.GONE);
                        negativeResults.setVisibility(View.VISIBLE);
                    } else {
                        resultListView.setVisibility(View.VISIBLE);
                        negativeResults.setVisibility(View.GONE);
                        for (int i = 0; i < people.size(); i++) {
                            APerson person = people.get(i);
                            CompositeEntry entry = new CompositeEntry(person.getPersonID(), "Email: " + person.getEmailAddress(), "Phone: " + person.getPhoneNumber());
                            entry.setFirstName(person.getFirstName());
                            entry.setLastName(person.getLastName());
                            results.add(entry);
                        }
                    }
                }
                break;
            case 6:
                if (editTextSearchBox.getText().toString().trim().isEmpty()) {
                    Toast.makeText(this, "Please enter a past time name.", Toast.LENGTH_SHORT).show();
                } else {
                    searchThread = editTextSearchBox.getText().toString();
                    pastTime = pastTimesViewModel.searchByPastTime(searchThread);
                    if (pastTime.isEmpty()) {
                        resultListView.setVisibility(View.GONE);
                        negativeResults.setVisibility(View.VISIBLE);
                    } else {
                        resultListView.setVisibility(View.VISIBLE);
                        negativeResults.setVisibility(View.GONE);
                        for (int i = 0; i < pastTime.size(); i++) {
                            PastTimes pastTimes = pastTime.get(i);
                            CompositeEntry entry = new CompositeEntry(pastTimes.getPersonID(), pastTimes.getPastTimeName(), pastTimes.getPastTimeDetails());
                            APerson person = personViewModel.getAPerson(pastTimes.getPersonID());
                            entry.setFirstName(person.getFirstName());
                            entry.setLastName(person.getLastName());
                            results.add(entry);
                        }
                    }
                }
                break;
        }
        CompositeAdapter resultAdapter = new CompositeAdapter(this, results);
        resultListView.setAdapter(resultAdapter);

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

    public class CompositeEntry {
        private int personID;
        private String firstName;
        private String lastName;
        private String entryOne;
        private String entryTwo;
        private String entryThree;
        private boolean hasThirdString;

        public CompositeEntry(int personID, String entryOne, String entryTwo, String entryThree) {
            this.personID = personID;
            this.entryOne = entryOne;
            this.entryTwo = entryTwo;
            this.entryThree = entryThree;
            this.hasThirdString = true;
        }

        public CompositeEntry(int personID, String entryOne, String entryTwo) {
            this.personID = personID;
            this.entryOne = entryOne;
            this.entryTwo = entryTwo;
            this.hasThirdString = false;
        }

        public int getPersonID() {
            return personID;
        }

        public void setPersonID(int personID) {
            this.personID = personID;
        }

        public String getFirstName() {
            return firstName;
        }

        public void setFirstName(String firstName) {
            this.firstName = firstName;
        }

        public String getLastName() {
            return lastName;
        }

        public void setLastName(String lastName) {
            this.lastName = lastName;
        }

        public String getEntryOne() {
            return entryOne;
        }

        public void setEntryOne(String entryOne) {
            this.entryOne = entryOne;
        }

        public String getEntryTwo() {
            return entryTwo;
        }

        public void setEntryTwo(String entryTwo) {
            this.entryTwo = entryTwo;
        }

        public String getEntryThree() {
            return entryThree;
        }

        public void setEntryThree(String entryThree) {
            this.entryThree = entryThree;
        }

        public boolean isHasThirdString() {
            return hasThirdString;
        }

        public void setHasThirdString(boolean hasThirdString) {
            this.hasThirdString = hasThirdString;
        }
    }

    public class CompositeAdapter extends ArrayAdapter<CompositeEntry> {
        public CompositeAdapter(Activity context, ArrayList<CompositeEntry> listEntry) {
            super(context, 0, listEntry);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View listItemView = convertView;
            if (listItemView == null) {
                listItemView = LayoutInflater.from(getContext()).inflate(
                        R.layout.composite_item, parent, false);
            }
            CompositeEntry currentListEntry = getItem(position);
            TextView firstName = (TextView) listItemView.findViewById(R.id.result_first_name);
            firstName.setText(currentListEntry.getFirstName());
            TextView lastName = (TextView) listItemView.findViewById(R.id.result_last_name);
            lastName.setText(currentListEntry.getLastName());
            TextView firstString = (TextView) listItemView.findViewById(R.id.result_first_data_string);
            firstString.setText(currentListEntry.getEntryOne());
            TextView secondString = (TextView) listItemView.findViewById(R.id.result_second_data_string);
            secondString.setText(currentListEntry.getEntryTwo());
            TextView thirdString = (TextView) listItemView.findViewById(R.id.result_third_data_string);
            if (currentListEntry.isHasThirdString()) {
                thirdString.setText(currentListEntry.getEntryThree());
            } else {
                thirdString.setVisibility(View.GONE);
            }

            listItemView.setOnClickListener(view -> {
                HelperUtility.setActivePerson(currentListEntry.getPersonID());
                Intent intent = new Intent(getContext(), DetailedPerson.class);
                startActivity(intent);
            });
            return listItemView;
        }
    }
}
