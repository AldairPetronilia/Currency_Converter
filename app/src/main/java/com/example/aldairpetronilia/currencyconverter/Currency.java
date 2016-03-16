package com.example.aldairpetronilia.currencyconverter;

/**
 * Created by aldairpetronilia on 16/03/2016.
 */

public class Currency {

    private String currencyCode;
    private String currencyName;
    private double rate;
    private String currencySymbol;

    public Currency(String currencyCode, String currencyName) {
        this.currencyCode = currencyCode;
        this.currencyName = currencyName;
    }

    public Currency( String currencyCode, String currencyName, double rate) {
        this.currencyName = currencyName;
        this.currencyCode = currencyCode;
        this.rate = rate;
    }

    @Override
    public String toString() {
        return "Currency{" +
                "currencyCode='" + currencyCode + '\'' +
                ", currencyName='" + currencyName + '\'' +
                ", rate=" + rate +
                ", currencySymbol='" + currencySymbol + '\'' +
                '}';
    }

    public String getCurrencyCode() {
        return currencyCode;
    }

    public void setCurrencyCode(String currencyCode) {
        this.currencyCode = currencyCode;
    }

    public String getCurrencyName() {
        return currencyName;
    }

    public void setCurrencyName(String currencyName) {
        this.currencyName = currencyName;
    }

    public double getRate() {
        return rate;
    }

    public void setRate(double rate) {
        this.rate = rate;
    }

    public String getCurrencySymbol() {
        return currencySymbol;
    }

    public void setCurrencySymbol(String currencySymbol) {
        this.currencySymbol = currencySymbol;
    }

    public String getSpinnerText(){

        return "(" + currencyCode + ") " + currencyName ;
    }


}
