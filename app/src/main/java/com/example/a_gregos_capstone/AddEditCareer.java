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

public class AddEditCareer extends AppCompatActivity {
    public static final String EXTRA_CAREER_ID = "com.example.a_gregos_capstone.EXTRA_CAREER_ID";
    public static final String EXTRA_CAREER_CATEGORY = "com.example.a_gregos_capstone.EXTRA_CAREER_CATEGORY";
    public static final String EXTRA_CAREER_SPECIALIZATION = "com.example.a_gregos_capstone.EXTRA_CAREER_SPECIALIZATION";
    public static final String EXTRA_COMPANY_NAME = "com.example.a_gregos_capstone.EXTRA_COMPANY_NAME";

    private Bundle savedState;
    private CareerViewModel careerViewModel;
    private PersonViewModel personViewModel;
    private Spinner careerCategoriesDropDown;
    private EditText editTextCompanyName;
    private EditText editTextSpecialization;
    private String careerCategory;
    private int personID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        savedState = savedInstanceState;
        getSupportActionBar();
        Intent intent = getIntent();
        setContentView(R.layout.add_edit_careers);

        personID = HelperUtility.getActivePerson();
        careerCategoriesDropDown = findViewById(R.id.career_category_spinner);
        editTextCompanyName = findViewById(R.id.edit_text_company_name);
        editTextSpecialization = findViewById(R.id.edit_text_specialization);
        careerViewModel = ViewModelProviders.of(this).get(CareerViewModel.class);
        personViewModel = ViewModelProviders.of(this).get(PersonViewModel.class);
        final ArrayList<String> items = HelperUtility.careerCategories();
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, items);
        careerCategoriesDropDown.setAdapter(adapter);
        careerCategoriesDropDown.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                careerCategory = items.get(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        if (intent.hasExtra(EXTRA_CAREER_ID)) {
            setTitle("Edit career");
            careerCategory = intent.getStringExtra(EXTRA_CAREER_CATEGORY);
            careerCategoriesDropDown.setSelection(items.indexOf(careerCategory));
            editTextCompanyName.setText(intent.getStringExtra(EXTRA_COMPANY_NAME));
            editTextSpecialization.setText(intent.getStringExtra(EXTRA_CAREER_SPECIALIZATION));
        } else {
            setTitle("Add career");
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.add_edit_menu, menu);
        return true;
    }

    public void saveCareer() {
        String companyName = editTextCompanyName.getText().toString();
        String specialization = editTextSpecialization.getText().toString();
        if (companyName.trim().isEmpty() || specialization.trim().isEmpty()) {
            Toast.makeText(this, "Please enter a company name and specialization.", Toast.LENGTH_SHORT).show();
            return;
        }
        Career career = new Career(personID, careerCategory, specialization, companyName);

        int id = getIntent().getIntExtra(EXTRA_CAREER_ID, -1);
        if (id != -1) {
            career.setCareerID(id);
            careerViewModel.update(career);
        } else {
            careerViewModel.insert(career);
            id = careerViewModel.getCareerID(personID, careerCategory, specialization, companyName);
        }
        Intent data = new Intent();
        data.putExtra(EXTRA_CAREER_ID, id);

        HelperUtility.setActivePerson(personID);

        setResult(RESULT_OK, data);
        finish();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.save_item:
                saveCareer();
                return true;
            case R.id.menu_cancel:
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
