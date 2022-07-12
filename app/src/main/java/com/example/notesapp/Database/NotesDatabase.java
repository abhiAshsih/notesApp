package com.example.notesapp.Database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

import com.example.notesapp.Dao.NotesDao;
import com.example.notesapp.Model.GenreConverter;
import com.example.notesapp.Model.Notes;

@Database(entities = {Notes.class},version=1)
@TypeConverters(GenreConverter.class)
public abstract class NotesDatabase extends RoomDatabase {

    public abstract NotesDao notesDao();  ///Doubt?
    public static NotesDatabase INSTANCE;

    public static NotesDatabase getDatabaseInstance(Context context){
        if(INSTANCE==null){
            INSTANCE= Room.databaseBuilder(context.getApplicationContext(),
                    NotesDatabase.class,"Notes_Database").allowMainThreadQueries().build();
        }
        return INSTANCE;
    }

}
