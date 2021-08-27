package jp.johkakka.JSON;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Location {
    public Location (String latString, String lngString){
        this.latString = latString;
        this.lngString = lngString;
    }

    public Location(){}

    @JsonProperty("lat")
    private String latString;

    @JsonProperty("lng")
    private String lngString;

    public double getLat(){
        return Double.parseDouble(latString);
    }

    public double getLng(){
        return Double.parseDouble(lngString);
    }

    public double[] get(){
        return new double[]{this.getLat(), this.getLng()};
    }

    public String[] getStrings(){
        return new String[]{String.format("%.3f", this.getLat()), String.format("%.3f", this.getLng())};
    }

    @Override
    public String toString() {
        return latString + "," + lngString;
    }
}
