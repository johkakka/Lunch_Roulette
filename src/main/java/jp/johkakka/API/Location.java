package jp.johkakka.API;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Location {
    @JsonProperty("lat")
    String latString;

    @JsonProperty("lng")
    String lngString;

    public double getLat(){
        return Double.parseDouble(latString);
    }

    public double getLng(){
        return Double.parseDouble(lngString);
    }

    public double[] get(){
        return new double[]{this.getLat(), this.getLng()};
    }

}
