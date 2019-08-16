package com.example.furni.Model;

public class User {
    private String email;
    private String uid;
    private String password;
    private float totalSpend;
    private float spentPoint;

    public User(String email,String uid,String password){
        this.email = email;
        this.uid = uid;
        this.password = password;
        this.totalSpend = 0;
        this.spentPoint = 0;
    }

    public User(String email, String uid, String password, float totalSpend, float spentPoint) {
        this.email = email;
        this.uid = uid;
        this.password = password;
        this.totalSpend = totalSpend;
        this.spentPoint = spentPoint;
    }

    public User(User u)
    {
        this.email=u.email;
        this.uid=u.uid;
        this.password=u.password;
        this.totalSpend=u.totalSpend;
        this.spentPoint=u.spentPoint;
    }

    public void setUser(String email, String uid, String password){
        this.email=email;
        this.uid=uid;
        this.password=password;
        this.totalSpend=0;
        this.spentPoint=0;
    }

    public void setUser(String email, String uid, String password, float totalSpend, float spentPoint){
        this.email=email;
        this.uid=uid;
        this.password=password;
        this.totalSpend=totalSpend;
        this.spentPoint=spentPoint;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public float getTotalSpend() {
        return totalSpend;
    }

    public void setTotalSpend(float totalSpend) {
        this.totalSpend = totalSpend;
    }

    public float getSpentPoint() {
        return spentPoint;
    }

    public void setSpentPoint(float spentPoint) {
        this.spentPoint = spentPoint;
    }
}
