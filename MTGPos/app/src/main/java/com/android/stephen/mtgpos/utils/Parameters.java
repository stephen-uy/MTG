package com.android.stephen.mtgpos.utils;

public enum Parameters {
    USERNAME("username"),
    PASSWORD("password"),
    MAC_ADDRESS("macaddress"),
    FROM("from"),
    TO("to"),
    ITEM_ID("itemid"),
    PRODUCT_ID("productid"),
    CATEGORY_ID("categoryid"),
    STOCK_REG_EN("stockregen"),
    CUSTOMER_ID_TEMP("customeridtemp"),
    CUSTOMER_ID("customerid"),
    CUSTOMER_ID_UP("custidup"),
    CUSTOMER_EN("customeren"),
    CUSTOMER_PICTURE_EN("customerpictureen"),
    CUSTOMER_UPLINE_EN("customeruplineen"),
    STORE_ID("storeid");

    private String value;

    public String getValue() {
        return value;
    }

    Parameters(String value) {
        this.value = value;
    }
}
