package com.android.stephen.mtgpos.model;

import java.io.Serializable;

/**
 * Created by Stephen Uy on 1/27/2017.
 */

public class LookUpModel implements Serializable{

    private String recID;
    private String storeID;
    private String itemID;
    private String itemDesc;
    private String qtyPerPack;
    private String productID;
    private String productDesc;
    private String sellingPrice;
    private String rebatePoints;
    private String sharePoints;
    private String picture;
    private String qtyPerServe;
    private String isActive;

    public String getRecID() {
        return recID;
    }

    public void setRecID(String recID) {
        this.recID = recID;
    }

    public String getStoreID() {
        return storeID;
    }

    public void setStoreID(String storeID) {
        this.storeID = storeID;
    }

    public String getProductID() {
        return productID;
    }

    public void setProductID(String productID) {
        this.productID = productID;
    }

    public String getProductDesc() {
        return productDesc;
    }

    public void setProductDesc(String productDesc) {
        this.productDesc = productDesc;
    }

    public String getSellingPrice() {
        return sellingPrice;
    }

    public void setSellingPrice(String sellingPrice) {
        this.sellingPrice = sellingPrice;
    }

    public String getRebatePoints() {
        return rebatePoints;
    }

    public void setRebatePoints(String rebatePoints) {
        this.rebatePoints = rebatePoints;
    }

    public String getSharePoints() {
        return sharePoints;
    }

    public void setSharePoints(String sharePoints) {
        this.sharePoints = sharePoints;
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }

    public String getIsActive() {
        return isActive;
    }

    public void setIsActive(String isActive) {
        this.isActive = isActive;
    }

    public String getItemID() {
        return itemID;
    }

    public void setItemID(String itemID) {
        this.itemID = itemID;
    }

    public String getItemDesc() {
        return itemDesc;
    }

    public void setItemDesc(String itemDesc) {
        this.itemDesc = itemDesc;
    }

    public String getQtyPerPack() {
        return qtyPerPack;
    }

    public void setQtyPerPack(String qtyPerPack) {
        this.qtyPerPack = qtyPerPack;
    }

    public String getQtyPerServe() {
        return qtyPerServe;
    }

    public void setQtyPerServe(String qtyPerServe) {
        this.qtyPerServe = qtyPerServe;
    }
}
