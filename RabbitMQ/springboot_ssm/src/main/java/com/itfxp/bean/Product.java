package com.itfxp.bean;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Product {
    private String id;
    private String productNum;
    private String productName;
    private String cityName;
    private Date departureTime;
    private String departureTimeStr;
    private Integer productPrice;
    private String productDesc;
    private Integer productStatus;
    private String productStatusStr;

    public Product() {
    }

    public Product(String id, String productNum, String productName, String cityName, Date departureTime, String departureTimeStr, Integer productPrice, String productDesc, Integer productStatus, String productStatusStr) {
        this.id = id;
        this.productNum = productNum;
        this.productName = productName;
        this.cityName = cityName;
        this.departureTime = departureTime;

        this.productPrice = productPrice;
        this.productDesc = productDesc;
        this.productStatus = productStatus;

    }

    /*public String getDepartureTimeStr() {
        return departureTimeStr;
    }

    public String getProductStatusStr() {
        return productStatusStr;
    }*/

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getProductNum() {
        return productNum;
    }

    public void setProductNum(String productNum) {
        this.productNum = productNum;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getCityName() {
        return cityName;
    }

    public void setCityName(String cityName) {
        this.cityName = cityName;
    }

    public Date getDepartureTime() {
        return departureTime;
    }

    public void setDepartureTime(Date departureTime) {
        this.departureTime = departureTime;
    }

    public void setDepartureTimeStr(String departureTimeStr) {
        this.departureTimeStr = departureTimeStr;
    }

    public Integer getProductPrice() {
        return productPrice;
    }

    public void setProductPrice(Integer productPrice) {
        this.productPrice = productPrice;
    }

    public String getProductDesc() {
        return productDesc;
    }

    public void setProductDesc(String productDesc) {
        this.productDesc = productDesc;
    }

    public Integer getProductStatus() {
        return productStatus;
    }

    public void setProductStatus(Integer productStatus) {
        this.productStatus = productStatus;
    }

    public void setProductStatusStr(String productStatusStr) {
        this.productStatusStr = productStatusStr;
    }


        public String getDepartureTimeStr() {
            // 对日期格式化
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

                departureTimeStr = dateFormat.format(departureTime);

            return departureTimeStr;
        }


    public String getProductStatusStr() {
        if (productStatus == null) {
            return  null;
        }

        if (productStatus == 0){
            productStatusStr = "关闭";
        } else if(productStatus == 1){
            productStatusStr = "开启";
        }

        return productStatusStr;
    }

    @Override
    public String toString() {
        return "Product{" +
                "id='" + id + '\'' +
                ", productNum='" + productNum + '\'' +
                ", productName='" + productName + '\'' +
                ", cityName='" + cityName + '\'' +
                ", departureTime=" + departureTime +
                  '\'' +
                ", productPrice=" + productPrice +
                ", productDesc='" + productDesc + '\'' +
                ", productStatus=" + productStatus
                 + '\'' +
                '}';
    }
}