package com.example.a_gregos_capstone;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.solver.widgets.Helper;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.lifecycle.ViewModelProviders;

public class DetailedCareerView extends AppCompatActivity {
    private Bundle savedState;
    private CareerViewModel careerViewModel;
    private PersonViewModel personViewModel;
    private TextView companyName;
    private TextView careerCategory;
    private TextView careerSpecialization;
    private Career career;
    private int personID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.detailed_career);

        personID = HelperUtility.getActivePerson();
        savedState = savedInstanceState;
        personViewModel = ViewModelProviders.of(this).get(PersonViewModel.class);
        careerViewModel = ViewModelProviders.of(this).get(CareerViewModel.class);

        companyName = findViewById(R.id.text_view_company_name);
        careerCategory = findViewById(R.id.text_view_career_category);
        careerSpecialization = findViewById(R.id.text_view_specialization);
        careerSpecialization.setMaxLines(20);
        updateViews();

    }

    public void updateViews() {
        career = careerViewModel.getCareer(HelperUtility.getActiveCareer());
        companyName.setText(career.getCompanyName());
        careerCategory.setText(career.getCareerCategory());
        careerSpecialization.setText(career.getCareerSpecialization());
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == HelperUtility.EDIT_CAREER_REQUEST && resultCode == RESULT_OK) {
            career = careerViewModel.getCareer(data.getIntExtra(AddEditCareer.EXTRA_CAREER_ID, -1));
            APerson person = personViewModel.getAPerson(personID);
            personViewModel.update(person);
            HelperUtility.setActivePerson(person.getPersonID());
            HelperUtility.setActiveCareer(career.getCareerID());
        }
        updateViews();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu_main, menu);
        menu.getItem(0).setTitle("Edit career");
        menu.getItem(1).setTitle("Delete this career");
        menu.getItem(2).setVisible(false);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Intent intent;
        switch (item.getItemId()) {
            case R.id.menu_option_0:
                HelperUtility.setActiveCareer(career.getCareerID());
                intent = new Intent(this, AddEditCareer.class);
                intent.putExtra(AddEditCareer.EXTRA_CAREER_ID, career.getCareerID());
                intent.putExtra(AddEditCareer.EXTRA_COMPANY_NAME, career.getCompanyName());
                intent.putExtra(AddEditCareer.EXTRA_CAREER_CATEGORY, career.getCareerCategory());
                intent.putExtra(AddEditCareer.EXTRA_CAREER_SPECIALIZATION, career.getCareerSpecialization());
                startActivityForResult(intent, HelperUtility.EDIT_CAREER_REQUEST, savedState);
                return true;
            case R.id.menu_option_1:
                APerson person = personViewModel.getAPerson(HelperUtility.getActivePerson());
                person.setCareerIDs(HelperUtility.removeElement(person.getCareerIDs(), HelperUtility.getActiveCareer()));
                career = careerViewModel.getCareer(HelperUtility.getActiveCareer());
                personViewModel.update(person);
                careerViewModel.delete(career);
                finish();
            case R.id.menu_option_2:
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}