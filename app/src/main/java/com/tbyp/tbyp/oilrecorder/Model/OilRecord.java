package com.tbyp.tbyp.oilrecorder.Model;

import java.util.Comparator;

public class OilRecord implements Comparable {
    private float numberOfOil;
    private float cost;
    private String date;
    private float kilometer;

    public OilRecord(float numberOfOil, float cost,
                     float kilometer, String date){
        this.numberOfOil = numberOfOil;
        this.cost = cost;
        this.date = date;
        this.kilometer = kilometer;
    }


    public float getNumberOfOil() {
        return numberOfOil;
    }

    public void setNumberOfOil(float numberOfOil) {
        this.numberOfOil = numberOfOil;
    }

    public float getKilometer() {
        return kilometer;
    }

    public void setKilometer(float kilometer) {
        this.kilometer = kilometer;
    }

    public float getCost() {
        return cost;
    }

    public void setCost(float cost) {
        this.cost = cost;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }


    @Override
    public int compareTo(Object another) {
        if(this.getKilometer() > ((OilRecord)another).getKilometer()){
            return 1;
        }
        else if(this.getKilometer() == ((OilRecord)another).getKilometer()){
            return 0;
        }
        else{
            return -1;
        }
    }
}
