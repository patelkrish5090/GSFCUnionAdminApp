package com.example.gsfcadminapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashSet;
import java.util.Locale;
import java.util.Set;

public class AddingAnnouncementActivity extends AppCompatActivity {

    private EditText editTextTitle, editTextDetails, editTextLink;
    private Button buttonAdd, buttonCancel;
    private DatabaseReference announcementsRef;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adding_announcement);

        editTextTitle = findViewById(R.id.editTextTitle);
        editTextDetails = findViewById(R.id.editTextDetails);
        editTextLink = findViewById(R.id.editTextAnnouncementLink);
        buttonAdd = findViewById(R.id.buttonAddAnnouncement);
        buttonCancel = findViewById(R.id.buttonCancelAnnouncement);

        // Initialize Firebase Database reference
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        announcementsRef = database.getReference("announcements");

        buttonAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addAnnouncement();
            }
        });

        buttonCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish(); // Close the activity
            }
        });
    }

    private void addAnnouncement() {
        String title = editTextTitle.getText().toString().trim();
        String details = editTextDetails.getText().toString().trim();
        String link = editTextLink.getText().toString().trim();

        if (!title.isEmpty() && !details.isEmpty()) {
            if (link.isEmpty()) {
                link = "none"; // Set link to "none" if it's empty
            }
            final String finalLink = link;
            // Get current date
            String currentDate = getCurrentDate();

            // Check if an announcement for the current date already exists
            announcementsRef.child(currentDate).addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    Set<String> existingIds = new HashSet<>();
                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                        existingIds.add(snapshot.getKey());
                    }

                    String uniqueId;
                    do {
                        int randomTwoDigitNumber = 10 + (int) (Math.random() * 90); // Generates a number between 10 and 99
                        uniqueId = currentDate + String.format("%02d", randomTwoDigitNumber);
                    } while (existingIds.contains(uniqueId));

                    String displayDate = convertDateToDisplayFormat(currentDate);

                    addAnnouncementToDatabase(uniqueId, displayDate, title, details, finalLink);
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                    Toast.makeText(AddingAnnouncementActivity.this, "Error: " + databaseError.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        } else {
            Toast.makeText(this, "Please fill all fields", Toast.LENGTH_SHORT).show();
        }
    }

    private void addAnnouncementToDatabase(String id, String date, String title, String details, String link) {

        announcementsRef.child(id).child("id").setValue(id);
        announcementsRef.child(id).child("date").setValue(date);
        announcementsRef.child(id).child("title").setValue(title);
        announcementsRef.child(id).child("link").setValue(link);
        announcementsRef.child(id).child("details").setValue(details)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        Toast.makeText(AddingAnnouncementActivity.this, "Announcement added successfully", Toast.LENGTH_SHORT).show();
                        finish(); // Close the activity
                    } else {
                        Toast.makeText(AddingAnnouncementActivity.this, "Failed to add announcement", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    private String getCurrentDate() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("ddMMyyyy", Locale.getDefault());
        return dateFormat.format(new Date());
    }

    private String convertDateToDisplayFormat(String date) {
        SimpleDateFormat originalFormat = new SimpleDateFormat("ddMMyyyy", Locale.getDefault());
        SimpleDateFormat displayFormat = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
        try {
            Date parsedDate = originalFormat.parse(date);
            return displayFormat.format(parsedDate);
        } catch (ParseException e) {
            e.printStackTrace();
            return date; // Fallback to original date if parsing fails
        }
    }
}
