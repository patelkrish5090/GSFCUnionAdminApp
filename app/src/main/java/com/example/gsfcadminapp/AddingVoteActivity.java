//package com.example.gsfcadminapp;
//
//import android.os.Bundle;
//import android.view.View;
//import android.widget.Button;
//import android.widget.EditText;
//import android.widget.LinearLayout;
//import androidx.annotation.Nullable;
//import androidx.appcompat.app.AppCompatActivity;
//import com.google.firebase.database.DatabaseReference;
//import com.google.firebase.database.FirebaseDatabase;
//
//public class AddingVoteActivity extends AppCompatActivity {
//
//    private LinearLayout optionsContainer;
//    private int optionCount = 0;
//    private DatabaseReference mDatabase;
//
//    @Override
//    protected void onCreate(@Nullable Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_adding_vote);
//
//        EditText questionInput = findViewById(R.id.question_input);
//        optionsContainer = findViewById(R.id.options_container);
//        Button addOptionButton = findViewById(R.id.add_option_button);
//        Button startVoteButton = findViewById(R.id.vote_save);
//
//        // Initialize Firebase Database reference
//        mDatabase = FirebaseDatabase.getInstance().getReference().child("vote");
//
//        addOptionButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                addOptionField();
//            }
//        });
//
//        startVoteButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                saveVoteToFirebase();
//            }
//        });
//    }
//
//    private void addOptionField() {
//        optionCount++;
//
//        LinearLayout newOptionLayout = new LinearLayout(this);
//        newOptionLayout.setOrientation(LinearLayout.HORIZONTAL);
//        newOptionLayout.setLayoutParams(new LinearLayout.LayoutParams(
//                LinearLayout.LayoutParams.MATCH_PARENT,
//                LinearLayout.LayoutParams.WRAP_CONTENT
//        ));
//
//        EditText newOption = new EditText(this);
//        newOption.setHint("Option " + optionCount);
//        newOption.setTag("option" + optionCount); // Set a unique tag for each EditText
//        LinearLayout.LayoutParams optionLayoutParams = new LinearLayout.LayoutParams(
//                0,
//                LinearLayout.LayoutParams.WRAP_CONTENT,
//                1.0f
//        );
//        newOption.setLayoutParams(optionLayoutParams);
//
//        Button removeOptionButton = new Button(this);
//        removeOptionButton.setText("Remove");
//        removeOptionButton.setTag(optionCount);
//        removeOptionButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                optionCount--;
//                optionsContainer.removeView(newOptionLayout);
//            }
//        });
//
//        newOptionLayout.addView(newOption);
//        newOptionLayout.addView(removeOptionButton);
//        optionsContainer.addView(newOptionLayout);
//    }
//
//    private void saveVoteToFirebase() {
//        // Save question to Firebase
//        EditText questionInput = findViewById(R.id.question_input);
//        String questionText = questionInput.getText().toString();
//        if (!questionText.isEmpty()) {
//            String pollId = "P" + System.currentTimeMillis(); // Generate a unique poll ID
//            DatabaseReference pollRef = mDatabase.child(pollId);
//            pollRef.child("question").setValue(questionText);
//            pollRef.child("status").setValue("active");
//            pollRef.child("pid").setValue(pollId);
//
//            // Save options to Firebase
//            for (int i = 1; i <= optionCount; i++) {
//                EditText optionEditText = optionsContainer.findViewWithTag("option" + i);
//                if (optionEditText != null) {
//                    String optionText = optionEditText.getText().toString();
//                    if (!optionText.isEmpty()) {
//                        String optionId = "option" + i;
//                        DatabaseReference optionRef = pollRef.child(optionId);
//                        optionRef.child("value").setValue(optionText);
//                        optionRef.child("oid").setValue(optionId);
//                        optionRef.child("count").setValue("0"); // Initialize count to 0
//                    }
//                }
//            }
//        }
//    }
//
//    // Utility method to convert dp to pixels
//    private int dpToPx(int dp) {
//        return (int) (dp * getResources().getDisplayMetrics().density);
//    }
//}

package com.example.gsfcadminapp;


import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Random;

public class AddingVoteActivity extends AppCompatActivity {

    private LinearLayout optionsContainer;
    private int optionCount = 0;
    private DatabaseReference mDatabase;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adding_vote);

        EditText questionInput = findViewById(R.id.question_input);
        optionsContainer = findViewById(R.id.options_container);
        Button addOptionButton = findViewById(R.id.add_option_button);
        Button startVoteButton = findViewById(R.id.vote_save);

        // Initialize Firebase Database reference
        mDatabase = FirebaseDatabase.getInstance().getReference().child("vote");

        addOptionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addOptionField();
            }
        });

        startVoteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveVoteToFirebase();
            }
        });
    }

    private void addOptionField() {
        optionCount++;

        LinearLayout newOptionLayout = new LinearLayout(this);
        newOptionLayout.setOrientation(LinearLayout.HORIZONTAL);
        newOptionLayout.setLayoutParams(new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.WRAP_CONTENT
        ));

        EditText newOption = new EditText(this);
        newOption.setHint("Option " + optionCount);
        newOption.setTag("option" + optionCount); // Set a unique tag for each EditText
        LinearLayout.LayoutParams optionLayoutParams = new LinearLayout.LayoutParams(
                0,
                LinearLayout.LayoutParams.WRAP_CONTENT,
                1.0f
        );
        newOption.setLayoutParams(optionLayoutParams);

        Button removeOptionButton = new Button(this);
        removeOptionButton.setText("Remove");
        removeOptionButton.setTag(optionCount);
        removeOptionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                optionCount--;
                optionsContainer.removeView(newOptionLayout);
            }
        });

        newOptionLayout.addView(newOption);
        newOptionLayout.addView(removeOptionButton);
        optionsContainer.addView(newOptionLayout);
    }

    private void saveVoteToFirebase() {
        // Save question to Firebase
        EditText questionInput = findViewById(R.id.question_input);
        String questionText = questionInput.getText().toString();
        if (!questionText.isEmpty()) {
            String pollId = generatePollId(); // Generate a unique poll ID
            DatabaseReference pollRef = mDatabase.child(pollId);
            pollRef.child("question").setValue(questionText);
            pollRef.child("status").setValue("active");
            pollRef.child("pid").setValue(pollId);
            pollRef.child("total_count").setValue("0"); // Initialize total count to 0

            // Save options to Firebase
            Map<String, Object> optionsMap = new HashMap<>();
            for (int i = 1; i <= optionCount; i++) {
                EditText optionEditText = optionsContainer.findViewWithTag("option" + i);
                if (optionEditText != null) {
                    String optionText = optionEditText.getText().toString();
                    if (!optionText.isEmpty()) {
                        String optionId = "option" + i;
                        Map<String, Object> optionMap = new HashMap<>();
                        optionMap.put("value", optionText);
                        optionMap.put("oid", optionId);
                        optionMap.put("count", "0"); // Initialize count to 0

                        optionsMap.put(optionId, optionMap);
                    }
                }
            }
            pollRef.child("options").setValue(optionsMap);
            Toast.makeText(this, "Vote created successfully", Toast.LENGTH_SHORT).show();
            finish(); // Close activity after saving vote
        } else {
            Toast.makeText(this, "Question cannot be empty", Toast.LENGTH_SHORT).show();
        }
    }

    // Utility method to convert dp to pixels
    private int dpToPx(int dp) {
        return (int) (dp * getResources().getDisplayMetrics().density);
    }


    private String generatePollId() {
        SimpleDateFormat sdf = new SimpleDateFormat("ddMMyyyy", Locale.getDefault());
        String date = sdf.format(new Date());
        Random random = new Random();
        int randomTwoDigit = random.nextInt(90) + 10; // Generates a random 2-digit number between 10 and 99
        return "P" + date + randomTwoDigit;
    }
}

