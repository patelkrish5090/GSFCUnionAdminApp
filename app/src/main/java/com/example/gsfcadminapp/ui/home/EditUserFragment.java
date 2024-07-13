package com.example.gsfcadminapp.ui.home;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.gsfcadminapp.ui.home.UserAdapter;
import com.example.gsfcadminapp.MainActivity;
import com.example.gsfcadminapp.R;
import com.example.gsfcadminapp.User;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
//
//public class EditUserFragment extends Fragment {
//
//    private EditText editTextUsername, editTextRole, editTextEmail, editTextPhone, editTextPassword;
//    private Button buttonSave, buttonCancel;
//    private User user;
//    private String originalempid;
//    private UserAdapter userAdapter;
//
//    public static EditUserFragment newInstance(User user) {
//        EditUserFragment fragment = new EditUserFragment();
//        Bundle args = new Bundle();
//        args.putParcelable("user", user);
//        fragment.setArguments(args);
//        return fragment;
//    }
//
//    @Override
//    public View onCreateView(LayoutInflater inflater, ViewGroup container,
//                             Bundle savedInstanceState) {
//        return inflater.inflate(R.layout.fragment_edit_user, container, false);
//    }
//
//    @Override
//    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
//        super.onViewCreated(view, savedInstanceState);
//
//        editTextUsername = view.findViewById(R.id.editTextUsername);
//        editTextRole = view.findViewById(R.id.editTextRole);
//        editTextEmail = view.findViewById(R.id.editTextEmail);
//        editTextPhone = view.findViewById(R.id.editTextPhone);
//        editTextPassword = view.findViewById(R.id.editTextPassword);
//        buttonSave = view.findViewById(R.id.buttonSave);
//        buttonCancel = view.findViewById(R.id.buttonCancel);
//
//        user = getArguments().getParcelable("user");
//        if (user != null) {
//            originalempid = user.getempid();
//            editTextUsername.setText(user.getName());
//            editTextRole.setText(user.getRole());
//            editTextEmail.setText(user.getEmail());
//            editTextPhone.setText(user.getPhone());
//            editTextPassword.setText(user.getPassword());
//        }
//
//        buttonSave.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                // Save changes to user data
//                saveUserData();
////                userAdapter.notifyDataSetChanged();
//                onUserClicked();
//            }
//        });
//
//        buttonCancel.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                // Cancel editing and navigate back to HomeFragment
////                navigateBack();
//                onUserClicked();
//            }
//        });
//    }
//
//    private void saveUserData() {
//        if (user != null) {
//            // Update the user object with new data from the edit texts
//            user.setempid(originalempid.toString());
//            user.setName(editTextUsername.getText().toString());
//            user.setRole(editTextRole.getText().toString());
//            user.setEmail(editTextEmail.getText().toString());
//            user.setPhone(editTextPhone.getText().toString());
//            user.setPassword(editTextPassword.getText().toString());
//
//            // Get a reference to the Firebase database
//            FirebaseDatabase database = FirebaseDatabase.getInstance();
//            DatabaseReference employeesRef = database.getReference("employees");
//
//            // Find the user by empid and update their data
//            employeesRef.orderByChild("empid").equalTo(user.getempid()).addListenerForSingleValueEvent(new ValueEventListener() {
//                @Override
//                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                    for (DataSnapshot empSnapshot : dataSnapshot.getChildren()) {
//                        // Update the user data
//                        empSnapshot.getRef().setValue(user);
//
//                        // Notify the user about the successful update
//                        Toast.makeText(getActivity(), "User data updated successfully", Toast.LENGTH_SHORT).show();
//                    }
//                }
//
//                @Override
//                public void onCancelled(@NonNull DatabaseError databaseError) {
//                    // Handle the error gracefully
//                    Toast.makeText(getActivity(), "Error: " + databaseError.getMessage(), Toast.LENGTH_SHORT).show();
//                }
//            });
//        }
//    }
//
//
//
//    public void onUserClicked() {
//        // Create an intent to start EditUserActivity
//        Intent intent = new Intent(getActivity(), MainActivity.class);
//
//        // Start the activity
//        startActivity(intent);
//    }
//}

import androidx.lifecycle.ViewModelProvider;

import java.util.ArrayList;
import java.util.List;

public class EditUserFragment extends Fragment {

    private EditText editTextUsername, editTextGrade, editTextDesignation, editTextDepartment, editTextBlood, editTextPhone, editTextPassword, editTextEmail;
    private Button buttonSave, buttonCancel;
    private User user;
    private String originalempid;
    private UserViewModel userViewModel;
    private DatabaseReference employeesRef;
    public static EditUserFragment newInstance(User user) {
        EditUserFragment fragment = new EditUserFragment();
        Bundle args = new Bundle();
        args.putParcelable("user", user);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_edit_user, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        editTextUsername = view.findViewById(R.id.editTextUsername);
        editTextGrade = view.findViewById(R.id.editTextGrade);
        editTextDesignation = view.findViewById(R.id.editTextDesignation);
        editTextDepartment = view.findViewById(R.id.editTextDepartment);
        editTextBlood = view.findViewById(R.id.editTextBlood);
        editTextPhone = view.findViewById(R.id.editTextPhone);
        editTextPassword = view.findViewById(R.id.editTextPassword);
        editTextEmail = view.findViewById(R.id.editTextEmail);
        buttonSave = view.findViewById(R.id.buttonSave);
        buttonCancel = view.findViewById(R.id.buttoneditCancel);

        // Initialize ViewModel
        userViewModel = new ViewModelProvider(requireActivity()).get(UserViewModel.class);

        user = getArguments().getParcelable("user");
        if (user != null) {
            originalempid = user.getempid();
            editTextUsername.setText(user.getName());
            editTextGrade.setText(user.getGrade());
            editTextDesignation.setText(user.getDesignation());
            editTextDepartment.setText(user.getDepartment());
            editTextBlood.setText(user.getBlood());
            editTextPhone.setText(user.getPhone());
            editTextPassword.setText(user.getPassword());
            editTextEmail.setText(user.getEmail());
        }

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
        if (user != null) {
            user.setempid(originalempid);
            user.setName(editTextUsername.getText().toString());
            user.setGrade(editTextGrade.getText().toString());
            user.setDesignation(editTextDesignation.getText().toString());
            user.setDepartment(editTextDepartment.getText().toString());
            user.setBlood(editTextBlood.getText().toString());
            user.setPhone(editTextPhone.getText().toString());
            user.setPassword(editTextPassword.getText().toString());
            user.setEmail(editTextEmail.getText().toString());

            FirebaseDatabase database = FirebaseDatabase.getInstance();
            DatabaseReference employeesRef = database.getReference("employees");

            employeesRef.orderByChild("empid").equalTo(user.getempid()).addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    for (DataSnapshot empSnapshot : dataSnapshot.getChildren()) {
                        empSnapshot.getRef().setValue(user);

                        Toast.makeText(getActivity(), "User data updated successfully", Toast.LENGTH_SHORT).show();

                        // Update the user list in ViewModel
                        updateUserList();
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                    Toast.makeText(getActivity(), "Error: " + databaseError.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
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
                Toast.makeText(getActivity(), "Error: " + databaseError.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    public void onUserClicked() {
        Intent intent = new Intent(getActivity(), MainActivity.class);
        startActivity(intent);
    }
}

