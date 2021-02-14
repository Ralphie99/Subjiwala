package com.thedevelopers.subjiwala.org.Models;

import android.support.annotation.NonNull;

public class Accessory_item_model implements Comparable<Accessory_item_model>{

    String image;
    String accessory_name,description,manufacturer,product_date;//vendor_name;
    int price,accessory_id;//brand_id,vendor_id,stock;
    String ifgm;

    public Accessory_item_model()
    {
    }

    public Accessory_item_model(String image,int accessory_id, String accessory_name, int price, String description,String manufacturer,String product_date,String ifgm)//, int accessory_id, int vehicle_type, int brand_id, int vendor_id, String vendor_name, int stock)
    {
        this.image = image;
        this.accessory_name = accessory_name;
        this.price = price;
        this.description = description;
        this.accessory_id = accessory_id;
        this.manufacturer = manufacturer;
        this.product_date = product_date;
        this.accessory_id = accessory_id;
        this.ifgm = ifgm;
//        this.vehicle_type = vehicle_type;
//        this.brand_id = brand_id;
//        this.vendor_id = vendor_id;
//        this.vendor_name = vendor_name;
//        this.stock = stock;
    }

    public String isIfgm() {
        return ifgm;
    }

    public String getManufacturer() {
        return manufacturer;
    }

    public int getAccessory_id() {
        return accessory_id;
    }

    public String getImage() {
        return image;
    }

    public String getAccessory_name() {
        return accessory_name;
    }

    public int getPrice() {
        return price;
    }

    public String getDescription() {
        return description;
    }

    public String getProduct_date() {
        return product_date;
    }

    //
//    public int getAccessory_id() {
//        return accessory_id;
//    }
//
//    public int getVehicle_type() {
//        return vehicle_type;
//    }
//
//    public int getBrand_id() {
//        return brand_id;
//    }
//
//    public int getVendor_id() {
//        return vendor_id;
//    }
//
//    public String getVendor_name() {
//        return vendor_name;
//    }
//
//    public int getStock() {
//        return stock;
//    }

    @Override
    public int compareTo(@NonNull Accessory_item_model o) {
        if (price > o.price) {
            return 1;
        }
        else if (price <  o.price) {
            return -1;
        }
        else {
            return 0;
        }

    }

}
