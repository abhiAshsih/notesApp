package com.example.notesapp.Adapter;

import android.content.Intent;
import android.provider.ContactsContract;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.notesapp.Activity.UpdateNotesActivity;
import com.example.notesapp.MainActivity;
import com.example.notesapp.Model.Notes;
import com.example.notesapp.R;

import java.util.List;

public class NotesAdapter extends RecyclerView.Adapter<NotesAdapter.notesViewHolder> {
    MainActivity mainActivity;
    List<Notes> notes;

    public NotesAdapter(MainActivity mainActivity, List<Notes> notes) {
        this.mainActivity=mainActivity;
        this.notes=notes;
    }

    @Override
    public notesViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new notesViewHolder(LayoutInflater.from(mainActivity).inflate(R.layout.item_notes,parent,false));
    }

    @Override
    public void onBindViewHolder(notesViewHolder holder, int position) {
        Notes note=notes.get(position);
        holder.title.setText(note.notesTitle);
        holder.subTitle.setText(note.notesSubtitle);
        holder.notesDate.setText(note.notesDate);
        String[] imageArr = new String[note.notesImage.size()];
        int count=0;
        for(String img:note.notesImage){
            imageArr[count]=img;
            count++;
        }
        holder.itemView.setOnClickListener(V->{
            Intent intent=new Intent(mainActivity, UpdateNotesActivity.class);
            intent.putExtra("id",note.id);
            intent.putExtra("title",note.notesTitle);
            intent.putExtra("subTitle",note.notesSubtitle);
            intent.putExtra("notes",note.notes);
            intent.putExtra("image",imageArr);
            mainActivity.startActivity(intent);

        });
    }

    @Override
    public int getItemCount() {
        return notes.size();
    }

    class notesViewHolder extends RecyclerView.ViewHolder{
        TextView title,subTitle,notesDate;
        public notesViewHolder(View itemView) {
            super(itemView);

            title=itemView.findViewById(R.id.notesTitle);
            subTitle=itemView.findViewById(R.id.notesSubtitle);
            notesDate=itemView.findViewById(R.id.notesDate);
        }
    }
}
