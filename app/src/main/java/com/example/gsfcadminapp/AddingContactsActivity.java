package com.example.gsfcadminapp;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.example.gsfcadminapp.ui.contacts.Contacts;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class AddingContactsActivity extends AppCompatActivity {

    private EditText editTextName, editTextPhone, editTextDesignation;
    private Button buttonAdd, buttonCancel;
    private DatabaseReference contactRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adding_contacts);

        editTextName = findViewById(R.id.addTextContactName);
        editTextPhone = findViewById(R.id.addTextContactPhone);
        editTextDesignation = findViewById(R.id.addTextContactDesignation);

        buttonAdd = findViewById(R.id.buttonAddContact);
        buttonCancel = findViewById(R.id.buttonCancelContact);

        buttonAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveUserData();
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

    private void saveUserData() {
        String name = editTextName.getText().toString();
        String phone = editTextPhone.getText().toString();
        String designation = editTextDesignation.getText().toString();

        phone = "+91"+phone;


        if (isPhonePerfect(phone)) {
            editTextPhone.setError("Phone Number must be 10 digits");
            return;
        }

        if (!name.isEmpty() && !phone.isEmpty() && !designation.isEmpty()) {
            Contacts newContact = new Contacts(name, phone, designation);

            FirebaseDatabase database = FirebaseDatabase.getInstance();
            contactRef = database.getReference("contacts");

            contactRef.child(phone).setValue(newContact).addOnCompleteListener(task -> {
                if (task.isSuccessful()) {
                    Toast.makeText(AddingContactsActivity.this, "User added successfully", Toast.LENGTH_SHORT).show();

                } else {
                    Toast.makeText(AddingContactsActivity.this, "Failed to add user", Toast.LENGTH_SHORT).show();
                }
            });
        } else {
            Toast.makeText(AddingContactsActivity.this, "Please fill all fields", Toast.LENGTH_SHORT).show();
        }
    }

    public void onUserClicked() {
        Intent intent = new Intent(AddingContactsActivity.this, MainActivity.class);
        startActivity(intent);
        finish(); // Optionally finish this activity when starting MainActivity
    }

    private boolean isPhonePerfect(String phoneNumber) {
        if (phoneNumber.length() == 10) {
            return true;
        }
        else {
            return false;
        }
    }
}