package com.android.stephen.mtgpos.model;

import java.io.Serializable;

/**
 * Created by Stephen Uy on 1/31/2017.
 */

public class StoreModel implements Serializable{

    private String recID;
    private String storeID;
    private String franchiseeName;
    private String street;
    private String city;
    private String municipality;
    private String province;
    private String zipCode;
    private String longitude;
    private String latitude;
    private String emailAddress;
    private String mobileNumber;
    private String phone;
    private String remarks;
    private String isActive;
    private String isUploaded;
    private String userName;
    private String password;
    private String firstName;
    private String middleName;
    private String lastName;
    private String level;
    private String regBy;
    private String regDate;
    private String totalPoints;
    private String remainingPoints;
    private String totalWDPoints;
    private String pointsRef;
    private String modeOfPayment;
    private String amount;
    private String points;
    private String dateDeposited;
    private String receivedDate;
    private String stocksRef;
    private String productID;
    private String quantity;
    private String dateReg;
    private String auditDate;
    private String auditDesc;
    private String purchaseRef;
    private String customerID;
    private String purchaseDate;
    private String totalAmt;
    private String totalQty;
    private String itemID;
    private String rebatePoints;
    private String sharePoints;
    private String itemDesc;

    public StoreModel (){

    }

    public StoreModel (String storeID, String itemID, String quantity, String remarks, String isActive, String isUploaded){
        this.storeID = storeID;
        this.itemID = itemID;
        this.quantity = quantity;
        this.remarks = remarks;
        this.isActive = isActive;
        this.isUploaded = isUploaded;
    }

    public StoreModel (String storeID, String stocksRef, String itemID, String quantity, String dateReg, String remarks, String isActive){
        this.storeID = storeID;
        this.stocksRef = stocksRef;
        this.itemID = itemID;
        this.quantity = quantity;
        this.dateReg = dateReg;
        this.remarks = remarks;
        this.isActive = isActive;
    }

    public String getRecID() {
        return recID;
    }

    public void setRecID(String recID) {
        this.recID = recID;
    }

    public String getFranchiseeName() {
        return franchiseeName;
    }

    public void setFranchiseeName(String franchiseeName) {
        this.franchiseeName = franchiseeName;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getIsActive() {
        return isActive;
    }

    public void setIsActive(String isActive) {
        this.isActive = isActive;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getMunicipality() {
        return municipality;
    }

    public void setMunicipality(String municipality) {
        this.municipality = municipality;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getZipCode() {
        return zipCode;
    }

    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getEmailAddress() {
        return emailAddress;
    }

    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }

    public String getIsUploaded() {
        return isUploaded;
    }

    public void setIsUploaded(String isUploaded) {
        this.isUploaded = isUploaded;
    }

    public String getStoreID() {
        return storeID;
    }

    public void setStoreID(String storeID) {
        this.storeID = storeID;
    }

    public String getMobileNumber() {
        return mobileNumber;
    }

    public void setMobileNumber(String mobileNumber) {
        this.mobileNumber = mobileNumber;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getMiddleName() {
        return middleName;
    }

    public void setMiddleName(String middleName) {
        this.middleName = middleName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public String getRegBy() {
        return regBy;
    }

    public void setRegBy(String regBy) {
        this.regBy = regBy;
    }

    public String getRegDate() {
        return regDate;
    }

    public void setRegDate(String regDate) {
        this.regDate = regDate;
    }

    public String getPointsRef() {
        return pointsRef;
    }

    public void setPointsRef(String pointsRef) {
        this.pointsRef = pointsRef;
    }

    public String getModeOfPayment() {
        return modeOfPayment;
    }

    public void setModeOfPayment(String modeOfPayment) {
        this.modeOfPayment = modeOfPayment;
    }

    public String getPoints() {
        return points;
    }

    public void setPoints(String points) {
        this.points = points;
    }

    public String getDateDeposited() {
        return dateDeposited;
    }

    public void setDateDeposited(String dateDeposited) {
        this.dateDeposited = dateDeposited;
    }

    public String getStocksRef() {
        return stocksRef;
    }

    public void setStocksRef(String stocksRef) {
        this.stocksRef = stocksRef;
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity;
    }

    public String getDateReg() {
        return dateReg;
    }

    public void setDateReg(String dateReg) {
        this.dateReg = dateReg;
    }

    public String getCustomerID() {
        return customerID;
    }

    public void setCustomerID(String customerID) {
        this.customerID = customerID;
    }

    public String getReceivedDate() {
        return receivedDate;
    }

    public void setReceivedDate(String receivedDate) {
        this.receivedDate = receivedDate;
    }

    public String getTotalQty() {
        return totalQty;
    }

    public void setTotalQty(String totalQty) {
        this.totalQty = totalQty;
    }

    public String getAuditDate() {
        return auditDate;
    }

    public void setAuditDate(String auditDate) {
        this.auditDate = auditDate;
    }

    public String getAuditDesc() {
        return auditDesc;
    }

    public void setAuditDesc(String auditDesc) {
        this.auditDesc = auditDesc;
    }

    public String getPurchaseRef() {
        return purchaseRef;
    }

    public void setPurchaseRef(String purchaseRef) {
        this.purchaseRef = purchaseRef;
    }

    public String getPurchaseDate() {
        return purchaseDate;
    }

    public void setPurchaseDate(String purchaseDate) {
        this.purchaseDate = purchaseDate;
    }

    public String getTotalAmt() {
        return totalAmt;
    }

    public void setTotalAmt(String totalAmt) {
        this.totalAmt = totalAmt;
    }

    public String getProductID() {
        return productID;
    }

    public void setProductID(String productID) {
        this.productID = productID;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getTotalPoints() {
        return totalPoints;
    }

    public void setTotalPoints(String totalPoints) {
        this.totalPoints = totalPoints;
    }

    public String getRemainingPoints() {
        return remainingPoints;
    }

    public void setRemainingPoints(String remainingPoints) {
        this.remainingPoints = remainingPoints;
    }

    public String getTotalWDPoints() {
        return totalWDPoints;
    }

    public void setTotalWDPoints(String totalWDPoints) {
        this.totalWDPoints = totalWDPoints;
    }

    public String getItemID() {
        return itemID;
    }

    public void setItemID(String itemID) {
        this.itemID = itemID;
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

    public String getItemDesc() {
        return itemDesc;
    }

    public void setItemDesc(String itemDesc) {
        this.itemDesc = itemDesc;
    }
}
