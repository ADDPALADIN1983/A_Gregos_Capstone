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

public class DetailedPastTimeView extends AppCompatActivity {
    private Bundle savedState;
    private PastTimesViewModel pastTimesViewModel;
    private PersonViewModel personViewModel;
    private PastTimes pastTimes;
    private TextView pastTimeName;
    private TextView pastTimeDetails;
    private int personID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.detailed_past_times);

        personID = HelperUtility.getActivePerson();
        savedState = savedInstanceState;
        personViewModel = ViewModelProviders.of(this).get(PersonViewModel.class);
        pastTimesViewModel = ViewModelProviders.of(this).get(PastTimesViewModel.class);

        pastTimeName = findViewById(R.id.text_view_past_time_name);
        pastTimeDetails = findViewById(R.id.text_view_past_time_details);
        updateViews();

    }

    public void updateViews() {
        pastTimes = pastTimesViewModel.getPastTimes(HelperUtility.getActivePastTime());
        pastTimeName.setText(pastTimes.getPastTimeName());
        pastTimeDetails.setText(pastTimes.getPastTimeDetails());
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
        menu.getItem(0).setTitle("Edit past time");
        menu.getItem(1).setTitle("Delete this past time");
        menu.getItem(2).setVisible(false);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Intent intent;
        switch (item.getItemId()) {
            case R.id.menu_option_0:
                intent = new Intent(this, AddEditPastTimes.class);
                HelperUtility.setActivePastTime(pastTimes.getPastTimeID());
                intent.putExtra(AddEditPastTimes.EXTRA_PAST_TIMES_ID, pastTimes.getPastTimeID());
                intent.putExtra(AddEditPastTimes.EXTRA_PAST_TIMES_NAME, pastTimes.getPastTimeName());
                intent.putExtra(AddEditPastTimes.EXTRA_PAST_TIMES_DETAILS, pastTimes.getPastTimeDetails());
                startActivityForResult(intent, HelperUtility.EDIT_PAST_TIME_REQUEST, savedState);
                return true;
            case R.id.menu_option_1:
                APerson person = personViewModel.getAPerson(HelperUtility.getActivePerson());
                person.setPastTimes(HelperUtility.removeElement(person.getPastTimes(), HelperUtility.getActivePastTime()));
                PastTimes pastTimes = pastTimesViewModel.getPastTimes(HelperUtility.getActivePastTime());
                personViewModel.update(person);
                pastTimesViewModel.delete(pastTimes);
                finish();
            case R.id.menu_option_2:
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}