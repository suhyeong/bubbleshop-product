package com.bubbleshop.product.interfaces.rest.controller;

public class ProductUrl {
    public static final String PRODUCT_DEFAULT_URL = "/product/v1";
    public static final String PRODUCTS = "/products";
    public static final String PRODUCT = PRODUCTS + "/{productId}";
    public static final String PRODUCT_IMAGE = PRODUCT + "/image";

    public static final String PRODUCT_TEMP_IMAGE = PRODUCTS + "/temp-image";
}
