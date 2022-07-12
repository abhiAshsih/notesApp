package com.example.notesapp.Repository;

import android.app.Application;

import androidx.lifecycle.LiveData;

import com.example.notesapp.Dao.NotesDao;
import com.example.notesapp.Database.NotesDatabase;
import com.example.notesapp.Model.Notes;

import java.util.List;

public class NotesRepository {
    public NotesDao notesDao;
    public LiveData<List<Notes>>getAllNotes;
    public LiveData<List<String>>getAllImages;

    public NotesRepository(Application application){
        NotesDatabase database=NotesDatabase.getDatabaseInstance(application);
        notesDao=database.notesDao();
        getAllNotes=notesDao.getAllNotes();
        getAllImages=notesDao.getNotesImage();
    }

    public void insertNotes(Notes notes){
        notesDao.insertNotes(notes);
    }

    public void deleteNodes(int id){
        notesDao.deleteNodes(id);
    }

    public void updateNotes(Notes notes){
        notesDao.updateNodes(notes);
    }
}
