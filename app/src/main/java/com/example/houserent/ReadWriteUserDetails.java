package com.example.houserent;

public class ReadWriteUserDetails {
    public String fullName;
    public String mobile;
    public String gender;

    public int rent;
    public int electricityBill;

    public int totalBill;

    public ReadWriteUserDetails(String fullName,String mobile, String textGender, int rent, int electricityBill, int totalBill){
        this.fullName = fullName;
        this.mobile = mobile;
        this.gender = textGender;
        this.rent = rent;
        this.electricityBill = electricityBill;
        this.totalBill = totalBill;
    }

}
