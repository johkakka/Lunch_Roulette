package jp.johkakka.API;

import java.util.List;

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
