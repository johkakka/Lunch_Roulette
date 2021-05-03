package jp.johkakka.JSON;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Photo {
    String height;
    String width;
    @JsonProperty("photo_reference")
    String photoReference;

    public String getHeight() {
        return height;
    }

    public int getHeightInt(){
        return Integer.parseInt(height);
    }

    public String getWidth() {
        return width;
    }

    public int getWidthInt(){
        return Integer.parseInt(width);
    }

    public String getPhotoReference() {
        return photoReference;
    }


}
