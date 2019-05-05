package com.example.superpositionexample.tables;

public class Config {
    private String id;
    private String total;
    private String time;
    private String user;

    public Config(String id, String totalGamesToPlay, String time, String user) {
        this.id = id;
        this.total = totalGamesToPlay;
        this.time=time;
        this.user=user;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTotal() {
        return total;
    }

    public void setTotal(String total) {
        this.total = total;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }
}
