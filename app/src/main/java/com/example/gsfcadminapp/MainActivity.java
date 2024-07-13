    package com.example.gsfcadminapp;

    import static android.app.PendingIntent.getActivity;

    import android.content.Intent;
    import android.os.Bundle;
    import android.view.MenuItem;
    import android.view.View;
    import android.view.Menu;
    import android.widget.Toast;

    import androidx.annotation.NonNull;
    import androidx.appcompat.app.AppCompatActivity;
    import androidx.drawerlayout.widget.DrawerLayout;
    import androidx.navigation.NavController;
    import androidx.navigation.Navigation;
    import androidx.navigation.ui.AppBarConfiguration;
    import androidx.navigation.ui.NavigationUI;
    import com.example.gsfcadminapp.databinding.ActivityMainBinding;
    import com.github.clans.fab.FloatingActionButton;
    import com.github.clans.fab.FloatingActionMenu;
    import com.google.android.material.navigation.NavigationView;

    public class MainActivity extends AppCompatActivity {

        private AppBarConfiguration mAppBarConfiguration;
        private ActivityMainBinding binding;
        FloatingActionMenu fabMenu;
        FloatingActionButton fabUser, fabAnnouncement, fabVote, fabPolicy, fabContact;

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);

            binding = ActivityMainBinding.inflate(getLayoutInflater());
            setContentView(binding.getRoot());

            setSupportActionBar(binding.appBarMain.toolbar);
            DrawerLayout drawer = binding.drawerLayout;
            NavigationView navigationView = binding.navView;

            // Set up navigation
            mAppBarConfiguration = new AppBarConfiguration.Builder(
                    R.id.nav_home, R.id.nav_announcements, R.id.nav_policy, R.id.nav_vote)
                    .setOpenableLayout(drawer)
                    .build();
            NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
            NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
            NavigationUI.setupWithNavController(navigationView, navController);

            // Initialize FloatingActionButtons
            fabMenu = findViewById(R.id.fab_menu);
            fabAnnouncement = findViewById(R.id.fab_announcement);
            fabUser = findViewById(R.id.fab_user);
            fabVote = findViewById(R.id.fab_vote);
            fabPolicy = findViewById(R.id.fab_policy);
            fabContact = findViewById(R.id.fab_contacts);

            // Set OnClickListener for fabAnnouncement
            fabAnnouncement.setOnClickListener(v -> {
                Intent intent = new Intent(MainActivity.this, AddingAnnouncementActivity.class);
                startActivity(intent);
            });

            // Set OnClickListener for fabUser
            fabUser.setOnClickListener(v -> {
                Intent intent = new Intent(MainActivity.this, AddingUserActivity.class);
                startActivity(intent);
            });

            // Set OnClickListener for fabVote
            fabVote.setOnClickListener(v -> {
                Intent intent = new Intent(MainActivity.this, AddingVoteActivity.class);
                startActivity(intent);
            });

            // Set OnClickListener for fabPolicy
            fabPolicy.setOnClickListener(v -> {
                Intent intent = new Intent(MainActivity.this, AddingPolicyActivity.class);
                startActivity(intent);
            });

            fabContact.setOnClickListener(v -> {
                Intent intent = new Intent(MainActivity.this, AddingContactsActivity.class);
                startActivity(intent);
            });
        }

        @Override
        public boolean onCreateOptionsMenu(Menu menu) {
            getMenuInflater().inflate(R.menu.main, menu);
            return true;
        }

        @Override
        public boolean onSupportNavigateUp() {
            NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_main);
            return NavigationUI.navigateUp(navController, mAppBarConfiguration) || super.onSupportNavigateUp();
        }

        @Override
        public boolean onOptionsItemSelected(@NonNull MenuItem item) {
            if (item.getItemId() == R.id.action_refresh) {
                // Call the method to refresh data
                refreshData();
                return true;
            }
            return super.onOptionsItemSelected(item);
        }

        public void refreshData() {
            // Implement your data refresh logic here
            Toast.makeText(this, "Refreshing data...", Toast.LENGTH_SHORT).show();

            // Example: Restarting the activity
            Intent intent = getIntent();
            finish();
            startActivity(intent);
        }
    }
