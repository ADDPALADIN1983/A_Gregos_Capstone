package com.example.a_gregos_capstone;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;

public class DetailedDateView extends AppCompatActivity {
    private Bundle savedState;
    private PersonDateViewModel dateViewModel;
    private PersonViewModel personViewModel;
    private TextView dateType;
    private TextView dateItself;
    private TextView dateDetails;
    private PersonDate date;
    private int personID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.detailed_date);

        personID = HelperUtility.getActivePerson();
        savedState = savedInstanceState;
        personViewModel = ViewModelProviders.of(this).get(PersonViewModel.class);
        dateViewModel = ViewModelProviders.of(this).get(PersonDateViewModel.class);

        dateType = findViewById(R.id.text_view_date_type);
        dateItself = findViewById(R.id.text_view_date);
        dateDetails = findViewById(R.id.text_view_date_description);
        updateViews();

    }

    public void updateViews() {
        date = dateViewModel.getDate(HelperUtility.getActiveDate());
        dateType.setText(date.getDateType());
        dateItself.setText(date.toString());
        dateDetails.setText(date.getDateDescription());
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        updateViews();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu_main, menu);
        menu.getItem(0).setTitle("Edit date");
        menu.getItem(1).setTitle("Delete this date");
        menu.getItem(2).setVisible(false);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Intent intent;
        switch (item.getItemId()) {
            case R.id.menu_option_0:
                intent = new Intent(this, AddEditDate.class);
                intent.putExtra(AddEditDate.EXTRA_DATE_ID, date.getDateID());
                intent.putExtra(AddEditDate.EXTRA_DATE_TYPE, date.getDateType());
                intent.putExtra(AddEditDate.EXTRA_DATE_YEAR, String.valueOf(date.getYear()));
                intent.putExtra(AddEditDate.EXTRA_DATE_MONTH, String.valueOf(date.getMonth()));
                intent.putExtra(AddEditDate.EXTRA_DATE_DAY, String.valueOf(date.getDay()));
                intent.putExtra(AddEditDate.EXTRA_DATE_DESCRIPTION, date.getDateDescription());
                startActivityForResult(intent, HelperUtility.EDIT_DATE_REQUEST, savedState);
                return true;
            case R.id.menu_option_1:
                APerson person = personViewModel.getAPerson(HelperUtility.getActivePerson());
                person.setImportantDates(HelperUtility.removeElement(person.getImportantDates(), HelperUtility.getActiveDate()));
                date = dateViewModel.getDate(HelperUtility.getActiveDate());
                if (date.getDateType().equalsIgnoreCase("Birthday")) {
                    person.setBirthDateID(-1);
                }
                personViewModel.update(person);
                dateViewModel.delete(date);
                finish();
            case R.id.menu_option_2:
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}