package com.android.stephen.mtgpos.model;

import java.io.Serializable;

/**
 * Created by Stephen Uy on 1/27/2017.
 */

public class CustomerModel implements Serializable{

    private String recID;
    private String customerID;
    private String upCustomerID;
    private String firstName;
    private String middleName;
    private String lastName;
    private String birthDate;
    private String emailAddress;
    private String mobileNumber;
    private String remarks;
    private String password;
    private String isStoreOwner;
    private String isActive;
    private String isUploaded;
    private String storeID;
    private String picture;
    private String dateCaptured;
    private String customerUpID1;
    private String customerUpID2;
    private String customerUpID3;
    private String customerUpID4;
    private String totalPoints;
    private String remainingPoints;
    private String withdrawPoints;
    private String purchaseReference;
    private String fromCustomerID;
    private String points;
    private String dateReceived;
    private String pointsType;

    public String getRecID() {
        return recID;
    }

    public void setRecID(String recID) {
        this.recID = recID;
    }

    public String getCustomerID() {
        return customerID;
    }

    public void setCustomerID(String customerID) {
        this.customerID = customerID;
    }

    public String getUpCustomerID() {
        return upCustomerID;
    }

    public void setUpCustomerID(String upCustomerID) {
        this.upCustomerID = upCustomerID;
    }

    public String getIsActive() {
        return isActive;
    }

    public void setIsActive(String isActive) {
        this.isActive = isActive;
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

    public String getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(String birthDate) {
        this.birthDate = birthDate;
    }

    public String getEmailAddress() {
        return emailAddress;
    }

    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }

    public String getMobileNumber() {
        return mobileNumber;
    }

    public void setMobileNumber(String mobileNumber) {
        this.mobileNumber = mobileNumber;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
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

    public String getCustomerUpID1() {
        return customerUpID1;
    }

    public void setCustomerUpID1(String customerUpID1) {
        this.customerUpID1 = customerUpID1;
    }

    public String getCustomerUpID2() {
        return customerUpID2;
    }

    public void setCustomerUpID2(String customerUpID2) {
        this.customerUpID2 = customerUpID2;
    }

    public String getCustomerUpID3() {
        return customerUpID3;
    }

    public void setCustomerUpID3(String customerUpID3) {
        this.customerUpID3 = customerUpID3;
    }

    public String getCustomerUpID4() {
        return customerUpID4;
    }

    public void setCustomerUpID4(String customerUpID4) {
        this.customerUpID4 = customerUpID4;
    }

    public String getPurchaseReference() {
        return purchaseReference;
    }

    public void setPurchaseReference(String purchaseReference) {
        this.purchaseReference = purchaseReference;
    }

    public String getIsStoreOwner() {
        return isStoreOwner;
    }

    public void setIsStoreOwner(String isStoreOwner) {
        this.isStoreOwner = isStoreOwner;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPicture() {
        return picture;
    }

    public void setPicture(String picture) {
        this.picture = picture;
    }

    public String getDateCaptured() {
        return dateCaptured;
    }

    public void setDateCaptured(String dateCaptured) {
        this.dateCaptured = dateCaptured;
    }

    public String getFromCustomerID() {
        return fromCustomerID;
    }

    public void setFromCustomerID(String fromCustomerID) {
        this.fromCustomerID = fromCustomerID;
    }

    public String getPoints() {
        return points;
    }

    public void setPoints(String points) {
        this.points = points;
    }

    public String getDateReceived() {
        return dateReceived;
    }

    public void setDateReceived(String dateReceived) {
        this.dateReceived = dateReceived;
    }

    public String getPointsType() {
        return pointsType;
    }

    public void setPointsType(String pointsType) {
        this.pointsType = pointsType;
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

    public String getWithdrawPoints() {
        return withdrawPoints;
    }

    public void setWithdrawPoints(String withdrawPoints) {
        this.withdrawPoints = withdrawPoints;
    }
}
