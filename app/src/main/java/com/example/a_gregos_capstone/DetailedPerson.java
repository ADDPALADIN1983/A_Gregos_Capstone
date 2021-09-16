package com.example.a_gregos_capstone;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.List;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;

public class DetailedPerson extends AppCompatActivity {
    private Bundle savedState;
    private PersonDateViewModel dateViewModel;
    private PersonViewModel personViewModel;
    private TextView firstName;
    private TextView lastName;
    private TextView phoneNumber;
    private TextView emailAddress;
    private TextView birthdayView;
    private Button addPhoneNumber;
    private Button addEmailAddress;
    private Button addBirthday;
    private Button addImportantDates;
    private Button addPastTimes;
    private Button addNotes;
    private Button addCareers;
    private APerson currentPerson;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.detailed_person);
        savedState = savedInstanceState;
        personViewModel = ViewModelProviders.of(this).get(PersonViewModel.class);
        dateViewModel = ViewModelProviders.of(this).get(PersonDateViewModel.class);

        firstName = findViewById(R.id.text_view_first_name);
        lastName = findViewById(R.id.text_view_last_name);
        phoneNumber = findViewById(R.id.text_view_phone_number);
        emailAddress = findViewById(R.id.text_view_email_address);
        birthdayView = findViewById(R.id.text_view_birthday);
        addPhoneNumber = findViewById(R.id.add_phone_button);
        addEmailAddress = findViewById(R.id.add_email_button);
        addBirthday = findViewById(R.id.add_birthday_button);
        addImportantDates = findViewById(R.id.important_dates_button);
        addPastTimes = findViewById(R.id.past_times_button);
        addNotes = findViewById(R.id.notes_button);
        addCareers = findViewById(R.id.careers_button);
        updateViews();
    }

    public void updateViews() {
        currentPerson = personViewModel.getAPerson(HelperUtility.getActivePerson());
        firstName.setText(currentPerson.getFirstName());
        lastName.setText(currentPerson.getLastName());

        if (currentPerson.getPhoneNumber().trim().isEmpty()) {
            phoneNumber.setVisibility(View.GONE);
            addPhoneNumber.setVisibility(View.VISIBLE);
            addPhoneNumber.setOnClickListener(v -> {
                Intent intent = new Intent(DetailedPerson.this, AddEditPerson.class);
                intent.putExtra(AddEditPerson.EXTRA_PERSON_ID, currentPerson.getPersonID());
                intent.putExtra(AddEditPerson.EXTRA_FIRST_NAME, currentPerson.getFirstName());
                intent.putExtra(AddEditPerson.EXTRA_LAST_NAME, currentPerson.getLastName());
                intent.putExtra(AddEditPerson.EXTRA_PHONE_NUMBER, currentPerson.getPhoneNumber());
                intent.putExtra(AddEditPerson.EXTRA_EMAIL_ADDRESS, currentPerson.getEmailAddress());
                intent.putExtra(AddEditPerson.EXTRA_CATEGORY_ID, currentPerson.getCategoryID());
                intent.putExtra(AddEditPerson.EXTRA_BIRTHDAY, currentPerson.getBirthDateID());
                intent.putExtra(AddEditPerson.EXTRA_IMPORTANT_DATES, currentPerson.getImportantDates());
                intent.putExtra(AddEditPerson.EXTRA_NOTES, currentPerson.getPersonalNotes());
                intent.putExtra(AddEditPerson.EXTRA_PAST_TIMES, currentPerson.getPastTimes());
                intent.putExtra(AddEditPerson.EXTRA_CAREER_ID, currentPerson.getCareerIDs());
                DetailedPerson.this.startActivityForResult(intent, HelperUtility.EDIT_PERSON_REQUEST, savedState);
            });
        } else {
            addPhoneNumber.setVisibility(View.GONE);
            phoneNumber.setVisibility(View.VISIBLE);
            phoneNumber.setText(currentPerson.getPhoneNumber());
        }

        if (currentPerson.getEmailAddress().trim().isEmpty()) {
            emailAddress.setVisibility(View.GONE);
            addEmailAddress.setVisibility(View.VISIBLE);
            addEmailAddress.setOnClickListener(v -> {
                Intent intent = new Intent(DetailedPerson.this, AddEditPerson.class);
                intent.putExtra(AddEditPerson.EXTRA_PERSON_ID, currentPerson.getPersonID());
                intent.putExtra(AddEditPerson.EXTRA_FIRST_NAME, currentPerson.getFirstName());
                intent.putExtra(AddEditPerson.EXTRA_LAST_NAME, currentPerson.getLastName());
                intent.putExtra(AddEditPerson.EXTRA_PHONE_NUMBER, currentPerson.getPhoneNumber());
                intent.putExtra(AddEditPerson.EXTRA_EMAIL_ADDRESS, currentPerson.getEmailAddress());
                intent.putExtra(AddEditPerson.EXTRA_CATEGORY_ID, currentPerson.getCategoryID());
                intent.putExtra(AddEditPerson.EXTRA_BIRTHDAY, currentPerson.getBirthDateID());
                intent.putExtra(AddEditPerson.EXTRA_IMPORTANT_DATES, currentPerson.getImportantDates());
                intent.putExtra(AddEditPerson.EXTRA_NOTES, currentPerson.getPersonalNotes());
                intent.putExtra(AddEditPerson.EXTRA_PAST_TIMES, currentPerson.getPastTimes());
                intent.putExtra(AddEditPerson.EXTRA_CAREER_ID, currentPerson.getCareerIDs());
                DetailedPerson.this.startActivityForResult(intent, HelperUtility.EDIT_PERSON_REQUEST, savedState);
            });
        } else {
            addEmailAddress.setVisibility(View.GONE);
            emailAddress.setVisibility(View.VISIBLE);
            emailAddress.setText(currentPerson.getEmailAddress());
        }

        if (currentPerson.getBirthDateID() == -1) {
            birthdayView.setVisibility(View.GONE);
            addBirthday.setVisibility(View.VISIBLE);
            addBirthday.setOnClickListener(v -> {
                Intent intent = new Intent(DetailedPerson.this, AddEditDate.class);
                DetailedPerson.this.startActivityForResult(intent, HelperUtility.ADD_DATE_REQUEST, savedState);
            });
        } else {
            addBirthday.setVisibility(View.GONE);
            birthdayView.setVisibility(View.VISIBLE);
            birthdayView.setText(dateViewModel.getDate(currentPerson.getBirthDateID()).toString());

        }

        if (currentPerson.getImportantDates().trim().isEmpty()) {
            addImportantDates.setText("Add a date");
            addImportantDates.setOnClickListener(v -> {
                Intent intent = new Intent(DetailedPerson.this, AddEditDate.class);
                DetailedPerson.this.startActivityForResult(intent, HelperUtility.ADD_DATE_REQUEST, savedState);
            });
        } else {
            addImportantDates.setText("View important dates");
            addImportantDates.setOnClickListener(v -> {
                Intent intent = new Intent(DetailedPerson.this, ImportantDateListActivity.class);
                DetailedPerson.this.startActivity(intent, savedState);
            });
        }

        if (currentPerson.getPastTimes().trim().isEmpty()) {
            addPastTimes.setText("Add a past time");
            addPastTimes.setOnClickListener(v -> {
                Intent intent = new Intent(DetailedPerson.this, AddEditPastTimes.class);
                DetailedPerson.this.startActivityForResult(intent, HelperUtility.ADD_PAST_TIME_REQUEST, savedState);
            });
        } else {
            addPastTimes.setText("View past times");
            addPastTimes.setOnClickListener(v -> {
                Intent intent = new Intent(DetailedPerson.this, PastTimesListActivity.class);
                DetailedPerson.this.startActivity(intent, savedState);
            });
        }

        if (currentPerson.getPersonalNotes().trim().isEmpty()) {
            addNotes.setText("Add a note");
            addNotes.setOnClickListener(v -> {
                Intent intent = new Intent(DetailedPerson.this, AddEditNote.class);
                DetailedPerson.this.startActivityForResult(intent, HelperUtility.ADD_NOTE_REQUEST, savedState);
            });
        } else {
            addNotes.setText("View notes");
            addNotes.setOnClickListener(v -> {
                Intent intent = new Intent(DetailedPerson.this, NoteListActivity.class);
                DetailedPerson.this.startActivity(intent, savedState);
            });
        }

        if (currentPerson.getCareerIDs().trim().isEmpty()) {
            addCareers.setText("Add a career");
            addCareers.setOnClickListener(v -> {
                Intent intent = new Intent(DetailedPerson.this, AddEditCareer.class);
                DetailedPerson.this.startActivityForResult(intent, HelperUtility.ADD_CAREER_REQUEST, savedState);
            });
        } else {
            addCareers.setText("View careers");
            addCareers.setOnClickListener(v -> {
                Intent intent = new Intent(DetailedPerson.this, CareerListActivity.class);
                DetailedPerson.this.startActivity(intent, savedState);
            });
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == HelperUtility.EDIT_PERSON_REQUEST && resultCode == RESULT_OK) {
            int personID = data.getIntExtra(AddEditPerson.EXTRA_PERSON_ID, -1);
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

            APerson person = new APerson(personID, categoryID, firstName, lastName, phoneNumber, emailAddress, birthDateID, importantDates, personalNotes, pastTimes, careerID);
            personViewModel.update(person);
            HelperUtility.setActivePerson(person.getPersonID());
            updateViews();
        }

        if (requestCode == HelperUtility.ADD_DATE_REQUEST && resultCode == RESULT_OK) {
            int personID = HelperUtility.getActivePerson();
            int dateID = data.getIntExtra(AddEditDate.EXTRA_DATE_ID, -1);
            APerson person = personViewModel.getAPerson(personID);
            PersonDate date = dateViewModel.getDate(dateID);
            if (date.getDateType().equalsIgnoreCase("Birthday")) {
                person.setBirthDateID(dateID);
            }

            person.setImportantDates(HelperUtility.addElement(person.getImportantDates(), dateID));
            personViewModel.update(person);
            HelperUtility.setActivePerson(person.getPersonID());
            updateViews();
        }

        if (requestCode == HelperUtility.ADD_PAST_TIME_REQUEST && resultCode == RESULT_OK) {
            int personID = HelperUtility.getActivePerson();
            int pastTimeID = data.getIntExtra(AddEditPastTimes.EXTRA_PAST_TIMES_ID, -1);
            APerson person = personViewModel.getAPerson(personID);
            person.setPastTimes(HelperUtility.addElement(person.getPastTimes(), pastTimeID));
            personViewModel.update(person);
            HelperUtility.setActivePerson(person.getPersonID());
            updateViews();
        }

        if (requestCode == HelperUtility.ADD_NOTE_REQUEST && resultCode == RESULT_OK) {
            int personID = HelperUtility.getActivePerson();
            int noteID = data.getIntExtra(AddEditNote.EXTRA_NOTE_ID, -1);
            APerson person = personViewModel.getAPerson(personID);
            person.setPersonalNotes(HelperUtility.addElement(person.getPersonalNotes(), noteID));

            personViewModel.update(person);
            HelperUtility.setActivePerson(person.getPersonID());
            updateViews();
        }

        if (requestCode == HelperUtility.ADD_CAREER_REQUEST && resultCode == RESULT_OK) {
            int personID = HelperUtility.getActivePerson();
            int careerID = data.getIntExtra(AddEditCareer.EXTRA_CAREER_ID, -1);
            APerson person = personViewModel.getAPerson(personID);
            person.setCareerIDs(HelperUtility.addElement(person.getCareerIDs(), careerID));

            personViewModel.update(person);
            HelperUtility.setActivePerson(person.getPersonID());
            updateViews();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu_main, menu);
        menu.getItem(0).setTitle("Edit Person");
        menu.getItem(1).setTitle("Delete this person");
        menu.getItem(2).setVisible(false);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Intent intent;
        switch (item.getItemId()) {
            case R.id.menu_option_0:
                HelperUtility.setActivePerson(currentPerson.getPersonID());
                intent = new Intent(DetailedPerson.this, AddEditPerson.class);
                intent.putExtra(AddEditPerson.EXTRA_PERSON_ID, currentPerson.getPersonID());
                intent.putExtra(AddEditPerson.EXTRA_FIRST_NAME, currentPerson.getFirstName());
                intent.putExtra(AddEditPerson.EXTRA_LAST_NAME, currentPerson.getLastName());
                intent.putExtra(AddEditPerson.EXTRA_PHONE_NUMBER, currentPerson.getPhoneNumber());
                intent.putExtra(AddEditPerson.EXTRA_EMAIL_ADDRESS, currentPerson.getEmailAddress());
                intent.putExtra(AddEditPerson.EXTRA_CATEGORY_ID, currentPerson.getCategoryID());
                intent.putExtra(AddEditPerson.EXTRA_BIRTHDAY, currentPerson.getBirthDateID());
                intent.putExtra(AddEditPerson.EXTRA_IMPORTANT_DATES, currentPerson.getImportantDates());
                intent.putExtra(AddEditPerson.EXTRA_NOTES, currentPerson.getPersonalNotes());
                intent.putExtra(AddEditPerson.EXTRA_PAST_TIMES, currentPerson.getPastTimes());
                intent.putExtra(AddEditPerson.EXTRA_CAREER_ID, currentPerson.getCareerIDs());

                DetailedPerson.this.startActivityForResult(intent, HelperUtility.EDIT_PERSON_REQUEST, savedState);
                return true;
            case R.id.menu_option_1:
                personViewModel.delete(currentPerson);
                finish();
            case R.id.menu_option_2:
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
