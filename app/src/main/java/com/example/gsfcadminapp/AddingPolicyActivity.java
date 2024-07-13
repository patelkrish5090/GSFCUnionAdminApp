package com.example.gsfcadminapp;

import android.content.Intent;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.lifecycle.ViewModelProvider;

import com.example.gsfcadminapp.ui.home.UserViewModel;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import com.example.gsfcadminapp.ui.policy.Policy;
import com.google.firebase.database.ValueEventListener;

import com.example.gsfcadminapp.ui.policy.PolicyViewModel;

import java.util.ArrayList;
import java.util.List;

public class    AddingPolicyActivity extends AppCompatActivity {

    private EditText editTextTitle, editTextDetails, editTextLink;
    private Button buttonAdd, buttonCancel;
    private DatabaseReference policyref;
    private PolicyViewModel policyViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adding_policy);

        editTextTitle = findViewById(R.id.editTextPolicyTitle);
        editTextDetails = findViewById(R.id.editTextPolicyDetails);
        editTextLink = findViewById(R.id.editTextFileLink);
        buttonAdd = findViewById(R.id.buttonAddPolicy);
        buttonCancel = findViewById(R.id.buttonCancelPolicy);

        policyViewModel = new ViewModelProvider(this).get(PolicyViewModel.class);

        FirebaseDatabase database = FirebaseDatabase.getInstance();
        policyref = database.getReference("policy");

        buttonAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addPolicy();
                onUserClicked();
            }
        });

        buttonCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onUserClicked();
            }
        });

    }

    private void addPolicy() {
        String title = editTextTitle.getText().toString().trim();
        String details = editTextDetails.getText().toString().trim();
        String link = editTextLink.getText().toString().trim();

        if (title.isEmpty() || details.isEmpty()) {
            Toast.makeText(AddingPolicyActivity.this, "Please fill all fields", Toast.LENGTH_SHORT).show();
            return;
        }

        if (link.isEmpty()) {
            link = "none"; // Set link to "none" if it's empty
        }

        String uid = convertTitleToIdentifier(title);

        Policy newPolicy = new Policy(uid, link, title, details);

        policyref.child(uid).setValue(newPolicy).addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                Toast.makeText(AddingPolicyActivity.this, "Policy added successfully", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(AddingPolicyActivity.this, "Failed to add policy", Toast.LENGTH_SHORT).show();
            }
        });
    }


    private static String convertTitleToIdentifier(String title) {
        if (title == null || title.isEmpty()) {
            return title;
        }
        // Convert to lowercase
        String lowerCaseTitle = title.toLowerCase();
        // Replace spaces with underscores
        String identifier = lowerCaseTitle.replace(" ", "_");
        return identifier;
    }

    private void updateUserList() {
        policyref.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                List<Policy> newPolicyList = new ArrayList<>();
                for (DataSnapshot policySnapshot : dataSnapshot.getChildren()) {
                    String uid = policySnapshot.child("uid").getValue(String.class);
                    String link = policySnapshot.child("link").getValue(String.class);
                    String title = policySnapshot.child("title").getValue(String.class);
                    String details = policySnapshot.child("details").getValue(String.class);

                    Policy policy = new Policy(uid, link,title, details);
                    newPolicyList.add(policy);
                }
                policyViewModel.setPolicies(newPolicyList);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(AddingPolicyActivity.this, "Error: " + databaseError.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void onUserClicked() {
        Intent intent = new Intent(AddingPolicyActivity.this, MainActivity.class);
        startActivity(intent);
        finish(); // Optionally finish this activity when starting MainActivity
    }
}