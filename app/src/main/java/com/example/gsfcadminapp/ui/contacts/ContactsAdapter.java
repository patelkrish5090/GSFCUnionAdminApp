package com.example.gsfcadminapp.ui.contacts;

import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.gsfcadminapp.R;
import com.example.gsfcadminapp.ui.policy.Policy;
import com.example.gsfcadminapp.ui.policy.PolicyAdapter;
import com.google.firebase.database.DatabaseReference;

import java.util.List;

//public class ContactsAdapter extends RecyclerView.Adapter<ContactsAdapter.ContactsViewHolder>{
//
//    private List<Contacts> contacts;
//    private DatabaseReference myRef;
//
//    public ContactsAdapter(List<Contacts> contacts, DatabaseReference myRef) {
//        this.contacts = contacts;
//        this.myRef = myRef;
//    }
//
//    @NonNull
//    @Override
//    public ContactsAdapter.ContactsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
//        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.contacts_item,parent,false);
//        return new ContactsAdapter.ContactsViewHolder(view);
//    }
//
//    @Override
//    public void onBindViewHolder(@NonNull ContactsAdapter.ContactsViewHolder holder, int position) {
//        Contacts currentContact = contacts.get(position);
//        holder.name.setText(currentContact.getContactName());
//        holder.phone.setText(currentContact.getContactPhone());
//
//        holder.deleteButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                myRef.child(currentContact.getContactPhone()).removeValue().addOnCompleteListener(task -> {
//                    if (task.isSuccessful()) {
//                        contacts.remove(position);
//                        notifyItemRemoved(position);
//                        notifyItemRangeChanged(position, contacts.size());
//                    } else {
//                        // Handle the error
//                        Log.e("Firebase", "Failed to delete announcement", task.getException());
//                    }
//                });
//            }
//        });
//
//
//    }
//
//    @Override
//    public int getItemCount() {
//        return contacts.size();
//    }
//
//    static class ContactsViewHolder extends RecyclerView.ViewHolder {
//        public TextView name;
//        public TextView phone;
//        public Button deleteButton;
//
//
//        public ContactsViewHolder(@NonNull View itemView) {
//            super(itemView);
//            name = itemView.findViewById(R.id.personName);
//            phone = itemView.findViewById(R.id.personPhone);
//            deleteButton = itemView.findViewById(R.id.deleteButtonContact);;
//        }
//    }
//}

public class ContactsAdapter extends RecyclerView.Adapter<ContactsAdapter.ContactsViewHolder> {

    private List<Contacts> contacts;
    private DatabaseReference myRef;

    public ContactsAdapter(List<Contacts> contacts, DatabaseReference myRef) {
        this.contacts = contacts;
        this.myRef = myRef;
    }

    @NonNull
    @Override
    public ContactsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.contacts_item, parent, false);
        return new ContactsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ContactsViewHolder holder, int position) {
        Contacts currentContact = contacts.get(position);
        holder.name.setText(currentContact.getName());
        holder.phone.setText(currentContact.getPhone());

        holder.deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myRef.child(currentContact.getPhone()).removeValue().addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        contacts.remove(position);
                        notifyItemRemoved(position);
                        notifyItemRangeChanged(position, contacts.size());
                    } else {
                        // Handle the error
                        Log.e("Firebase", "Failed to delete contact", task.getException());
                    }
                });
            }
        });
    }


    @Override
    public int getItemCount() {
        return contacts.size();
    }

    static class ContactsViewHolder extends RecyclerView.ViewHolder {
        TextView name;
        TextView phone;
        Button deleteButton;

        ContactsViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.personName);
            phone = itemView.findViewById(R.id.personPhone);
            deleteButton = itemView.findViewById(R.id.deleteButtonContact);
        }
    }
}
