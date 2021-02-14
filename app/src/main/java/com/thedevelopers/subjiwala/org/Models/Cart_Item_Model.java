package com.thedevelopers.subjiwala.org.Models;

public class Cart_Item_Model {

    String image,product_name,ifgm;
    int product_price,qty,product_id;

    public Cart_Item_Model(String image, String product_name, int product_price,int qty,int product_id,String ifgm) {
        this.image = image;
        this.product_name = product_name;
        this.product_price = product_price;
        this.qty = qty;
        this.product_id = product_id;
        this.ifgm = ifgm;
    }

    public String getImage() {
        return image;
    }

    public String getProduct_name() {
        return product_name;
    }

    public int getProduct_price() {
        return product_price;
    }

    public int getQty() {
        return qty;
    }

    public int getProduct_id() {
        return product_id;
    }

    public void setQty(int qty) {
        this.qty = qty;
    }

    public String getIfgm() {
        return ifgm;
    }
}
