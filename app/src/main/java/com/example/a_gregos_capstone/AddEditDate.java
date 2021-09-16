package com.example.a_gregos_capstone;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;

public class AddEditDate extends AppCompatActivity {
    public static final String EXTRA_DATE_ID = "com.example.a_gregos_capstone.EXTRA_DATE_ID";
    public static final String EXTRA_DATE_TYPE = "com.example.a_gregos_capstone.EXTRA_DATE_TYPE";
    public static final String EXTRA_DATE_YEAR = "com.example.a_gregos_capstone.EXTRA_DATE_YEAR";
    public static final String EXTRA_DATE_MONTH = "com.example.a_gregos_capstone.EXTRA_DATE_MONTH";
    public static final String EXTRA_DATE_DAY = "com.example.a_gregos_capstone.EXTRA_DATE_DAY";
    public static final String EXTRA_DATE_DESCRIPTION = "com.example.a_gregos_capstone.EXTRA_DATE_DESCRIPTION";

    private Spinner type;
    private EditText editTextYear;
    private EditText editTextMonth;
    private EditText editTextDay;
    private EditText editTextDescription;
    private PersonDate personDate;
    private PersonViewModel personViewModel;
    private PersonDateViewModel dateViewModel;
    private int personID;
    private String typePlaceholder = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar();
        setContentView(R.layout.add_edit_dates);
        Intent intent = getIntent();

        type = findViewById(R.id.type_spinner);
        editTextYear = findViewById(R.id.edit_text_year);
        editTextMonth = findViewById(R.id.edit_text_month);
        editTextDay = findViewById(R.id.edit_text_day);
        editTextDescription = findViewById(R.id.edit_text_date_description);
        personViewModel = ViewModelProviders.of(this).get(PersonViewModel.class);
        dateViewModel = ViewModelProviders.of(this).get(PersonDateViewModel.class);
        final ArrayList<String> types = HelperUtility.getDateTypes();
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, types);
        type.setAdapter(adapter);
        type.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                typePlaceholder = types.get(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        personID = HelperUtility.getActivePerson();
        if (intent.hasExtra(EXTRA_DATE_ID)) {
            setTitle("Edit date");
            personDate = dateViewModel.getDate(HelperUtility.getActiveDate());
            typePlaceholder = intent.getStringExtra(EXTRA_DATE_TYPE);
            type.setSelection(adapter.getPosition(typePlaceholder));
            editTextYear.setText(intent.getStringExtra(EXTRA_DATE_YEAR));
            editTextMonth.setText(intent.getStringExtra(EXTRA_DATE_MONTH));
            editTextDay.setText(intent.getStringExtra(EXTRA_DATE_DAY));
            editTextDescription.setText(intent.getStringExtra(EXTRA_DATE_DESCRIPTION));
        } else {
            setTitle("Add date");

        }

    }


    private void saveDate() {
        String year = editTextYear.getText().toString();
        String month = editTextMonth.getText().toString();
        String day = editTextDay.getText().toString();
        String description = editTextDescription.getText().toString();
        int dateID = getIntent().getIntExtra(EXTRA_DATE_ID, -1);
        if (description.trim().isEmpty() || year.trim().isEmpty() || month.trim().isEmpty() || day.trim().isEmpty()) {
            Toast.makeText(this, "Please enter a month, day, year and description.", Toast.LENGTH_SHORT).show();
            return;
        }

        PersonDate date = new PersonDate(personID, typePlaceholder, Integer.parseInt(year), Integer.parseInt(month), Integer.parseInt(day), description);

        if (dateID != -1) {
            date.setDateID(dateID);
            dateViewModel.update(date);
        } else {
            dateViewModel.insert(date);
            dateID = dateViewModel.getDateID(personID, Integer.parseInt(year), Integer.parseInt(month), Integer.parseInt(day), description);
            if (typePlaceholder.equalsIgnoreCase("Birthday")) {
                APerson person = personViewModel.getAPerson(personID);
                person.setBirthDateID(dateID);
                personViewModel.update(person);
            }
        }
        Intent data = new Intent();
        data.putExtra(EXTRA_DATE_ID, dateID);
        HelperUtility.setActivePerson(personID);
        HelperUtility.setActiveDate(dateID);

        setResult(RESULT_OK, data);
        finish();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.add_edit_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.save_item:
                saveDate();
                return true;
            case R.id.menu_cancel:
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
