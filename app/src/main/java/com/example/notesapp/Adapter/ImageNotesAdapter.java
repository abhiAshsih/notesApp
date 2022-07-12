package com.example.notesapp.Adapter;

import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.notesapp.Activity.UpdateNotesActivity;
import com.example.notesapp.MainActivity;
import com.example.notesapp.Model.Notes;
import com.example.notesapp.R;

import java.util.List;

public class ImageNotesAdapter extends RecyclerView.Adapter<ImageNotesAdapter.imageNotesViewHolder> {

    UpdateNotesActivity updateNotesActivity;
    List<String> imageNotes;

    public ImageNotesAdapter(UpdateNotesActivity updateNotesActivity, List<String> imageNotes) {
        this.updateNotesActivity=updateNotesActivity;
        this.imageNotes=imageNotes;
    }

    @Override
    public imageNotesViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new imageNotesViewHolder(LayoutInflater.from(updateNotesActivity).inflate(R.layout.item_image,parent,false));
    }

    @Override
    public void onBindViewHolder(imageNotesViewHolder holder, int position) {
        String imageStr=imageNotes.get(position);
        holder.imageNote.setImageURI(Uri.parse(imageStr));
    }

    @Override
    public int getItemCount() {
        return imageNotes.size();
    }

    public class imageNotesViewHolder extends RecyclerView.ViewHolder {
        ImageView imageNote;
        public imageNotesViewHolder(View itemView) {
            super(itemView);
            imageNote=itemView.findViewById(R.id.imageNotes);
        }
    }
}
