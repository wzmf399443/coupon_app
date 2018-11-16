package com.example.user.coupon_app;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class Coupon_entity implements Serializable {

    @SerializedName("merchant")
    private String coupon_name;

    @SerializedName("couponAddress")
    private String couponAddress;

    @SerializedName("couponName")
    private String coupon_merchant;

    @SerializedName("value")
    private int coupon_value;

    @SerializedName("consumerValue")
    private int coupon_consumerValue;

    @SerializedName("limit")
    private int limit;

    @SerializedName("obtainValue")
    private int obtainValue;

    @SerializedName("status")
    private int status;

    @SerializedName("startDate")
    private String startDate;

    @SerializedName("obtainDate")
    private String obtainDate;

    @SerializedName("consumeDate")
    private String consumeDate;


    @SerializedName("endDate")
    private String expire_date;

    public Coupon_entity() {
    }

    public Coupon_entity(String coupon_name, String couponAddress,String coupon_merchant,
                         int coupon_value, int coupon_consumerValue, int limit, int obtainValue, int status,
                         String startDate, String obtainDate, String expire_date,String consumeDate) {
        this.setCoupon_name(coupon_name);
        this.setCouponAddress(couponAddress);
        this.setCoupon_merchant(coupon_merchant);
        this.setCoupon_value(coupon_value);
        this.setCoupon_consumerValue(coupon_consumerValue);
        this.setLimit(limit);
        this.setObtainValue(obtainValue);
        this.setStatus(status);
        this.setStartDate(startDate);
        this.setObtainDate(obtainDate);
        this.setExpire_date(expire_date);
        this.setConsumeDate(consumeDate);
    }

    @Override
    public String toString() {
        return "Coupon_entity{" +
                "coupon_name='" + coupon_name + '\'' +
                ", couponAddress='" + couponAddress + '\'' +
                ", coupon_merchant='" + coupon_merchant + '\'' +
                ", coupon_value=" + coupon_value +
                ", coupon_consumerValue=" + coupon_consumerValue +
                ", limit=" + limit +
                ", obtainValue=" + obtainValue +
                ", status=" + status +
                ", startDate='" + startDate + '\'' +
                ", obtainDate='" + obtainDate + '\'' +
                ", consumeDate='" + consumeDate + '\'' +
                ", expire_date='" + expire_date + '\'' +
                '}';
    }

    public String getCoupon_name() {
        return coupon_name;
    }

    public void setCoupon_name(String coupon_name) {
        this.coupon_name = coupon_name;
    }

    public String getCoupon_merchant() {
        return coupon_merchant;
    }

    public void setCoupon_merchant(String coupon_merchant) {
        this.coupon_merchant = coupon_merchant;
    }

    public int getCoupon_value() {
        return coupon_value;
    }

    public void setCoupon_value(int coupon_value) {
        this.coupon_value = coupon_value;
    }

    public int getCoupon_consumerValue() {
        return coupon_consumerValue;
    }

    public void setCoupon_consumerValue(int coupon_consumerValue) {
        this.coupon_consumerValue = coupon_consumerValue;
    }

    public int getLimit() {
        return limit;
    }

    public void setLimit(int limit) {
        this.limit = limit;
    }

    public int getObtainValue() {
        return obtainValue;
    }

    public void setObtainValue(int obtainValue) {
        this.obtainValue = obtainValue;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getObtainDate() {
        return obtainDate;
    }

    public void setObtainDate(String obtainDate) {
        this.obtainDate = obtainDate;
    }

    public String getExpire_date() {
        return expire_date;
    }

    public void setExpire_date(String expire_date) {
        this.expire_date = expire_date;
    }

    public String getCouponAddress() {
        return couponAddress;
    }

    public void setCouponAddress(String couponAddress) {
        this.couponAddress = couponAddress;
    }

    public String getConsumeDate() {
        return consumeDate;
    }

    public void setConsumeDate(String consumeDate) {
        this.consumeDate = consumeDate;
    }
}
