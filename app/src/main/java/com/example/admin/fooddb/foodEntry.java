package com.example.admin.fooddb;

/**
 * Created by Admin on 8/7/2017.
 */

public class foodEntry {
    String name;
    String calories;
    String fat;
    String protein;
    byte [] picture;

    public foodEntry(String name, String calories, String fat, String protein, byte[] picture){
        this.name = name;
        this.calories = calories;
        this.fat = fat;
        this.protein = protein;
        this.picture = picture;
    }

    public byte[] getPicture() {
        return picture;
    }

    public void setPicture(byte[] picture) {
        this.picture = picture;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCalories() {
        return calories;
    }

    public void setCalories(String calories) {
        this.calories = calories;
    }

    public String getFat() {
        return fat;
    }

    public void setFat(String fat) {
        this.fat = fat;
    }

    public String getProtein() {
        return protein;
    }

    public void setProtein(String protein) {
        this.protein = protein;
    }

}
