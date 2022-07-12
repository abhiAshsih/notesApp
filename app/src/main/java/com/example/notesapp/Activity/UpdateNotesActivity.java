package com.example.notesapp.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.notesapp.Adapter.ImageNotesAdapter;
import com.example.notesapp.Adapter.NotesAdapter;
import com.example.notesapp.MainActivity;
import com.example.notesapp.Model.Notes;
import com.example.notesapp.R;
import com.example.notesapp.VIewModel.NotesViewModel;
import com.example.notesapp.databinding.ActivityInsertNotesBinding;
import com.example.notesapp.databinding.ActivityUpdateNotesBinding;
import com.google.android.material.bottomsheet.BottomSheetDialog;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class UpdateNotesActivity extends AppCompatActivity {
    RecyclerView imageRecycler;
    ImageNotesAdapter adapter;
    NotesViewModel notesViewModel;
    String[] imageArr;
    Boolean undoState=false;
    int index=0,max=0;
    List<String> originalNotes=new ArrayList<String>();
    ActivityUpdateNotesBinding binding;
    String stitle,ssubTitle,snotes;
    int iid;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding= ActivityUpdateNotesBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        stitle= getIntent().getStringExtra("title");
        ssubTitle= getIntent().getStringExtra("subTitle");
        iid= getIntent().getIntExtra("id",0);
        snotes=getIntent().getStringExtra("notes");
        imageArr=getIntent().getStringArrayExtra("image");
        binding.upTitle.setText(stitle);
        binding.upSubtitle.setText(ssubTitle);
        binding.upNotes.setText(snotes);

        notesViewModel= ViewModelProviders.of(this).get(NotesViewModel.class);

        binding.updateNotesBtn.setOnClickListener(v->{
            String title=binding.upTitle.getText().toString();
            String subtitle=binding.upSubtitle.getText().toString();
            String notes=binding.upNotes.getText().toString();
            List<String> imageNotesList=new ArrayList<>();
            for(String img:imageArr){
                imageNotesList.add(img);
            }
            UpdateNotes(title,subtitle,notes,imageNotesList);
        });

        binding.shareNotesButton.setOnClickListener(v->{
            String title=binding.upTitle.getText().toString();
            String subtitle=binding.upSubtitle.getText().toString();
            String notes=binding.upNotes.getText().toString();
            String finalString =title + "\n" + subtitle + "\n" + notes;
            ShareNotes(finalString);
        });

        binding.upNotes.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View view, int i, KeyEvent keyEvent) {
                if(i==keyEvent.KEYCODE_DEL){
                    if(undoState==false){
                        max=originalNotes.size();
                        originalNotes.add(binding.upNotes.getText().toString().trim());
                        index++;
                        undoState=true;
                    }
                    else{
                        originalNotes.add(binding.upNotes.getText().toString().trim());
                        index++;
                    }
                }
                return false;
            }
        });

        imageRecycler=findViewById(R.id.imageRecyclerView);
        notesViewModel.getAllNotes.observe(this,notes -> {
            //imageList=notes.notes_image;
            List<String> imageNotesList=new ArrayList<>();
            for(String img:imageArr){
                imageNotesList.add(img);
            }
            imageRecycler.setLayoutManager(new GridLayoutManager(this,3));
            adapter=new ImageNotesAdapter(UpdateNotesActivity.this,imageNotesList);
            imageRecycler.setAdapter(adapter);
        });
    }

    public void undoOperation(View view){
        index--;
        index=index<=0 ? 0: index;
        binding.upNotes.setText(originalNotes.get(index));
    }
    public void redoOperation(View view){
        index++;
        max=originalNotes.size()-1;
        index=index>=max?max:index;
        binding.upNotes.setText(originalNotes.get(index));
    }

    private void ShareNotes(String text){
        Intent sendIntent = new Intent();
        sendIntent.setAction(Intent.ACTION_SEND);
        sendIntent.putExtra(Intent.EXTRA_TEXT, text);
        sendIntent.setType("text/plain");

        Intent shareIntent = Intent.createChooser(sendIntent, null);
        startActivity(shareIntent);
        finish();
    }
    private void UpdateNotes(String title, String subtitle, String notes,List<String>imageList) {
        SimpleDateFormat formatter = new SimpleDateFormat("HH:MM, MMMM d");
        Date date = new Date();
        String currTimeAndDate=formatter.format(date).toString();

        Notes updateNotes=new Notes();
        updateNotes.id=iid;
        updateNotes.notesTitle=title;
        updateNotes.notesSubtitle=subtitle;
        updateNotes.notes=notes;
        updateNotes.notesDate=currTimeAndDate;
        updateNotes.notesImage=imageList;
        notesViewModel.updateNote(updateNotes);


        Toast.makeText(this,"Note Updated Successfully",Toast.LENGTH_SHORT).show();
        finish();
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.delete_menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        if(item.getItemId()==R.id.ic_delete){
            BottomSheetDialog sheetDialog=new BottomSheetDialog(UpdateNotesActivity.this,R.style.BottomSheetStyle);

            View view= LayoutInflater.from(UpdateNotesActivity.this).
                    inflate(R.layout.delete_bottom_sheet,(LinearLayout)findViewById(R.id.bottomSheet));

            sheetDialog.setContentView(view);

            TextView yes,no;
            yes=view.findViewById(R.id.yes_delete);
            no=view.findViewById(R.id.no_delete);

            yes.setOnClickListener(v->{
                notesViewModel.deleteNote(iid);
                finish();
            });

            no.setOnClickListener(v->{
                sheetDialog.dismiss();
            });

            sheetDialog.show();

        }

        return true;
    }
}