package com.example.gsfcadminapp.ui.contacts;

import static android.content.ContentValues.TAG;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.gsfcadminapp.R;
import com.example.gsfcadminapp.ui.policy.Policy;
import com.example.gsfcadminapp.ui.policy.PolicyAdapter;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class ContactsFragment extends Fragment {
    private RecyclerView recyclerView;
    private ContactsAdapter adapter;
    private List<Contacts> contacts;
    private DatabaseReference myRef;

    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_contacts, container, false);

        recyclerView = view.findViewById(R.id.recyclerview_contacts);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        contacts = new ArrayList<>();
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        myRef = database.getReference("contacts");
        adapter = new ContactsAdapter(contacts, myRef);
        recyclerView.setAdapter(adapter);

        fetchContacts();

        return view;

    }

    private void fetchContacts() {
        myRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                contacts.clear();
                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                    Contacts contact = snapshot.getValue(Contacts.class);
                    if (contact != null) {
                        contacts.add(contact);
                    } else {
                        Log.w(TAG, "Contact is null");
                    }
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(DatabaseError error) {
                Log.w(TAG, "Failed to read contacts", error.toException());
            }
        });
    }



}