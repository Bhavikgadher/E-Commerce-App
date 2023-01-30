package com.example.myapp;

public class WishlistModel {

    private String productId;
    private String productImage;
    private String productTitle;
    private long freeCoupens;
    private String rating;
    private long totalRating;
    private String productPrice;
    private String cuttedPrice;
    private boolean COD;
    private Class<Boolean> inStock;


    public WishlistModel(String productId, String productImage, String productTitle, long freeCoupens, String rating, long totalRating, String productPrice, String cuttedPrice, boolean COD, boolean inStock) {
        this.productId = productId;
        this.productImage = productImage;
        this.productTitle = productTitle;
        this.freeCoupens = freeCoupens;
        this.rating = rating;
        this.totalRating = totalRating;
        this.productPrice = productPrice;
        this.cuttedPrice = cuttedPrice;
        this.COD = COD;
    }

    public WishlistModel(String productId, String product_image_1, String product_title_1, Long freeCoupens, String rating, Long totalRating, String price, String cuttedPrice, Boolean cod, Object o, Class<Boolean> booleanClass) {
        this.productId = productId;
        this.productImage =  product_image_1;
        this.productTitle = product_title_1;
        this.freeCoupens = freeCoupens;
        this.rating = rating;
        this.totalRating = totalRating;
        this.productPrice = price;
        this.cuttedPrice = cuttedPrice;
        this.COD = cod;
        this.inStock = booleanClass;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public String getProductImage() {
        return productImage;
    }

    public void setProductImage(String productImage) {
        this.productImage = productImage;
    }

    public String getProductTitle() {
        return productTitle;
    }

    public void setProductTitle(String productTitle) {
        this.productTitle = productTitle;
    }

    public long getFreeCoupens() {
        return freeCoupens;
    }

    public void setFreeCoupens(long freeCoupens) {
        this.freeCoupens = freeCoupens;
    }

    public String getRating() {
        return rating;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

    public long getTotalRating() {
        return totalRating;
    }

    public void setTotalRating(long totalRating) {
        this.totalRating = totalRating;
    }

    public String getProductPrice() {
        return productPrice;
    }

    public void setProductPrice(String productPrice) {
        this.productPrice = productPrice;
    }

    public String getCuttedPrice() {
        return cuttedPrice;
    }

    public void setCuttedPrice(String cuttedPrice) {
        this.cuttedPrice = cuttedPrice;
    }

    public boolean isCOD() {
        return COD;
    }

    public void setCOD(boolean COD) {
        this.COD = COD;
    }

    public Class<Boolean> getInStock() {
        return inStock;
    }

    public void setInStock(Class<Boolean> inStock) {
        this.inStock = inStock;
    }

    public Class<Boolean> isInStock() {
        return inStock;
    }

    public boolean getCOD() {
        return isCOD();
    }
}
