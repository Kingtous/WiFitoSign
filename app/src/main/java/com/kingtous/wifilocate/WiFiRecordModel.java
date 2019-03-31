package com.kingtous.wifilocate;

public class WiFiRecordModel {

    private String WifiName;
    private String bssid;
    private String date;
    private String position;

    public String getWifiName() {
        return WifiName;
    }

    public void setWifiName(String wifiName) {
        WifiName = wifiName;
    }

    public String getBssid() {
        return bssid;
    }

    public void setBssid(String bssid) {
        this.bssid = bssid;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }
}
