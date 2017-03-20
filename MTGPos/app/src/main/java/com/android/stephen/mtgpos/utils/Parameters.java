package com.android.stephen.mtgpos.utils;

public enum Parameters {
    USERNAME("username"),
    UserName("userName"),
    PASSWORD("password"),
    MAC_ADDRESS("macaddress"),
    FROM("from"),
    TO("to"),
    ITEM_ID("itemid"),
    PRODUCT_ID("productid"),
    CATEGORY_ID("categoryid"),
    ITEM_EN("itemEn"),
    CUSTOMER_ID_TEMP("customeridtemp"),
    CUSTOMER_ID("customerid"),
    CUSTOMER_ID_UP("custidup"),
    CUSTOMER_EN("customerenreg"),
    CUSTOMER_PICTURE_EN("customerpictureenreg"),
    CUSTOMER_UPLINE_EN("customeruplineenreg"),
    Stocks_Ref("StockRef"),
    Item_ID("ItemID"),
    Quantity("Quantity"),
    Old_Quantity("OldQuantity"),
    Total_Quantity("TotalQuantity"),
    Date_Delivered("DateDelivered"),
    Reg_By("RegBy"),
    response("response"),
    STORE_ID("storeid");

    private String value;

    public String getValue() {
        return value;
    }

    Parameters(String value) {
        this.value = value;
    }
}
