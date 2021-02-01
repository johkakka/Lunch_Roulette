package jp.johkakka.JSON;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class PlaceModel {
    private List<PlaceResult> results;
    private String status;

    public List<PlaceResult> getResults() {
        return results;
    }

    public PlaceResult getTopResult(){
        if (results.isEmpty()){
            return null;
        } else {
            return results.get(0);
        }
    }

    public String getStatus() {
        return status;
    }

    public boolean isOK(){
        return status.equals("OK");
    }

}
