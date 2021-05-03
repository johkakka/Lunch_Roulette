package jp.johkakka.JSON;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown = true)
public class OpeningHour {
    @JsonProperty("open_now")
    private String openNow;

    public String getOpenNow() {
        return openNow;
    }

    public boolean isOpenNow(){
        return Boolean.parseBoolean(openNow);
    }

}
