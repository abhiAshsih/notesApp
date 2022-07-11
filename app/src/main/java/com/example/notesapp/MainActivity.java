package com.example.notesapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;

import com.example.notesapp.Activity.InsertNotesActivity;
import com.example.notesapp.Adapter.NotesAdapter;
import com.example.notesapp.Model.Notes;
import com.example.notesapp.VIewModel.NotesViewModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

public class MainActivity extends AppCompatActivity {

    FloatingActionButton newNotesBtn;
    NotesViewModel notesViewModel;
    RecyclerView notesRecycler;
    NotesAdapter adapter;
    List<Notes>listOfNotes;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        notesRecycler=findViewById(R.id.notesRecycler);
        notesViewModel= ViewModelProviders.of(this).get(NotesViewModel.class);

        newNotesBtn=findViewById(R.id.newNotesBtn);
        newNotesBtn.setOnClickListener(v->{
            startActivity(new Intent(MainActivity.this, InsertNotesActivity.class));
        });
        //listOfNotes=notesViewModel.getAllNotes;
        notesViewModel.getAllNotes.observe(this,notes -> {
            listOfNotes=notes;
            notesRecycler.setLayoutManager(new GridLayoutManager(this,2));
            adapter=new NotesAdapter(MainActivity.this,notes);
            notesRecycler.setAdapter(adapter);
        });
        ItemTouchHelper itemTouchHelper=new ItemTouchHelper(simpleCallback);
        itemTouchHelper.attachToRecyclerView(notesRecycler);
    }

    ItemTouchHelper.SimpleCallback simpleCallback=new ItemTouchHelper.SimpleCallback(0,ItemTouchHelper.LEFT|ItemTouchHelper.RIGHT) {
        @Override
        public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
            return false;
        }

        @Override
        public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
            int position=viewHolder.getAdapterPosition();

            //List<Notes>listOfNotes=notesViewModel.getAllNotes;
            Notes note= listOfNotes.get(position);
            switch (direction){
                case ItemTouchHelper.LEFT:
                    notesViewModel.deleteNote(note.id);
                    break;
                case ItemTouchHelper.RIGHT:
                    break;
            }
        }
    };
}