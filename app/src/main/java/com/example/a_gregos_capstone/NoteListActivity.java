package com.example.a_gregos_capstone;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class NoteListActivity extends AppCompatActivity {
    Bundle savedState;
    private PersonViewModel personViewModel;
    private NoteViewModel noteViewModel;
    private FloatingActionButton addButton;
    private final NoteAdapter noteAdapter = new NoteAdapter();
    private RecyclerView noteRecyclerView;
    private int personID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        savedState = savedInstanceState;
        setContentView(R.layout.generic_recycler_view);

        personID = HelperUtility.getActivePerson();
        personViewModel = ViewModelProviders.of(this).get(PersonViewModel.class);
        noteViewModel = ViewModelProviders.of(this).get(NoteViewModel.class);
        addButton = findViewById(R.id.add_button);
        addButton.setOnClickListener(v -> {
            Intent intent = new Intent(NoteListActivity.this, AddEditNote.class);
            startActivityForResult(intent, HelperUtility.ADD_NOTE_REQUEST);
        });

        noteRecyclerView = findViewById(R.id.generic_recycler);
        noteRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        noteRecyclerView.setHasFixedSize(true);
        noteRecyclerView.setAdapter(noteAdapter);
        updateViews();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == HelperUtility.ADD_NOTE_REQUEST && resultCode == RESULT_OK) {

            int noteID = data.getIntExtra(AddEditNote.EXTRA_NOTE_ID, -1);
            APerson person = personViewModel.getAPerson(personID);
            person.setPersonalNotes(HelperUtility.addElement(person.getPersonalNotes(), noteID));

            personViewModel.update(person);
            HelperUtility.setActivePerson(person.getPersonID());
        }
        updateViews();
    }

    private void updateViews() {
        noteViewModel.getAllNotes(HelperUtility.getActivePerson()).observe(this, note -> noteAdapter.submitList(note));
        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                Note selectedNote = noteAdapter.getNoteAt(viewHolder.getAdapterPosition());
                APerson person = personViewModel.getAPerson(selectedNote.getPersonID());
                person.setPersonalNotes(HelperUtility.removeElement(person.getPersonalNotes(), selectedNote.getNoteID()));
                personViewModel.update(person);
                noteViewModel.delete(selectedNote);
                updateViews();
                Toast.makeText(NoteListActivity.this, "Note deleted.", Toast.LENGTH_SHORT).show();
            }
        }).attachToRecyclerView(noteRecyclerView);

        noteAdapter.setOnItemClickListener(note -> {
            HelperUtility.setActiveNote(note.getNoteID());

            Intent intent = new Intent(NoteListActivity.this, DetailedNoteView.class);
            startActivity(intent, savedState);
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu_main, menu);
        menu.getItem(0).setVisible(false);
        menu.getItem(1).setVisible(false);
        menu.getItem(2).setVisible(false);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Intent intent;
        switch (item.getItemId()) {
            case R.id.menu_option_0:
                return true;
            case R.id.menu_option_1:
                return true;
            case R.id.menu_option_2:
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
