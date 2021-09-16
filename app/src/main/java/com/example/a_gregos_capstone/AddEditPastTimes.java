package com.example.a_gregos_capstone;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;

public class AddEditPastTimes extends AppCompatActivity {
    public static final String EXTRA_PAST_TIMES_ID = "com.example.a_gregos_capstone.EXTRA_PAST_TIMES_ID";
    public static final String EXTRA_PAST_TIMES_NAME = "com.example.a_gregos_capstone.EXTRA_PAST_TIMES_NAME";
    public static final String EXTRA_PAST_TIMES_DETAILS = "com.example.a_gregos_capstone.EXTRA_PAST_TIMES_DETAILS";

    private Bundle savedState;
    private PastTimesViewModel pastTimesViewModel;
    private EditText editTextPastTimeName;
    private EditText editTextDetails;
    private int personID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        savedState = savedInstanceState;
        getSupportActionBar();
        setContentView(R.layout.add_edit_past_times);
        Intent intent = getIntent();

        personID = HelperUtility.getActivePerson();
        editTextPastTimeName = findViewById(R.id.edit_text_past_time_name);
        editTextDetails = findViewById(R.id.edit_text_past_time_details);
        pastTimesViewModel = ViewModelProviders.of(this).get(PastTimesViewModel.class);
        if (intent.hasExtra(EXTRA_PAST_TIMES_ID)) {
            setTitle("Edit past times");
            editTextPastTimeName.setText(intent.getStringExtra(EXTRA_PAST_TIMES_NAME));
            editTextDetails.setText(intent.getStringExtra(EXTRA_PAST_TIMES_DETAILS));
        } else {
            setTitle("Add past times");
        }
    }

    private void savePastTime() {
        String name = editTextPastTimeName.getText().toString();
        String details = editTextDetails.getText().toString();
        int id = getIntent().getIntExtra(EXTRA_PAST_TIMES_ID, -1);
        if (name.trim().isEmpty() || details.trim().isEmpty()) {
            Toast.makeText(this, "Please enter a name and details.", Toast.LENGTH_SHORT).show();
            return;
        }
        PastTimes pastTimes = new PastTimes(personID, name, details);
        Intent data = new Intent();

        if (id != -1) {
            pastTimes.setPastTimeID(id);
            pastTimesViewModel.update(pastTimes);
            data.putExtra(EXTRA_PAST_TIMES_ID, id);
        } else {
            pastTimesViewModel.insert(pastTimes);
            id = pastTimesViewModel.getPastTimeID(personID, name, details);
            data.putExtra(EXTRA_PAST_TIMES_ID, id);
        }
        HelperUtility.setActivePastTime(id);
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
                savePastTime();
                return true;
            case R.id.menu_cancel:
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}