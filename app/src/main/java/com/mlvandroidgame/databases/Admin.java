package com.mlvandroidgame.databases;

/**
 * Created by Zacharie BARBECOT.
 */
public class Admin {

    private long id;
    private String imei;

    public Admin(long id, String imei) {
        this.id = id;
        this.imei = imei;
    }

    public Admin(String imei) {
        this.imei = imei;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getImei() {
        return imei;
    }

    public void setImei(String imei) {
        this.imei = imei;
    }
}
