package jp.johkakka.JSON;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class GeometryModel {
    private List<GeometryResult> results;
    private String status;

    public List<GeometryResult> getResults() {
        return results;
    }

    public GeometryResult getTopResult(){
        if (results.isEmpty()){
            return null;
        } else {
          return results.get(0);
        }
    }

    public String getStatus() {
        return status;
    }

    public boolean isOk(){
        return getStatus().equals("OK");
    }
}
