package com.example.gsfcadminapp.ui.policy;

import static android.content.ContentValues.TAG;

import android.os.Bundle;
import android.provider.ContactsContract;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.gsfcadminapp.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.database.core.Tag;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class PolicyFragment extends Fragment {

    private RecyclerView recyclerView;
    private PolicyAdapter adapter;
    private List<Policy> policy;
    private DatabaseReference myRef;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_policy, container, false);

        recyclerView = view.findViewById(R.id.recycler_view_policy);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        policy = new ArrayList<>();
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        myRef = database.getReference("policy");
        adapter = new PolicyAdapter(policy, myRef);
        recyclerView.setAdapter(adapter);

        fetchPolicy();

        return view;

    }

    private void fetchPolicy() {
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                policy.clear();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    Policy policy1 = snapshot.getValue(Policy.class);
                    if (policy1 != null) {
                        policy.add(policy1);
                    } else {
                        Log.w(TAG, "Policy is null");
                    }
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(DatabaseError error){
                Log.w(TAG, "Policy to read value.", error.toException());
            }
        });
    }
}