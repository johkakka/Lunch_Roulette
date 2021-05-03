package jp.johkakka.JSON;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Location {
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

    @Override
    public String toString() {
        return latString + "," + lngString;
    }
}
