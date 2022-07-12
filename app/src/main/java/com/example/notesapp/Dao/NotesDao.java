package com.example.notesapp.Dao;

import androidx.lifecycle.LiveData;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.example.notesapp.Model.Notes;

import java.util.List;

@androidx.room.Dao
public interface NotesDao {
    @Query("SELECT * FROM Notes_Database")
    LiveData<List<Notes>> getAllNotes();   ///Doubt??

    @Insert
    void insertNotes(Notes... notes);

    @Query("DELETE FROM Notes_Database WHere id=:id")
    void deleteNodes(int id);

    @Update
    void updateNodes(Notes notes);

    @Query("SELECT notes_image FROM NOTES_DATABASE")
    LiveData<List<String>> getNotesImage();

}
