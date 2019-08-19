package com.example.furni.Model;

public class Shop {
    private String Address;
    private String Phone;
    private String Longitude;
    private String Latitude;

    public Shop(){

    }

    public Shop(String address, String phone,String latitude,String longitude) {
        Address = address;
        Phone = phone;
        Longitude=longitude;
        Latitude=latitude;
    }

    public String getAddress() {
        return Address;
    }

    public void setAddress(String address) {
        Address = address;
    }

    public String getPhone() {
        return Phone;
    }

    public void setPhone(String phone) {
        Phone = phone;
    }

    public String getLongitude() {
        return Longitude;
    }

    public void setLongitude(String longitude) {
        Longitude = longitude;
    }

    public String getLatitude() {
        return Latitude;
    }

    public void setLatitude(String latitude) {
        Latitude = latitude;
    }
}
