package com.example.notesapp.Activity;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.notesapp.Model.Notes;
import com.example.notesapp.R;
import com.example.notesapp.VIewModel.NotesViewModel;
import com.example.notesapp.databinding.ActivityInsertNotesBinding;
import com.github.dhaval2404.imagepicker.ImagePicker;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class InsertNotesActivity extends AppCompatActivity {
    Boolean undoState=false;
    int index=0,max=0;
    List<String> originalNotes=new ArrayList<String>();
    List<String> imageNotes =new ArrayList<>();
    ActivityInsertNotesBinding binding;
    String title,subtitle,notes;
    NotesViewModel notesViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding=ActivityInsertNotesBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        notesViewModel= ViewModelProviders.of(this).get(NotesViewModel.class);

        binding.doneNotesBtn.setOnClickListener(v->{
            title=binding.notesTitle.getText().toString();
            subtitle=binding.notesSubtitle.getText().toString();
            notes=binding.notesData.getText().toString();

            CreateNotes(title,subtitle,notes);

        });

        binding.notesData.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View view, int i, KeyEvent keyEvent) {
                if(i==keyEvent.KEYCODE_DEL){
                    if(undoState==false){
                        max=originalNotes.size();
                        originalNotes.add(binding.notesData.getText().toString().trim());
                        index++;
                        undoState=true;
                    }
                    else{
                        max=originalNotes.size();
                        originalNotes.add(binding.notesData.getText().toString().trim());
                        index++;
                    }
                }
                return false;
            }
        });

        binding.imageUploadBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ImagePicker.with(InsertNotesActivity.this)
                        .crop()	    			//Crop image(Optional), Check Customization for more option
                        .compress(1024)			//Final image size will be less than 1 MB(Optional)
                        /*.maxResultSize(1080, 720)	//Final image resolution will be less than 1080 x 1080(Optional)*/
                        .start();
            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode,Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        String imageUriString=data.getData().toString();
        Log.d("NotesAPP",imageUriString);
        imageNotes.add(imageUriString);
    }

    public void undoOperation(View view){
        index--;
        index=index<=0 ? 0: index;
        binding.notesData.setText(originalNotes.get(index));
    }
    public void redoOperation(View view){
        index++;
        max=originalNotes.size()-1;
        index=index>=max?max:index;
        binding.notesData.setText(originalNotes.get(index));
    }
    private void CreateNotes(String title, String subtitle, String notes) {
        SimpleDateFormat formatter = new SimpleDateFormat("HH:MM, MMMM d");
        Date date = new Date();
        String currTimeAndDate=formatter.format(date).toString();
        Notes notes1=new Notes();
        notes1.notesTitle=title;
        notes1.notesSubtitle=subtitle;
        notes1.notes=notes;
        notes1.notesDate=currTimeAndDate;
        notes1.notesImage=imageNotes;
        notesViewModel.insertNote(notes1);

        Toast.makeText(this,"Note Created Successfully",Toast.LENGTH_SHORT).show();
        finish();
    }
}