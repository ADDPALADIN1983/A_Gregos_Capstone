package com.example.a_gregos_capstone;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.NumberPicker;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;

public class AddEditNote extends AppCompatActivity {
    public static final String EXTRA_NOTE_ID = "com.example.a_gregos_capstone.EXTRA_NOTE_ID";
    public static final String EXTRA_NOTE_TITLE = "com.example.a_gregos_capstone.EXTRA_NOTE_TITLE";
    public static final String EXTRA_NOTE_DETAILS = "com.example.a_gregos_capstone.EXTRA_NOTE_DETAILS";
    public static final String EXTRA_NOTE_IMPORTANCE = "com.example.a_gregos_capstone.EXTRA_NOTE_IMPORTANCE";

    private Bundle savedState;
    private NoteViewModel noteViewModel;
    private NumberPicker numberPicker;
    private EditText editTextNoteTitle;
    private EditText editTextNoteDetails;
    private int personID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar();
        setContentView(R.layout.add_edit_notes);
        Intent intent = getIntent();

        personID = HelperUtility.getActivePerson();
        numberPicker = findViewById(R.id.note_number_picker_priority);
        editTextNoteTitle = findViewById(R.id.note_edit_text_title);
        editTextNoteDetails = findViewById(R.id.note_edit_text_details);
        noteViewModel = ViewModelProviders.of(this).get(NoteViewModel.class);
        numberPicker.setMinValue(1);
        numberPicker.setMaxValue(10);

        if (intent.hasExtra(EXTRA_NOTE_ID)) {
            setTitle("Edit Note");
            editTextNoteTitle.setText(intent.getStringExtra(EXTRA_NOTE_TITLE));
            numberPicker.setValue(intent.getIntExtra(EXTRA_NOTE_IMPORTANCE, 1));
            editTextNoteDetails.setText(intent.getStringExtra(EXTRA_NOTE_DETAILS));
        } else {
            setTitle("Add Note");
        }

    }

    private void saveNote() {
        String title = editTextNoteTitle.getText().toString();
        String details = editTextNoteDetails.getText().toString();
        int importance = numberPicker.getValue();
        int id = getIntent().getIntExtra(EXTRA_NOTE_ID, -1);
        if (title.trim().isEmpty() || details.trim().isEmpty()) {
            Toast.makeText(this, "Please enter a title and description.", Toast.LENGTH_SHORT).show();
            return;
        }
        Note note = new Note(personID, title, details, importance);
        Intent data = new Intent();

        if (id != -1) {
            note.setNoteID(id);
            noteViewModel.update(note);
        } else {
            noteViewModel.insert(note);
            id = noteViewModel.getNoteID(personID, title, details, importance);
        }
        data.putExtra(EXTRA_NOTE_ID, id);
        note.setNoteID(id);
        HelperUtility.setActiveNote(id);
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
                saveNote();
                return true;
            case R.id.menu_cancel:
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}