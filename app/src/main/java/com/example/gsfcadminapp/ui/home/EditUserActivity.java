package com.example.gsfcadminapp.ui.home;

import static android.content.Intent.getIntent;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.example.gsfcadminapp.R;
import com.example.gsfcadminapp.User;
import com.example.gsfcadminapp.ui.home.EditUserFragment;

public class EditUserActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_user);

        if (savedInstanceState == null) {
            // Retrieve the user data from the intent
            User user = getIntent().getParcelableExtra("user");

            // Create a new instance of EditUserFragment and set the user data as arguments
            EditUserFragment editUserFragment = EditUserFragment.newInstance(user);

            // Begin the fragment transaction
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragment_container_edit_user, editUserFragment)
                    .commit();
        }
    }
}
