
package com.example.gsfcadminapp.ui.vote;

import com.example.gsfcadminapp.ui.vote.Option;

import java.util.Map;


public class Vote {
    public String pid;
    public String question;
    public String status;
    public String total_count;
    public Map<String, Option> options;

    // Default constructor required for calls to DataSnapshot.getValue(Vote.class)
    public Vote() {}

    public Vote(String pid, String question, String status, String total_count, Map<String, Option> options) {
        this.pid = pid;
        this.question = question;
        this.status = status;
        this.total_count = total_count;
        this.options = options;
    }
}
