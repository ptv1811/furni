package com.example.furni.Model;

public class Product {
    private String Name;
    private String Description;
    private String Price;
    private String Quantity;
    private String Image;
    private String Sfa;
    private String Sfb;

    public String getSfa() {
        return Sfa;
    }

    public void setSfa(String sfa) {
        Sfa = sfa;
    }

    public String getSfb() {
        return Sfb;
    }

    public void setSfb(String sfb) {
        Sfb = sfb;
    }

    public Product(){

    }

    public Product(String name,String description ,String price, String quantity, String image, String sfa, String sfb) {
        Name = name;
        Description=description;
        Price = price;
        Quantity = quantity;
        Image = image;
        Sfa = sfa;
        this.Sfb = sfb;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public String getPrice() {
        return Price;
    }

    public void setPrice(String price) {
        Price = price;
    }

    public String getQuantity() {
        return Quantity;
    }

    public void setQuantity(String quantity) {
        Quantity = quantity;
    }

    public String getImage() {
        return Image;
    }

    public void setImage(String image) {
        Image = image;
    }


}
