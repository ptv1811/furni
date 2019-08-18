package com.example.furni.Model;

public class Cart {
    private String orderID;
    private String productAmount;
    private String productId,productImage,productName,productPrice;

    public Cart(){

    }

    public Cart(String orderID, String productAmount, String productId, String productImage, String productName, String productPrice) {
        this.orderID = orderID;
        this.productAmount = productAmount;
        this.productId = productId;
        this.productImage = productImage;
        this.productName = productName;
        this.productPrice = productPrice;
    }

    public String getOrderID() {
        return orderID;
    }

    public void setOrderID(String orderID) {
        this.orderID = orderID;
    }

    public String getProductAmount() {
        return productAmount;
    }

    public void setProductAmount(String productAmout) {
        this.productAmount = productAmout;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productID) {
        this.productId = productID;
    }

    public String getProductImage() {
        return productImage;
    }

    public void setProductImage(String productImage) {
        this.productImage = productImage;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getProductPrice() {
        return productPrice;
    }

    public void setProductPrice(String productPrice) {
        this.productPrice = productPrice;
    }
}
