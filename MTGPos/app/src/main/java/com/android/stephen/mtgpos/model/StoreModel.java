package com.android.stephen.mtgpos.model;

import java.io.Serializable;

/**
 * Created by Stephen Uy on 1/31/2017.
 */

public class StoreModel implements Serializable{

    private String recID;
    private String storeID;
    private String storeName;
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
    private String pass;
    private String firstName;
    private String middleName;
    private String lastName;
    private String level;
    private String regBy;
    private String totalPoints;
    private String remainingPoints;
    private String totalWDPoints;
    private String pointsRef;
    private String modeOfPayment;
    private String amount;
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
    private String totalRPoints;
    private String totalSPoints;
    private String itemDesc;
    private String itemType;
    //new columns
    private String region;
    private String island;
    private String isMTGStore;
    private String appType;
    private String dateLastUpdated;
    private String saltPass;
    private String amountDeposited;
    private String uPoints;
    private String sPoints;
    private String rPoints;
    private String amtPurchased;
    private String isOwner;
    private String module;
    private String qtyPerItemType;
    private String oldTotalRemainingPoints;
    private String oldTotalPoints;
    private String newTotalRemainingPoints;
    private String newTotalPoints;
    private String isAddToStore;
    private String tranType;
    private String dateOfTransaction;

    public StoreModel (){

    }

    public StoreModel (String storeID, String itemID, String quantity, String remarks, String isActive){
        this.storeID = storeID;
        this.itemID = itemID;
        this.quantity = quantity;
        this.remarks = remarks;
        this.isActive = isActive;
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

    public StoreModel (String storeID, String itemID, String quantity, String itemDesc, String itemType,
                       String qtyPerItemType, String remarks, String isActive){
        this.storeID = storeID;
        this.itemID = itemID;
        this.quantity = quantity;
        this.itemDesc = itemDesc;
        this.itemType = itemType;
        this.qtyPerItemType = qtyPerItemType;
        this.remarks = remarks;
        this.isActive = isActive;
    }

    public StoreModel (String pointsRef, String amount, String uPoints, String dateOfTransaction, String oldTotalPoints,
                       String oldTotalRemainingPoints, String newTotalPoints, String newTotalRemainingPoints, String isActive){
        this.pointsRef = pointsRef;
        this.amount = amount;
        this.uPoints = uPoints;
        this.dateOfTransaction = dateOfTransaction;
        this.oldTotalPoints = oldTotalPoints;
        this.oldTotalRemainingPoints = oldTotalRemainingPoints;
        this.newTotalPoints = newTotalPoints;
        this.newTotalRemainingPoints = newTotalRemainingPoints;
        this.isActive = isActive;
    }

    public String getRecID() {
        return recID;
    }

    public void setRecID(String recID) {
        this.recID = recID;
    }

    public String getStoreName() {
        return storeName;
    }

    public void setStoreName(String storeName) {
        this.storeName = storeName;
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

    public String getPass() {
        return pass;
    }

    public void setPass(String pass) {
        this.pass = pass;
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

    public String getTotalRPoints() {
        return totalRPoints;
    }

    public void setTotalRPoints(String totalRPoints) {
        this.totalRPoints = totalRPoints;
    }

    public String getTotalSPoints() {
        return totalSPoints;
    }

    public void setTotalSPoints(String totalSPoints) {
        this.totalSPoints = totalSPoints;
    }

    public String getItemDesc() {
        return itemDesc;
    }

    public void setItemDesc(String itemDesc) {
        this.itemDesc = itemDesc;
    }

    public String getAppType() {
        return appType;
    }

    public void setAppType(String appType) {
        this.appType = appType;
    }

    public String getDateLastUpdated() {
        return dateLastUpdated;
    }

    public void setDateLastUpdated(String dateLastUpdated) {
        this.dateLastUpdated = dateLastUpdated;
    }

    public String getSaltPass() {
        return saltPass;
    }

    public void setSaltPass(String saltPass) {
        this.saltPass = saltPass;
    }

    public String getAmountDeposited() {
        return amountDeposited;
    }

    public void setAmountDeposited(String amountDeposited) {
        this.amountDeposited = amountDeposited;
    }

    public String getuPoints() {
        return uPoints;
    }

    public void setuPoints(String uPoints) {
        this.uPoints = uPoints;
    }

    public String getsPoints() {
        return sPoints;
    }

    public void setsPoints(String sPoints) {
        this.sPoints = sPoints;
    }

    public String getrPoints() {
        return rPoints;
    }

    public void setrPoints(String rPoints) {
        this.rPoints = rPoints;
    }

    public String getAmtPurchased() {
        return amtPurchased;
    }

    public void setAmtPurchased(String amtPurchased) {
        this.amtPurchased = amtPurchased;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public String getIsland() {
        return island;
    }

    public void setIsland(String island) {
        this.island = island;
    }

    public String getIsMTGStore() {
        return isMTGStore;
    }

    public void setIsMTGStore(String isMTGStore) {
        this.isMTGStore = isMTGStore;
    }

    public String getIsOwner() {
        return isOwner;
    }

    public void setIsOwner(String isOwner) {
        this.isOwner = isOwner;
    }

    public String getModule() {
        return module;
    }

    public void setModule(String module) {
        this.module = module;
    }

    public String getItemType() {
        return itemType;
    }

    public void setItemType(String itemType) {
        this.itemType = itemType;
    }

    public String getQtyPerItemType() {
        return qtyPerItemType;
    }

    public void setQtyPerItemType(String qtyPerItemType) {
        this.qtyPerItemType = qtyPerItemType;
    }

    public String getOldTotalRemainingPoints() {
        return oldTotalRemainingPoints;
    }

    public void setOldTotalRemainingPoints(String oldTotalRemainingPoints) {
        this.oldTotalRemainingPoints = oldTotalRemainingPoints;
    }

    public String getOldTotalPoints() {
        return oldTotalPoints;
    }

    public void setOldTotalPoints(String oldTotalPoints) {
        this.oldTotalPoints = oldTotalPoints;
    }

    public String getNewTotalRemainingPoints() {
        return newTotalRemainingPoints;
    }

    public void setNewTotalRemainingPoints(String newTotalRemainingPoints) {
        this.newTotalRemainingPoints = newTotalRemainingPoints;
    }

    public String getNewTotalPoints() {
        return newTotalPoints;
    }

    public void setNewTotalPoints(String newTotalPoints) {
        this.newTotalPoints = newTotalPoints;
    }

    public String getIsAddToStore() {
        return isAddToStore;
    }

    public void setIsAddToStore(String isAddToStore) {
        this.isAddToStore = isAddToStore;
    }

    public String getTranType() {
        return tranType;
    }

    public void setTranType(String tranType) {
        this.tranType = tranType;
    }

    public String getDateOfTransaction() {
        return dateOfTransaction;
    }

    public void setDateOfTransaction(String dateOfTransaction) {
        this.dateOfTransaction = dateOfTransaction;
    }
}
