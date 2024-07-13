package com.example.gsfcadminapp.ui.vote;

import java.util.Map;

public class Option {
    public String oid;
    public String value;
    public String count;
    public Map<String, Voter> voters;

    // Default constructor required for calls to DataSnapshot.getValue(Option.class)
    public Option() {}

    public Option(String oid, String value, String count, Map<String, Voter> voters) {
        this.oid = oid;
        this.value = value;
        this.count = count;
        this.voters = voters;
    }
}
