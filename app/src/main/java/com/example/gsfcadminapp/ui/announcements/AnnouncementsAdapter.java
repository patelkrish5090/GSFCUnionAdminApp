package com.example.gsfcadminapp.ui.announcements;

import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.gsfcadminapp.R;
import com.google.firebase.database.DatabaseReference;

import java.util.List;

public class AnnouncementsAdapter extends RecyclerView.Adapter<AnnouncementsAdapter.AnnouncementViewHolder> {

    private List<Announcement> announcements;
    private DatabaseReference myRef;

    public AnnouncementsAdapter(List<Announcement> announcements, DatabaseReference myRef) {
        this.announcements = announcements;
        this.myRef = myRef;
    }

    @NonNull
    @Override
    public AnnouncementViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.announcements_item, parent, false);
        return new AnnouncementViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AnnouncementViewHolder holder, int position) {
        Announcement announcement = announcements.get(position);
        holder.date.setText(announcement.getDate());
        holder.title.setText(announcement.getTitle());
        holder.details.setText(announcement.getDetails());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Toggle visibility of details and delete button
                if (holder.details.getVisibility() == View.VISIBLE) {
                    holder.details.setVisibility(View.GONE);
                    holder.deleteButton.setVisibility(View.GONE);
                    holder.linkButton.setVisibility(View.GONE);
                } else {
                    holder.details.setVisibility(View.VISIBLE);
                    holder.deleteButton.setVisibility(View.VISIBLE);
                    holder.linkButton.setVisibility(View.VISIBLE);
                }
            }
        });

        holder.deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int currentPosition = holder.getAdapterPosition();
                if (currentPosition != RecyclerView.NO_POSITION) {
                    Announcement currentAnnouncement = announcements.get(currentPosition);
                    myRef.child(currentAnnouncement.getId()).removeValue().addOnCompleteListener(task -> {
                        if (task.isSuccessful()) {
                            announcements.remove(currentPosition);
                            notifyItemRemoved(currentPosition);
                            notifyItemRangeChanged(currentPosition, announcements.size());
                            Toast.makeText(holder.itemView.getContext(), "Announcement deleted", Toast.LENGTH_SHORT).show();
                        } else {
                            Log.e("Firebase", "Failed to delete announcement", task.getException());
                            Toast.makeText(holder.itemView.getContext(), "Failed to delete announcement", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }
        });

        String url = announcement.getLink();
        if (url != null && !url.isEmpty() && !url.equals("none")) {
            holder.linkButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
                    holder.itemView.getContext().startActivity(intent);
                }
            });
        } else {
            holder.linkButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Toast.makeText(holder.itemView.getContext(), "No file attached to this", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return announcements.size();
    }

    static class AnnouncementViewHolder extends RecyclerView.ViewHolder {
        TextView title;
        TextView details;
        TextView date;
        Button deleteButton, linkButton;

        public AnnouncementViewHolder(@NonNull View itemView) {
            super(itemView);
            date = itemView.findViewById(R.id.announcement_date);
            title = itemView.findViewById(R.id.announcement_title);
            details = itemView.findViewById(R.id.announcement_details);
            deleteButton = itemView.findViewById(R.id.delete_button);
            linkButton = itemView.findViewById(R.id.link_button);
        }
    }
}
