package com.example.gsfcadminapp.ui.vote;

public class Voter {
    public String empid;

    // Default constructor required for calls to DataSnapshot.getValue(Voter.class)
    public Voter() {}

    public Voter(String empid) {
        this.empid = empid;
    }
}
