package com.miguelarc.book_store_app.models;

public class SaleInfo {
    private String country;
    private String saleability;
    private boolean isEbook;

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getSaleability() {
        return saleability;
    }

    public void setSaleability(String saleability) {
        this.saleability = saleability;
    }

    public boolean isIsEbook() {
        return isEbook;
    }

    public void setIsEbook(boolean isEbook) {
        this.isEbook = isEbook;
    }
}
