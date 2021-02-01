package jp.johkakka.JSON;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Place {
    private String name;
    @JsonProperty("opening_hours")
    private OpeningHour openingHours;
    private List<Photo> photos;
    private String rating;
    private String vicinity;

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
        return Double.parseDouble(rating);
    }

    public String getVicinity() {
        return vicinity;
    }
}
