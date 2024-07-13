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
import androidx.lifecycle.ViewModelProvider;

import com.example.gsfcadminapp.ui.home.UserViewModel;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class AddingUserActivity extends AppCompatActivity {

    private EditText editTextEmpid, editTextUsername, editTextGrade, editTextPhone, editTextPassword, editTextDesignation, editTextDepartment, editTextBlood, editTextEmail;
    private Button buttonSave, buttonCancel;
    private UserViewModel userViewModel;
    private DatabaseReference employeesRef;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adding_user);

        editTextEmpid = findViewById(R.id.addTextEmpid);
        editTextUsername = findViewById(R.id.addTextUsername);
        editTextGrade = findViewById(R.id.addTextGrade);
        editTextDesignation = findViewById(R.id.addTextDesignation);
        editTextDepartment = findViewById(R.id.addTextDepartment);
        editTextBlood = findViewById(R.id.addTextBlood);
        editTextPhone = findViewById(R.id.addTextPhone);
        editTextPassword = findViewById(R.id.addTextPassword);
        editTextEmail = findViewById(R.id.addTextEmail);
        buttonSave = findViewById(R.id.buttonAdd);
        buttonCancel = findViewById(R.id.buttonCancel);

        // Initialize ViewModel
        userViewModel = new ViewModelProvider(this).get(UserViewModel.class);

        buttonSave.setOnClickListener(new View.OnClickListener() {
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
        String empid = editTextEmpid.getText().toString();
        String username = editTextUsername.getText().toString();
        String grade = editTextGrade.getText().toString();
        String designation = editTextDesignation.getText().toString();
        String department = editTextDepartment.getText().toString();
        String blood = editTextBlood.getText().toString();
        String phone = editTextPhone.getText().toString();
        String password = editTextPassword.getText().toString();
        String email = editTextEmail.getText().toString();

        if (!isPhonePerfect(phone)) {
            editTextPhone.setError("Phone Number must be 10 digits");
            return;
        }

        if (!empid.isEmpty() && !username.isEmpty() && !grade.isEmpty() && !phone.isEmpty() && !password.isEmpty() && !designation.isEmpty() && !department.isEmpty() && !blood.isEmpty()) {
            User newUser = new User(empid, username, phone, grade, designation, department, blood, password, email);

            FirebaseDatabase database = FirebaseDatabase.getInstance();
            employeesRef = database.getReference("employees");

            employeesRef.child(empid).setValue(newUser).addOnCompleteListener(task -> {
                if (task.isSuccessful()) {
                    Toast.makeText(AddingUserActivity.this, "User added successfully", Toast.LENGTH_SHORT).show();

                        // Update the user list in ViewModel
                    updateUserList();
                } else {
                    Toast.makeText(AddingUserActivity.this, "Failed to add user", Toast.LENGTH_SHORT).show();
                }
            });
        } else {
            Toast.makeText(AddingUserActivity.this, "Please fill all fields", Toast.LENGTH_SHORT).show();
        }
    }

    private void updateUserList() {
        employeesRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                List<User> newUserList = new ArrayList<>();
                for (DataSnapshot empSnapshot : dataSnapshot.getChildren()) {
                    String empid = empSnapshot.child("empid").getValue(String.class);
                    String name = empSnapshot.child("name").getValue(String.class);
                    String phone = empSnapshot.child("phone").getValue(String.class);
                    String grade = empSnapshot.child("grade").getValue(String.class);
                    String designation = empSnapshot.child("designation").getValue(String.class);
                    String department = empSnapshot.child("department").getValue(String.class);
                    String blood = empSnapshot.child("blood").getValue(String.class);
                    String password = empSnapshot.child("password").getValue(String.class);
                    String email = empSnapshot.child("email").getValue(String.class);

                    User user = new User(empid, name, phone, grade, designation, department, blood, password, email);
                    newUserList.add(user);
                }
                userViewModel.setUsers(newUserList);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(AddingUserActivity.this, "Error: " + databaseError.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void onUserClicked() {
        Intent intent = new Intent(AddingUserActivity.this, MainActivity.class);
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
