package com.example.wheel.model;

public class AccessibiliteRoute {
    private String id;
    private float ing;
    private float lat;
    private Boolean est_approvue;
    private long type_id;

    public AccessibiliteRoute() {
    }

    public AccessibiliteRoute(String id, float ing, float lat, Boolean est_approuve, long type_id) {
        this.id = id;
        this.ing = ing;
        this.lat = lat;
        this.est_approvue = est_approuve;
        this.type_id = type_id;
    }

    public AccessibiliteRoute(boolean est_approvue, float lat, float ing, Long type_id) {
        this.est_approvue = est_approvue;
        this.lat = lat;
        this.ing = ing;
        this.type_id = type_id;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public float getIng() {
        return ing;
    }

    public void setIng(float ing) {
        this.ing = ing;
    }

    public float getLat() {
        return lat;
    }

    public void setLat(float lat) {
        this.lat = lat;
    }

    public Boolean isEst_approvue() {
        return est_approvue;
    }

    public void setEst_approvue(Boolean est_approuve) {
        this.est_approvue = est_approuve;
    }

    public long getType_id() {
        return type_id;
    }

    public void setType_id(long type_id) {
        this.type_id = type_id;
    }

    @Override
    public String toString() {
        return "AccessibiliteRoute{" +
                "id='" + id + '\'' +
                ", ing=" + ing +
                ", lat=" + lat +
                ", est_approuve=" + est_approvue +
                ", type_id='" + type_id + '\'' +
                '}';
    }
}
