package com.example.gsfcadminapp.ui.vote;

import static android.content.Intent.getIntent;

import android.Manifest;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.gsfcadminapp.R;
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
import java.util.Map;


//import android.content.pm.PackageManager;
//import android.inputmethodservice.Keyboard;
//import android.os.Build;
//import android.os.Bundle;
//import android.util.Log;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.Button;
//import android.widget.Toast;
//
//import androidx.annotation.NonNull;
//import androidx.annotation.Nullable;
//import androidx.core.app.ActivityCompat;
//import androidx.core.content.ContextCompat;
//import androidx.fragment.app.Fragment;
//import androidx.recyclerview.widget.LinearLayoutManager;
//import androidx.recyclerview.widget.RecyclerView;
//
//import com.example.gsfcadminapp.Manifest;
//import com.example.gsfcadminapp.R;
//import com.google.android.gms.tasks.OnFailureListener;
//import com.google.android.gms.tasks.OnSuccessListener;
//import com.google.firebase.database.DataSnapshot;
//import com.google.firebase.database.DatabaseError;
//import com.google.firebase.database.DatabaseReference;
//import com.google.firebase.database.FirebaseDatabase;
//import com.google.firebase.database.Query;
//import com.google.firebase.database.ValueEventListener;
//
//import org.apache.poi.ss.usermodel.Row;
//import org.apache.poi.ss.usermodel.Sheet;
//import org.apache.poi.ss.usermodel.Workbook;
//import org.apache.poi.xssf.usermodel.XSSFWorkbook;
//
//import java.io.File;
//import java.io.FileOutputStream;
//import java.io.IOException;
//import java.util.ArrayList;
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;

//public class VoteFragment extends Fragment {
//    private RecyclerView recyclerView;
//    private VoteAdapter voteAdapter;
//    private DatabaseReference voteRef;
//    private Button buttonEndSession, buttonDeleteVote;
//
//    @Override
//    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
//        View view = inflater.inflate(R.layout.fragment_vote, container, false);
//
//        recyclerView = view.findViewById(R.id.recyclerViewVotes);
//        buttonEndSession = view.findViewById(R.id.buttonEndSession);
//        buttonDeleteVote = view.findViewById(R.id.buttonDeleteVote);
//
//        voteRef = FirebaseDatabase.getInstance().getReference("vote");
//
//        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
//        voteAdapter = new VoteAdapter(new ArrayList<>());
//        recyclerView.setAdapter(voteAdapter);
//
//        loadVotes();
//
//        buttonEndSession.setOnClickListener(v -> endSession());
//        buttonDeleteVote.setOnClickListener(v -> deleteVote());
//
//        return view;
//    }
//
//    private void loadVotes() {
//        voteRef.addValueEventListener(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                List<Vote> votes = new ArrayList<>();
//                for (DataSnapshot voteSnapshot : dataSnapshot.getChildren()) {
//                    Vote vote = voteSnapshot.getValue(Vote.class);
//                    votes.add(vote);
//                }
//                voteAdapter.setVotes(votes);
//            }
//
//            @Override
//            public void onCancelled(@NonNull DatabaseError databaseError) {
//                Toast.makeText(getContext(), "Failed to load votes.", Toast.LENGTH_SHORT).show();
//            }
//        });
//    }
//
//    private void endSession() {
//        // Implement logic to end the voting session
//    }
//
//    private void deleteVote() {
//        // Implement logic to delete the vote
//    }
//}
//

public class VoteFragment extends Fragment implements VoteAdapter.OnVoteActionListener {
    private RecyclerView recyclerView;
    private VoteAdapter voteAdapter;
    private DatabaseReference voteRef;
    private List<Vote> votes;
    private static final int PERMISSION_REQUEST_CODE = 100;
    private int exportPosition;
    private ActivityResultLauncher<String> requestPermissionLauncher;
    private ProgressBar progressBar;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_vote, container, false);

        requestPermissionLauncher = registerForActivityResult(new ActivityResultContracts.RequestPermission(), isGranted -> {
            if (isGranted) {

            } else {
                Toast.makeText(getContext(), "Permission denied to write to external storage", Toast.LENGTH_SHORT).show();
            }
        });

        recyclerView = view.findViewById(R.id.recyclerViewVotes);
        voteRef = FirebaseDatabase.getInstance().getReference("vote");
        votes = new ArrayList<>();
        progressBar = view.findViewById(R.id.progressBarVotesExport);

        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        voteAdapter = new VoteAdapter(votes, this);
        recyclerView.setAdapter(voteAdapter);

        loadVotes();

        return view;
    }

    private void loadVotes() {
        voteRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                votes.clear();
                for (DataSnapshot voteSnapshot : dataSnapshot.getChildren()) {
                    Vote vote = voteSnapshot.getValue(Vote.class);
                    votes.add(vote);
                }
                voteAdapter.setVotes(votes);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(getContext(), "Failed to load votes.", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onEndSessionClick(int position) {
        Vote vote = votes.get(position);
        voteRef.child(vote.pid).child("status").setValue("ended")
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        Toast.makeText(getContext(), "Voting session ended.", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(getContext(), "Failed to end session.", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    @Override
    public void onDeleteVoteClick(int position) {
        Vote vote = votes.get(position);
        voteRef.child(vote.pid).removeValue()
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        Toast.makeText(getContext(), "Vote deleted.", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(getContext(), "Failed to delete vote.", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    @Override
    public void onExportVoteClick(int position) {
        exportPosition = position;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M &&
                ContextCompat.checkSelfPermission(getContext(), Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            requestPermissionLauncher.launch(Manifest.permission.WRITE_EXTERNAL_STORAGE);
        } else {
            exportVotersToExcel(exportPosition);
        }
    }

//    private void exportVotersToExcel(int position) {
//        Vote vote = votes.get(position);
//
//        Workbook workbook = new XSSFWorkbook();
//        Sheet sheet = workbook.createSheet("Voters List");
//
//        Row headerRow = sheet.createRow(0);
//        headerRow.createCell(0).setCellValue("Option");
//        headerRow.createCell(1).setCellValue("Voter ID");
//
//        int rowCount = 1;
//
//        for (Map.Entry<String, Option> optionEntry : vote.options.entrySet()) {
//            String optionValue = optionEntry.getValue().value;
//            for (Map.Entry<String, Voter> voterEntry : optionEntry.getValue().voters.entrySet()) {
//                String voterId = voterEntry.getValue().empid;
//
//                Row row = sheet.createRow(rowCount++);
//                row.createCell(0).setCellValue(optionValue);
//                row.createCell(1).setCellValue(voterId);
//            }
//        }
//
//        File dir = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS), "GSFE Folder");
//        if (!dir.exists()) {
//            dir.mkdirs(); // Ensure the directory exists
//        }
//
//        File file = new File(dir, "VotersList.xlsx");
//
//        try (FileOutputStream outputStream = new FileOutputStream(file)) {
//            workbook.write(outputStream);
//            Toast.makeText(getContext(), "File exported to GSFE Folder", Toast.LENGTH_SHORT).show();
//        } catch (IOException e) {
//            Log.e("VoteFragment", "Failed to export file", e);
//            Toast.makeText(getContext(), "Failed to export file", Toast.LENGTH_SHORT).show();
//        } finally {
//            try {
//                workbook.close();
//            } catch (IOException e) {
//                Log.e("VoteFragment", "Failed to close workbook", e);
//            }
//        }
//    }

    private void exportVotersToExcel(int position) {
        progressBar.setVisibility(View.VISIBLE);
        Vote vote = votes.get(position);

        boolean hasVotes = false;
        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet("Voters List");

        Row headerRow = sheet.createRow(0);
        headerRow.createCell(0).setCellValue("Option");
        headerRow.createCell(1).setCellValue("Voter ID");

        int rowCount = 1;

        for (Map.Entry<String, Option> optionEntry : vote.options.entrySet()) {
            String optionValue = optionEntry.getValue().value;
            Map<String, Voter> votersMap = optionEntry.getValue().voters;

            if (votersMap != null && !votersMap.isEmpty()) {
                hasVotes = true;
                for (Map.Entry<String, Voter> voterEntry : votersMap.entrySet()) {
                    String voterId = voterEntry.getValue().empid;

                    Row row = sheet.createRow(rowCount++);
                    row.createCell(0).setCellValue(optionValue);
                    row.createCell(1).setCellValue(voterId);
                }
            }
        }

        if (!hasVotes) {
            Toast.makeText(getContext(), "No voting happened till now.", Toast.LENGTH_SHORT).show();
            try {
                workbook.close();
            } catch (IOException e) {
                Log.e("VoteFragment", "Failed to close workbook", e);
            }
            return;
        }

        File dir = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS), "GSFE Folder");
        if (!dir.exists()) {
            dir.mkdirs(); // Ensure the directory exists
        }

        File file = new File(dir, "VotersList.xlsx");

        try (FileOutputStream outputStream = new FileOutputStream(file)) {
            workbook.write(outputStream);
            Toast.makeText(getContext(), "File exported to GSFE Folder", Toast.LENGTH_SHORT).show();
        } catch (IOException e) {
            Log.e("VoteFragment", "Failed to export file", e);
            Toast.makeText(getContext(), "Failed to export file", Toast.LENGTH_SHORT).show();
        } finally {
            try {
                workbook.close();
            } catch (IOException e) {
                Log.e("VoteFragment", "Failed to close workbook", e);
            }
            progressBar.setVisibility(View.GONE);
        }
    }


}
