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

public class DetailedNoteView extends AppCompatActivity {
    private Bundle savedState;
    private NoteViewModel noteViewModel;
    private PersonViewModel personViewModel;
    private TextView noteTitle;
    private TextView notePriority;
    private TextView noteDetails;
    private Note note;
    private int personID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.detailed_note);

        personID = HelperUtility.getActivePerson();
        savedState = savedInstanceState;
        personViewModel = ViewModelProviders.of(this).get(PersonViewModel.class);
        noteViewModel = ViewModelProviders.of(this).get(NoteViewModel.class);

        noteTitle = findViewById(R.id.text_view_note_title);
        notePriority = findViewById(R.id.text_view_note_priority);
        noteDetails = findViewById(R.id.text_view_note_details);
        updateViews();

    }

    public void updateViews() {
        note = noteViewModel.getNote(HelperUtility.getActiveNote());
        noteTitle.setText(note.getNoteTitle());
        notePriority.setText(String.valueOf(note.getNoteImportance()));
        noteDetails.setText(note.getNoteDetails());
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
        menu.getItem(0).setTitle("Edit note");
        menu.getItem(1).setTitle("Delete this note");
        menu.getItem(2).setVisible(false);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Intent intent;
        switch (item.getItemId()) {
            case R.id.menu_option_0:
                intent = new Intent(this, AddEditNote.class);
                intent.putExtra(AddEditNote.EXTRA_NOTE_ID, note.getNoteID());
                intent.putExtra(AddEditNote.EXTRA_NOTE_TITLE, note.getNoteTitle());
                intent.putExtra(AddEditNote.EXTRA_NOTE_DETAILS, note.getNoteDetails());
                intent.putExtra(AddEditNote.EXTRA_NOTE_IMPORTANCE, note.getNoteImportance());
                HelperUtility.setActiveNote(note.getNoteID());
                startActivityForResult(intent, HelperUtility.EDIT_NOTE_REQUEST, savedState);
                return true;
            case R.id.menu_option_1:
                APerson person = personViewModel.getAPerson(HelperUtility.getActivePerson());
                person.setPersonalNotes(HelperUtility.removeElement(person.getPersonalNotes(), HelperUtility.getActiveNote()));
                Note note = noteViewModel.getNote(HelperUtility.getActiveNote());
                personViewModel.update(person);
                noteViewModel.delete(note);
                finish();
            case R.id.menu_option_2:
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}