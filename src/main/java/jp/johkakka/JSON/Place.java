package jp.johkakka.JSON;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Place {
    private Geometry geometry;
    private String name;
    @JsonProperty("opening_hours")
    private OpeningHour openingHours;
    private List<Photo> photos;
    private String rating;
    private String vicinity;

    public Geometry getGeometry() {
        return geometry;
    }

    public String getName() {
        return name;
    }

    public OpeningHour getOpeningHours() {
        return openingHours;
    }

    public List<Photo> getPhotos() {
        return photos;
    }

    public Photo getTopPhoto(){
        if(getPhotos().isEmpty()){
            return null;
        }

        return getPhotos().get(0);
    }

    public String getRating() {
        return rating;
    }

    public double getRatingDouble() {
        if (rating == null){
            return 2.0;
        }
        return Double.parseDouble(rating);
    }

    public String getVicinity() {
        return vicinity;
    }
}
