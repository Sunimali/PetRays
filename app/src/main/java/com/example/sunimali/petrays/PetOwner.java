package com.example.sunimali.petrays;



public class PetOwner{
    private String name;
    private String userName;
    private String password;
    private String address;
    private String Email;
    private String mobileNumber;
    private String petOwnerID;

    public PetOwner(String name, String userName, String password, String address, String email, String mobileNumber, String petOwnerID) {
        this.name = name;
        this.userName = userName;
        this.password = password;
        this.address = address;
        Email = email;
        this.mobileNumber = mobileNumber;
        this.petOwnerID = petOwnerID;
    }

    public String getName() {
        return name;

    }


    public String getUserName() {
        return userName;
    }


    public String getPassword() {
        return password;
    }


    public String getAddress() {
        return address;
    }


    public String getEmail() {
        return Email;
    }


    public String getMobileNumber() {
        return mobileNumber;
    }


    public String getPetOwnerID() {
        return petOwnerID;
    }


}
