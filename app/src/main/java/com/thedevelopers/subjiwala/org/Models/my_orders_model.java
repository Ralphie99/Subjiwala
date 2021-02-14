package com.thedevelopers.subjiwala.org.Models;

public class my_orders_model {

    int order_id,product_id,product_price;
    String product_name,order_date,image,qty;


    public my_orders_model(int order_id, int product_id, String qty, int product_price, String product_name, String order_date,String image) {
        this.order_id = order_id;
        this.product_id = product_id;
        this.qty = qty;
        this.product_price = product_price;
        this.product_name = product_name;
        this.order_date = order_date;
        this.image = image;
    }

    public String getImage() {
        return image;
    }

    public int getOrder_id() {
        return order_id;
    }

    public int getProduct_id() {
        return product_id;
    }

    public String getQty() {
        return qty;
    }

    public int getProduct_price() {
        return product_price;
    }

    public String getProduct_name() {
        return product_name;
    }

    public String getOrder_date() {
        return order_date;
    }
}
