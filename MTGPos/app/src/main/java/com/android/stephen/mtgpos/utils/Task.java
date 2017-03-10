package com.android.stephen.mtgpos.utils;

public enum Task {
    STORE_DETAILS("getstoredetails"),
    ITEMS("getstoreitems"),
    PRODUCTS("getstoreproduct"),
    PRODUCT_ITEM("getstoreproductitem"),
    STOCKS("getstorestock"),
    ITEM_TYPE("getitemtypelookup"),
    UPOINTS_HISTORY("getupointshistory"),
    STORE_UPOINTS("getstoreupoints"),
    STOCKS_LIST("getlistofallstorestocks"),
    PRODUCT_LIST("getlistofallproducts"),
    PRODUCT_CATEGORY_LIST("getlistofallprocat"),
    STORE_STOCKS_LIST("getlistofallstorestocks"),
    SAVE_NEW_STOCK("postsavenewstock"),
    GENERATE_CUSTOMER_ID("generatecustomerid"),
    GET_UPLINE("getupline"),
    SAVE_NEW_CUSTOMER("savenewcustomer");

    private String value;

    public String getValue() {
        return value;
    }

    Task(String value) {
        this.value = value;
    }
}
