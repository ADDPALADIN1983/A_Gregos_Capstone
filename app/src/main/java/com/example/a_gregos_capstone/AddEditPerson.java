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
import java.util.List;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;

public class AddEditPerson extends AppCompatActivity {
    public static final String EXTRA_PERSON_ID = "com.example.a_gregos_capstone.EXTRA_PERSON_ID";
    public static final String EXTRA_CATEGORY_ID = "com.example.a_gregos_capstone.EXTRA_CATEGORY_ID";
    public static final String EXTRA_FIRST_NAME = "com.example.a_gregos_capstone.EXTRA_FIRST_NAME";
    public static final String EXTRA_LAST_NAME = "com.example.a_gregos_capstone.EXTRA_LAST_NAME";
    public static final String EXTRA_PHONE_NUMBER = "com.example.a_gregos_capstone.EXTRA_PHONE_NUMBER";
    public static final String EXTRA_EMAIL_ADDRESS = "com.example.a_gregos_capstone.EXTRA_EMAIL_ADDRESS";
    public static final String EXTRA_BIRTHDAY = "com.example.a_gregos_capstone.EXTRA_BIRTHDAY";
    public static final String EXTRA_IMPORTANT_DATES = "com.example.a_gregos_capstone.EXTRA_IMPORTANT_DATES";
    public static final String EXTRA_NOTES = "com.example.a_gregos_capstone.EXTRA_NOTES";
    public static final String EXTRA_PAST_TIMES = "com.example.a_gregos_capstone.EXTRA_PAST_TIMES";
    public static final String EXTRA_CAREER_ID = "com.example.a_gregos_capstone.EXTRA_CAREER_ID";

    private Bundle savedState;
    private PersonCategoryViewModel categoryViewModel;
    private Spinner categoryDropDown;
    private int category;
    private EditText firstName;
    private EditText lastName;
    private EditText phoneNumber;
    private EditText emailAddress;
    private int birthday;
    private String importantDates;
    private String notes;
    private String pastTimes;
    private String careerIDs;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        savedState = savedInstanceState;
        getSupportActionBar();
        Intent intent = getIntent();
        setContentView(R.layout.add_edit_person);
        firstName = findViewById(R.id.edit_text_first_name);
        lastName = findViewById(R.id.edit_text_last_name);
        phoneNumber = findViewById(R.id.edit_text_phone_number);
        emailAddress = findViewById(R.id.edit_text_email_address);
        categoryDropDown = findViewById(R.id.person_category_spinner);
        categoryViewModel = ViewModelProviders.of(this).get(PersonCategoryViewModel.class);
        final List<String> items = categoryViewModel.getCategoryNames();
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, items);
        categoryDropDown.setAdapter(adapter);
        categoryDropDown.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                category = position + 1;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        if (intent.hasExtra(EXTRA_PERSON_ID)) {
            setTitle("Edit person");
            firstName.setText(intent.getStringExtra(EXTRA_FIRST_NAME));
            lastName.setText(intent.getStringExtra(EXTRA_LAST_NAME));
            phoneNumber.setText(intent.getStringExtra(EXTRA_PHONE_NUMBER));
            emailAddress.setText(intent.getStringExtra(EXTRA_EMAIL_ADDRESS));
            category = intent.getIntExtra(EXTRA_CATEGORY_ID, -1)-1;
            List<String> personCategoryList = categoryViewModel.getCategoryNames();
            categoryDropDown.setSelection(adapter.getPosition(personCategoryList.get(category)));
            birthday = intent.getIntExtra(EXTRA_BIRTHDAY, -1);
            importantDates = intent.getStringExtra(EXTRA_IMPORTANT_DATES);
            notes = intent.getStringExtra(EXTRA_NOTES);
            pastTimes = intent.getStringExtra(EXTRA_PAST_TIMES);
            careerIDs = intent.getStringExtra(EXTRA_CAREER_ID);
        } else {
            setTitle("Add person");
            birthday = -1;
            importantDates = "";
            notes = "";
            pastTimes = "";
            careerIDs = "";
        }

    }

    public void savePerson() {
        String first = firstName.getText().toString();
        String last = lastName.getText().toString();
        String phone;
        if (phoneNumber.getText().toString().isEmpty()) {
            phone = "";
        } else {
            phone = HelperUtility.filterString(phoneNumber.getText().toString());
        }
        String email;
        if (emailAddress.getText().toString().isEmpty()) {
            email = "";
        } else {
            email = emailAddress.getText().toString();
        }

        if (first.trim().isEmpty() || last.trim().isEmpty()) {
            Toast.makeText(this, "Please enter a first and last name.", Toast.LENGTH_SHORT).show();
            return;
        }

        Intent data = new Intent();
        data.putExtra(EXTRA_CATEGORY_ID, category);
        data.putExtra(EXTRA_FIRST_NAME, first);
        data.putExtra(EXTRA_LAST_NAME, last);
        data.putExtra(EXTRA_PHONE_NUMBER, phone);
        data.putExtra(EXTRA_EMAIL_ADDRESS, email);
        data.putExtra(EXTRA_BIRTHDAY, birthday);
        data.putExtra(EXTRA_IMPORTANT_DATES, importantDates);
        data.putExtra(EXTRA_NOTES, notes);
        data.putExtra(EXTRA_PAST_TIMES, pastTimes);
        data.putExtra(EXTRA_CAREER_ID, careerIDs);
        int id = getIntent().getIntExtra(EXTRA_PERSON_ID, -1);

        if (id != -1) {
            data.putExtra(EXTRA_PERSON_ID, id);
        }
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
                savePerson();
                return true;
            case R.id.menu_cancel:
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}