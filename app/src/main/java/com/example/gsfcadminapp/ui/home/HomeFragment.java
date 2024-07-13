////package com.example.gsfcadminapp.ui.home;
////
////import android.content.Intent;
////import android.os.Bundle;
////import android.view.LayoutInflater;
////import android.view.View;
////import android.view.ViewGroup;
////import android.widget.ArrayAdapter;
////import android.widget.Spinner;
////import android.widget.Toast;
////
////import androidx.annotation.NonNull;
////import androidx.annotation.Nullable;
////import androidx.fragment.app.Fragment;
////import androidx.navigation.NavDirections;
////import androidx.navigation.Navigation;
////import androidx.recyclerview.widget.LinearLayoutManager;
////import androidx.recyclerview.widget.RecyclerView;
////
////import com.example.gsfcadminapp.R;
////import com.example.gsfcadminapp.User;
////import com.example.gsfcadminapp.ui.home.EditUserFragment;
////import com.google.firebase.database.DataSnapshot;
////import com.google.firebase.database.DatabaseError;
////import com.google.firebase.database.DatabaseReference;
////import com.google.firebase.database.FirebaseDatabase;
////import com.google.firebase.database.ValueEventListener;
////
////import java.util.ArrayList;
////import java.util.List;
//////
//////public class HomeFragment extends Fragment implements UserAdapter.OnUserClickListener {
//////
//////    private RecyclerView recyclerView;
//////    private UserAdapter userAdapter;
//////    private List<User> userList;
//////    private DatabaseReference employeesRef;
//////    private Spinner searchFilter;
//////
//////    public View onCreateView(@NonNull LayoutInflater inflater,
//////                             ViewGroup container, Bundle savedInstanceState) {
//////        View root = inflater.inflate(R.layout.fragment_home, container, false);
//////
//////        recyclerView = root.findViewById(R.id.recyclerview);
//////        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
//////
//////        userList = new ArrayList<>();
//////        userAdapter = new UserAdapter(userList, this);
//////        recyclerView.setAdapter(userAdapter);
//////
//////        // Initialize Firebase Database reference
//////        FirebaseDatabase database = FirebaseDatabase.getInstance();
//////        employeesRef = database.getReference("employees");
//////
//////        // Retrieve data from Firebase
//////        retrieveEmployeesData();
//////
//////        // Initialize search filter spinner
//////        searchFilter = root.findViewById(R.id.searchFilter);
//////        // Set adapter and data for spinner
//////        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(requireContext(),
//////                R.array.filter_options, android.R.layout.simple_spinner_item);
//////        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//////        searchFilter.setAdapter(adapter);
//////
//////        return root;
//////    }
//////
//////    private void retrieveEmployeesData() {
//////        employeesRef.addListenerForSingleValueEvent(new ValueEventListener() {
//////            @Override
//////            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//////                for (DataSnapshot empSnapshot : dataSnapshot.getChildren()) {
//////                    String empid = empSnapshot.child("empid").getValue(String.class);
//////                    String name = empSnapshot.child("name").getValue(String.class);
//////                    String email = empSnapshot.child("email").getValue(String.class);
//////                    String phone = empSnapshot.child("phone").getValue(String.class);
//////                    String role = empSnapshot.child("role").getValue(String.class);
//////                    String password = empSnapshot.child("password").getValue(String.class);
//////
//////                    // Create a User object and add it to the user list
//////                    User user = new User(empid, name, phone, role, email, password);
//////                    userList.add(user);
//////                }
//////                // Notify the adapter that the data set has changed
//////                userAdapter.notifyDataSetChanged();
//////            }
//////
//////            @Override
//////            public void onCancelled(@NonNull DatabaseError databaseError) {
//////                // Error occurred while retrieving data
//////                // Handle the error gracefully
//////                Toast.makeText(getActivity(), "Error: " + databaseError.getMessage(), Toast.LENGTH_SHORT).show();
//////            }
//////        });
//////    }
//////
//////
//////    @Override
//////    public void onUserClicked(User user) {
//////        // Create an intent to start EditUserActivity
//////        Intent intent = new Intent(getActivity(), EditUserActivity.class);
//////
//////        // Pass the user data to the activity
//////        intent.putExtra("user", user);
//////
//////        // Start the activity
//////        startActivity(intent);
//////    }
//////
//////
//////}
////
////import androidx.lifecycle.Observer;
////import androidx.lifecycle.ViewModelProvider;
////
////public class HomeFragment extends Fragment implements UserAdapter.OnUserClickListener {
////
////    private RecyclerView recyclerView;
////    private UserAdapter userAdapter;
////    private List<User> userList;
////    private DatabaseReference employeesRef;
////    private Spinner searchFilter;
////    private UserViewModel userViewModel;
////
////    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
////        View root = inflater.inflate(R.layout.fragment_home, container, false);
////
////        recyclerView = root.findViewById(R.id.recyclerview);
////        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
////
////        userList = new ArrayList<>();
////        userAdapter = new UserAdapter(userList, this);
////        recyclerView.setAdapter(userAdapter);
////
////        // Initialize ViewModel
////        userViewModel = new ViewModelProvider(requireActivity()).get(UserViewModel.class);
////
////        // Observe changes in the user list
////        userViewModel.getUsers().observe(getViewLifecycleOwner(), new Observer<List<User>>() {
////            @Override
////            public void onChanged(List<User> users) {
////                userList.clear();
////                userList.addAll(users);
////                userAdapter.notifyDataSetChanged();
////            }
////        });
////
////        // Initialize Firebase Database reference
////        FirebaseDatabase database = FirebaseDatabase.getInstance();
////        employeesRef = database.getReference("employees");
////
////        // Retrieve data from Firebase
////        retrieveEmployeesData();
////
////        // Initialize search filter spinner
////        searchFilter = root.findViewById(R.id.searchFilter);
////        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(requireContext(),
////                R.array.filter_options, android.R.layout.simple_spinner_item);
////        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
////        searchFilter.setAdapter(adapter);
////
////        return root;
////    }
////
////    private void retrieveEmployeesData() {
////        employeesRef.addListenerForSingleValueEvent(new ValueEventListener() {
////            @Override
////            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
////                List<User> newUserList = new ArrayList<>();
////                for (DataSnapshot empSnapshot : dataSnapshot.getChildren()) {
////                    String empid = empSnapshot.child("empid").getValue(String.class);
////                    String name = empSnapshot.child("name").getValue(String.class);
////                    String email = empSnapshot.child("email").getValue(String.class);
////                    String phone = empSnapshot.child("phone").getValue(String.class);
////                    String role = empSnapshot.child("role").getValue(String.class);
////                    String password = empSnapshot.child("password").getValue(String.class);
////
////                    User user = new User(empid, name, phone, role, email, password);
////                    newUserList.add(user);
////                }
////                userViewModel.setUsers(newUserList);
////            }
////
////            @Override
////            public void onCancelled(@NonNull DatabaseError databaseError) {
////                Toast.makeText(getActivity(), "Error: " + databaseError.getMessage(), Toast.LENGTH_SHORT).show();
////            }
////        });
////    }
////
////    @Override
////    public void onUserClicked(User user) {
////        // Create an intent to start EditUserActivity
////        Intent intent = new Intent(getActivity(), EditUserActivity.class);
////
////        // Pass the user data to the activity
////        intent.putExtra("user", user);
////
////        // Start the activity
////        startActivity(intent);
////    }
////}
//
//package com.example.gsfcadminapp.ui.home;
//
//import android.content.Intent;
//import android.os.Bundle;
//import android.text.TextUtils;
//import android.util.Log;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.ArrayAdapter;
//import android.widget.SearchView;
//import android.widget.Spinner;
//import android.widget.Toast;
//
//import androidx.annotation.NonNull;
//import androidx.appcompat.app.AppCompatActivity;
//import androidx.fragment.app.Fragment;
//import androidx.lifecycle.Observer;
//import androidx.lifecycle.ViewModelProvider;
//import androidx.recyclerview.widget.LinearLayoutManager;
//import androidx.recyclerview.widget.RecyclerView;
//
//import com.example.gsfcadminapp.R;
//import com.example.gsfcadminapp.User;
//import com.google.firebase.database.DataSnapshot;
//import com.google.firebase.database.DatabaseError;
//import com.google.firebase.database.DatabaseReference;
//import com.google.firebase.database.FirebaseDatabase;
//import com.google.firebase.database.ValueEventListener;
//
//import java.util.ArrayList;
//import java.util.List;
//import java.util.stream.Collectors;
//
//public class HomeFragment extends Fragment implements UserAdapter.OnUserClickListener {
//
//    private RecyclerView recyclerView;
//    private UserAdapter userAdapter;
//    private List<User> userList;
//    private List<User> filteredUserList;
//    private DatabaseReference employeesRef;
//    private Spinner searchFilter;
//    private SearchView searchView;
//    private UserViewModel userViewModel;
//
//    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
//        View root = inflater.inflate(R.layout.fragment_home, container, false);
//
//        recyclerView = root.findViewById(R.id.recyclerview);
//        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
//
//        userList = new ArrayList<>();
//        filteredUserList = new ArrayList<>();
//        userAdapter = new UserAdapter(filteredUserList, this);
//        recyclerView.setAdapter(userAdapter);
//
//        searchView = root.findViewById(R.id.search_view);
//
//        // Initialize ViewModel
//        userViewModel = new ViewModelProvider(requireActivity()).get(UserViewModel.class);
//
//        // Observe changes in the user list
//        userViewModel.getUsers().observe(getViewLifecycleOwner(), new Observer<List<User>>() {
//            @Override
//            public void onChanged(List<User> users) {
//                userList.clear();
//                userList.addAll(users);
//                filterUsers(searchView.getQuery().toString());
//            }
//        });
//
//        // Initialize Firebase Database reference
//        FirebaseDatabase database = FirebaseDatabase.getInstance();
//        employeesRef = database.getReference("employees");
//
//        // Retrieve data from Firebase
//        retrieveEmployeesData();
//
//        // Initialize search filter spinner
//        searchFilter = root.findViewById(R.id.searchFilter);
//        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(requireContext(),
//                R.array.filter_options, android.R.layout.simple_spinner_item);
//        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//        searchFilter.setAdapter(adapter);
//
//        // Set up SearchView listener
//        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
//            @Override
//            public boolean onQueryTextSubmit(String query) {
//                filterUsers(query);
//                return true;
//            }
//
//            @Override
//            public boolean onQueryTextChange(String newText) {
//                filterUsers(newText);
//                return true;
//            }
//        });
//
//        return root;
//    }
//
//    private void retrieveEmployeesData() {
//        employeesRef.addListenerForSingleValueEvent(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                List<User> newUserList = new ArrayList<>();
//                for (DataSnapshot empSnapshot : dataSnapshot.getChildren()) {
//                    String empid = empSnapshot.child("empid").getValue(String.class);
//                    String name = empSnapshot.child("name").getValue(String.class);
//                    String phone = empSnapshot.child("phone").getValue(String.class);
//                    String grade = empSnapshot.child("grade").getValue(String.class);
//                    String designation = empSnapshot.child("designation").getValue(String.class);
//                    String department = empSnapshot.child("department").getValue(String.class);
//                    String blood = empSnapshot.child("blood").getValue(String.class);
//                    String password = empSnapshot.child("password").getValue(String.class);
//
//                    User user = new User(empid, name, phone, grade, designation, department, blood, password);
//                    newUserList.add(user);
//                }
//                userViewModel.setUsers(newUserList);
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError databaseError) {
//                Toast.makeText(getActivity(), "Error: " + databaseError.getMessage(), Toast.LENGTH_SHORT).show();
//            }
//        });
//    }
//
//    private void filterUsers(String query) {
//        if (TextUtils.isEmpty(query)) {
//            filteredUserList.clear();
//            filteredUserList.addAll(userList);
//        } else {
//            filteredUserList.clear();
//            for (User user : userList) {
//                if (user.getName().toLowerCase().contains(query.toLowerCase()) ||
//                        user.getempid().toLowerCase().contains(query.toLowerCase())) {
//                    filteredUserList.add(user);
//                }
//            }
//        }
//        userAdapter.notifyDataSetChanged();
//    }
//
//    @Override
//    public void onUserClicked(User user) {
//        Intent intent = new Intent(getActivity(), EditUserActivity.class);
//        intent.putExtra("user", user);
//        startActivity(intent);
//    }
//
//    @Override
//    public void onDeleteClicked(User user, int position) {
//        employeesRef.child(user.getempid()).removeValue().addOnCompleteListener(task -> {
//            if (task.isSuccessful()) {
//                userList.remove(position);
//                userAdapter.notifyItemRemoved(position);
//                userAdapter.notifyItemRangeChanged(position, userList.size());
//                Toast.makeText(getContext(), "User deleted successfully", Toast.LENGTH_SHORT).show();
//            } else {
//                Log.e("Firebase", "Failed to delete user", task.getException());
//                Toast.makeText(getContext(), "Failed to delete user", Toast.LENGTH_SHORT).show();
//            }
//        });
//    }
//}
//

package com.example.gsfcadminapp.ui.home;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.SearchView;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.gsfcadminapp.R;
import com.example.gsfcadminapp.User;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment implements UserAdapter.OnUserClickListener {

    private RecyclerView recyclerView;
    private UserAdapter userAdapter;
    private List<User> userList;
    private List<User> filteredUserList;
    private DatabaseReference employeesRef;
    private Spinner searchFilter;
    private SearchView searchView;
    private UserViewModel userViewModel;
    private String currentFilter = "Name";
    private ProgressBar progressBar;
    private Button buttonExport;
    private ActivityResultLauncher<String> requestPermissionLauncher;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_home, container, false);

        recyclerView = root.findViewById(R.id.recyclerview);
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        requestPermissionLauncher = registerForActivityResult(new ActivityResultContracts.RequestPermission(), isGranted -> {
            if (isGranted) {

            } else {
                Toast.makeText(getContext(), "Permission denied to write to external storage", Toast.LENGTH_SHORT).show();
            }
        });

        userList = new ArrayList<>();
        filteredUserList = new ArrayList<>();
        userAdapter = new UserAdapter(filteredUserList, this);
        recyclerView.setAdapter(userAdapter);
        progressBar = root.findViewById(R.id.progressBarEmployeeExport);
        buttonExport = root.findViewById(R.id.buttonExportEmployees);

        searchView = root.findViewById(R.id.search_view);

        // Initialize ViewModel
        userViewModel = new ViewModelProvider(requireActivity()).get(UserViewModel.class);

        // Observe changes in the user list
        userViewModel.getUsers().observe(getViewLifecycleOwner(), new Observer<List<User>>() {
            @Override
            public void onChanged(List<User> users) {
                userList.clear();
                userList.addAll(users);
                filterUsers(searchView.getQuery().toString());
            }
        });

        // Initialize Firebase Database reference
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        employeesRef = database.getReference("employees");

        // Retrieve data from Firebase
        retrieveEmployeesData();

        // Initialize search filter spinner
        searchFilter = root.findViewById(R.id.searchFilter);
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(requireContext(),
                R.array.filter_options, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        searchFilter.setAdapter(adapter);

        // Set up SearchView listener
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                filterUsers(query);
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                filterUsers(newText);
                return true;
            }
        });

        // Set up Spinner listener
        searchFilter.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                currentFilter = parent.getItemAtPosition(position).toString();
                filterUsers(searchView.getQuery().toString());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                currentFilter = "Name";
            }
        });

        buttonExport.setOnClickListener(v -> {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M &&
                    ContextCompat.checkSelfPermission(getContext(), Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
                requestPermissionLauncher.launch(Manifest.permission.WRITE_EXTERNAL_STORAGE);
            } else {
                exportEmployeesToExcel();
            }
        });

        return root;
    }

    private void retrieveEmployeesData() {
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

    private void filterUsers(String query) {
        filteredUserList.clear();
        if (TextUtils.isEmpty(query)) {
            filteredUserList.addAll(userList);
        } else {
            for (User user : userList) {
                switch (currentFilter) {
                    case "Name":
                        if (user.getName() != null && user.getName().toLowerCase().contains(query.toLowerCase())) {
                            filteredUserList.add(user);
                        }
                        break;
                    case "Employee ID":
                        if (user.getempid() != null && user.getempid().toLowerCase().contains(query.toLowerCase())) {
                            filteredUserList.add(user);
                        }
                        break;
                    case "Blood Group":
                        if (user.getBlood() != null && user.getBlood().toLowerCase().contains(query.toLowerCase())) {
                            filteredUserList.add(user);
                        }
                        break;
                    case "Phone":
                        if (user.getPhone() != null && user.getPhone().toLowerCase().contains(query.toLowerCase())) {
                            filteredUserList.add(user);
                        }
                        break;
                    case "Grade":
                        if (user.getGrade() != null && user.getGrade().toLowerCase().contains(query.toLowerCase())) {
                            filteredUserList.add(user);
                        }
                        break;
                    case "Designation":
                        if (user.getDesignation() != null && user.getDesignation().toLowerCase().contains(query.toLowerCase())) {
                            filteredUserList.add(user);
                        }
                        break;
                    case "Department":
                        if (user.getDepartment() != null && user.getDepartment().toLowerCase().contains(query.toLowerCase())) {
                            filteredUserList.add(user);
                        }
                        break;
                }
            }
        }
        userAdapter.notifyDataSetChanged();
    }


    @Override
    public void onUserClicked(User user) {
        Intent intent = new Intent(getActivity(), EditUserActivity.class);
        intent.putExtra("user", user);
        startActivity(intent);
    }

    @Override
    public void onDeleteClicked(User user, int position) {
        employeesRef.child(user.getempid()).removeValue().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                userList.remove(position);
                userAdapter.notifyItemRemoved(position);
                userAdapter.notifyItemRangeChanged(position, userList.size());
                Toast.makeText(getContext(), "User deleted successfully", Toast.LENGTH_SHORT).show();
            } else {
                Log.e("Firebase", "Failed to delete user", task.getException());
                Toast.makeText(getContext(), "Failed to delete user", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void exportEmployeesToExcel() {
        progressBar.setVisibility(View.VISIBLE);  // Show progress bar

        employeesRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Workbook workbook = new XSSFWorkbook();
                Sheet sheet = workbook.createSheet("Employees List");

                Row headerRow = sheet.createRow(0);
                headerRow.createCell(0).setCellValue("Emp ID");
                headerRow.createCell(1).setCellValue("Name");
                headerRow.createCell(2).setCellValue("Email");
                headerRow.createCell(3).setCellValue("Phone");
                headerRow.createCell(4).setCellValue("Department");
                headerRow.createCell(5).setCellValue("Designation");
                headerRow.createCell(6).setCellValue("Grade");
                headerRow.createCell(7).setCellValue("Blood Group");
                headerRow.createCell(8).setCellValue("Password");

                int rowCount = 1;
                for (DataSnapshot employeeSnapshot : dataSnapshot.getChildren()) {
                    User employee = employeeSnapshot.getValue(User.class);
                    if (employee != null) {
                        Row row = sheet.createRow(rowCount++);
                        row.createCell(0).setCellValue(employee.getempid());
                        row.createCell(1).setCellValue(employee.getName());
                        row.createCell(2).setCellValue(employee.getEmail());
                        row.createCell(3).setCellValue(employee.getPhone());
                        row.createCell(4).setCellValue(employee.getDepartment());
                        row.createCell(5).setCellValue(employee.getDesignation());
                        row.createCell(6).setCellValue(employee.getGrade());
                        row.createCell(7).setCellValue(employee.getBlood());
                        row.createCell(8).setCellValue(employee.getPassword());
                    }
                }

                File dir = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS), "GSFE Folder");
                if (!dir.exists()) {
                    dir.mkdirs(); // Ensure the directory exists
                }

                File file = new File(dir, "EmployeesList.xlsx");

                try (FileOutputStream outputStream = new FileOutputStream(file)) {
                    workbook.write(outputStream);
                    Toast.makeText(getContext(), "File exported to GSFE Folder", Toast.LENGTH_SHORT).show();
                } catch (IOException e) {
                    Log.e("HomeFragment", "Failed to export file", e);
                    Toast.makeText(getContext(), "Failed to export file", Toast.LENGTH_SHORT).show();
                } finally {
                    try {
                        workbook.close();
                    } catch (IOException e) {
                        Log.e("HomeFragment", "Failed to close workbook", e);
                    }
                    progressBar.setVisibility(View.GONE);  // Hide progress bar
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(getContext(), "Failed to load employees.", Toast.LENGTH_SHORT).show();
                progressBar.setVisibility(View.GONE);  // Hide progress bar
            }
        });
    }
}

