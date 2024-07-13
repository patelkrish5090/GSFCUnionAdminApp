package com.example.gsfcadminapp;

import android.os.Parcel;
import android.os.Parcelable;

public class User implements Parcelable {
    private String empid;
    private String name;
    private String phone;
    private String grade;
    private String designation;
    private String department;
    private String blood;
    private String password;
    private String email;

    // Default constructor required for calls to DataSnapshot.getValue(User.class)
    public User() {
    }

    public User(String empid, String name, String phone, String grade, String designation, String department, String blood, String password, String email) {
        this.empid = empid;
        this.name = name;
        this.phone = phone;
        this.grade = grade;
        this.designation = designation;
        this.department = department;
        this.blood = blood;
        this.password = password;
        this.email = email;
    }

    // Parcelable constructor
    protected User(Parcel in) {
        empid = in.readString();
        name = in.readString();
        phone = in.readString();
        grade = in.readString();
        designation = in.readString();
        department = in.readString();
        blood = in.readString();
        password = in.readString();
        email = in.readString();
    }

    // Getters and setters
    public String getempid() {
        return empid;
    }

    public void setempid(String empid) {
        this.empid = empid;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getGrade() {
        return grade;
    }

    public void setGrade(String role) {
        this.grade = role;
    }

    public String getDesignation(){return designation;}
    public void setDesignation(String designation){this.designation = designation;}
    public String getDepartment(){return this.department;}
    public void setDepartment(String department){this.department = department;}
    public String getBlood(){return blood;}
    public void setBlood(String blood){this.blood = blood;}
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
    public String getEmail() {return email;}
    public void setEmail(String email){this.email = email;}

    // Parcelable methods
    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(empid);
        dest.writeString(name);
        dest.writeString(phone);
        dest.writeString(grade);
        dest.writeString(designation);
        dest.writeString(department);
        dest.writeString(blood);
        dest.writeString(password);
        dest.writeString(email);
    }

    public static final Creator<User> CREATOR = new Creator<User>() {
        @Override
        public User createFromParcel(Parcel in) {
            return new User(in);
        }

        @Override
        public User[] newArray(int size) {
            return new User[size];
        }
    };
}
