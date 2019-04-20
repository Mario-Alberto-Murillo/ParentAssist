package com.example.superpositionexample.tables;

public class Config {
    private Integer id;
    private Integer total;

    public Config(Integer id, Integer totalGamesToPlay) {
        this.id = id;
        this.total = totalGamesToPlay;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getTotalGamesToPlay() {
        return total;
    }

    public void setTotalGamesToPlay(Integer totalGamesToPlay) {
        this.total = totalGamesToPlay;
    }
}
