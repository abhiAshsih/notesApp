package com.example.notesapp.Model;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverter;
import java.util.ArrayList;
import java.util.List;

@Entity(tableName="Notes_Database")
public class Notes {

    @PrimaryKey(autoGenerate = true)
    public int id;

    @ColumnInfo(name = "notes_title")
    public String notesTitle;

    @ColumnInfo(name = "notes_subtitle")
    public String notesSubtitle;

    @ColumnInfo(name = "notes")
    public String notes;

    @ColumnInfo(name = "notes_date")
    public String notesDate;

    @ColumnInfo(name = "notes_image")
    public List<String> notesImage;

}

