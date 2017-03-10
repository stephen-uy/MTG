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
    private String catID;
    private String catDesc;
    private String qtyPerItemType;
    private String type;
    private String itemType;
    private String description;
    private String dateLastUpdated;
    private String noOfServing;
    private String quantity;

    public LookUpModel (){

    }

    public LookUpModel (String type, String description, String isActive){
        this.type = type;
        this.description = description;
        this.isActive = isActive;
    }

    public LookUpModel (String catID, String catDesc, String isActive, String extra){
        this.catID = catID;
        this.catDesc = catDesc;
        this.isActive = isActive;
    }

    public LookUpModel (String storeID, String itemID, String itemDesc, String qtyPerItemType, String itemType, String isActive,
                        String dateLastUpdated){
        this.storeID = storeID;
        this.itemID = itemID;
        this.itemDesc = itemDesc;
        this.qtyPerItemType = qtyPerItemType;
        this.itemType = itemType;
        this.isActive = isActive;
        this.dateLastUpdated = dateLastUpdated;
    }

    public LookUpModel (String storeID, String productID, String itemID, String qtyPerServe, String isActive){
        this.storeID = storeID;
        this.productID = productID;
        this.itemID = itemID;
        this.qtyPerServe = qtyPerServe;
        this.isActive = isActive;
    }

    public LookUpModel (String storeID, String productID, String productDesc, String sellingPrice, String rebatePoints,
                        String sharePoints, String picture, String noOfServing, String isActive){
        this.storeID = storeID;
        this.productID = productID;
        this.productDesc = productDesc;
        this.sellingPrice = sellingPrice;
        this.rebatePoints = rebatePoints;
        this.sharePoints = sharePoints;
        this.picture = picture;
        this.noOfServing = noOfServing;
        this.isActive = isActive;
    }

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

    public String getCatID() {
        return catID;
    }

    public void setCatID(String catID) {
        this.catID = catID;
    }

    public String getCatDesc() {
        return catDesc;
    }

    public void setCatDesc(String catDesc) {
        this.catDesc = catDesc;
    }

    public String getQtyPerItemType() {
        return qtyPerItemType;
    }

    public void setQtyPerItemType(String qtyPerItemType) {
        this.qtyPerItemType = qtyPerItemType;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getItemType() {
        return itemType;
    }

    public void setItemType(String itemType) {
        this.itemType = itemType;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDateLastUpdated() {
        return dateLastUpdated;
    }

    public void setDateLastUpdated(String dateLastUpdated) {
        this.dateLastUpdated = dateLastUpdated;
    }

    public String getNoOfServing() {
        return noOfServing;
    }

    public void setNoOfServing(String noOfServing) {
        this.noOfServing = noOfServing;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }
}
