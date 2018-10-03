package com.example.sunimali.petrays;



public class PetOwner{
    private String name;
    private String userName;
    private String password;
    private String address;
    private String Email;
    private String mobileNumber;
    private String petOwnerID;

    public PetOwner() {

    }

    public PetOwner(String name, String userName, String password, String address, String email, String mobileNumber, String petOwnerID) {
        this.setName(name);
        this.setUserName(userName);
        this.setPassword(password);
        this.setAddress(address);
        setEmail(email);
        this.setMobileNumber(mobileNumber);
        this.setPetOwnerID(petOwnerID);
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


    public void setName(String name) {
        this.name = name;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public void setMobileNumber(String mobileNumber) {
        this.mobileNumber = mobileNumber;
    }

    public void setPetOwnerID(String petOwnerID) {
        this.petOwnerID = petOwnerID;
    }
}
