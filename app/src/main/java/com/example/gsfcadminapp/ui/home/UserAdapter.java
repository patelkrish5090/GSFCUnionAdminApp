package com.example.gsfcadminapp.ui.home;

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
import com.example.gsfcadminapp.User;
import com.example.gsfcadminapp.ui.announcements.Announcement;

import java.util.List;

public class UserAdapter extends RecyclerView.Adapter<UserAdapter.UserViewHolder> {

    private List<User> userList;
    private OnUserClickListener listener;

    public UserAdapter(List<User> userList, OnUserClickListener listener) {
        this.userList = userList;
        this.listener = listener;
    }

    @NonNull
    @Override
    public UserViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.user_item, parent, false);
        return new UserViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull UserViewHolder holder, int position) {
        User user = userList.get(position);
        holder.userName.setText(user.getName());
        holder.userId.setText(user.getempid());
        holder.userBlood.setText(user.getBlood());
        holder.userPhone.setText(user.getPhone());
        holder.userDesignation.setText(user.getDesignation());
        holder.userDepartment.setText(user.getDepartment());
        holder.userGrade.setText(user.getGrade());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener != null) {
                    listener.onUserClicked(user);
                }
            }
        });

        holder.deleteButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener != null) {
                    listener.onDeleteClicked(user, position);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return userList.size();
    }

    public static class UserViewHolder extends RecyclerView.ViewHolder {
        TextView userName, userId, userBlood, userPhone, userGrade, userDesignation, userDepartment;
        Button deleteButton;

        public UserViewHolder(@NonNull View itemView) {
            super(itemView);
            userName = itemView.findViewById(R.id.userName);
            userId = itemView.findViewById(R.id.userId);
            userBlood = itemView.findViewById(R.id.blood);
            userPhone = itemView.findViewById(R.id.phone);
            userGrade = itemView.findViewById(R.id.grade);
            userDesignation = itemView.findViewById(R.id.designation);
            userDepartment = itemView.findViewById(R.id.department);
            deleteButton = itemView.findViewById(R.id.deleteButton);
        }
    }

    public interface OnUserClickListener {
        void onUserClicked(User user);
        void onDeleteClicked(User user, int position);
        // void onDeleteClicked(User user); // If you want to handle delete clicks too
    }
}
