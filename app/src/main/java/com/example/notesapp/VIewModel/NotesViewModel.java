package com.example.notesapp.VIewModel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.notesapp.Model.Notes;
import com.example.notesapp.Repository.NotesRepository;

import java.util.List;

public class NotesViewModel extends AndroidViewModel {

    public NotesRepository repository;
    public LiveData<List<Notes>> getAllNotes;
    public LiveData<List<String>>getAllImages;


    public NotesViewModel(Application application) {
        super(application);

        repository = new NotesRepository(application);
        getAllNotes = repository.getAllNotes;
        getAllImages = repository.getAllImages;
    }

    public void insertNote(Notes notes) {
        repository.insertNotes(notes);
    }

    public void deleteNote(int id){
        repository.deleteNodes(id);

    }
    public void updateNote(Notes notes){
        repository.updateNotes(notes);
    }
}
