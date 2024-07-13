package com.example.gsfcadminapp.ui.contacts;

public class Contacts {
    private String name;
    private String phone;
    private String designation;

    public Contacts() {
        // Empty constructor needed for Firebase
    }

    public Contacts(String name, String phone, String designation) {
        this.name = name;
        this.phone = phone;
        this.designation = designation;
    }

    public String getName() {
        return name;
    }

    public String getPhone() {
        return phone;
    }

    public String getDesignation() {
        return designation;
    }
}

