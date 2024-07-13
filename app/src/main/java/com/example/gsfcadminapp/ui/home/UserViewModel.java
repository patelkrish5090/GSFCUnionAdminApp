package com.example.gsfcadminapp.ui.home;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.gsfcadminapp.User;

import java.util.List;

public class UserViewModel extends ViewModel {
    private final MutableLiveData<List<User>> users = new MutableLiveData<>();

    public void setUsers(List<User> userList) {
        users.setValue(userList);
    }

    public LiveData<List<User>> getUsers() {
        return users;
    }
}
