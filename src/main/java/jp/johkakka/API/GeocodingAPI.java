package jp.johkakka.API;

import com.fasterxml.jackson.databind.ObjectMapper;
import jp.johkakka.JSON.GeometryModel;

import java.io.IOException;
import java.net.URL;

public class GeocodingAPI extends GoogleAPI{

    public double[] result(String name){
        double[] geoLoc = new double[]{Double.NaN, Double.NaN};

        String key = super.getKey();
        if (key == null){
            return geoLoc;
        }
        super.setPath("https://maps.googleapis.com/maps/api/geocode/json?address="+name+"&region=jp&key="+key);

        //Get from Geocoding API
        String geoJson = "";
        try {
            //to Java class
            ObjectMapper objectMapper = new ObjectMapper();
            GeometryModel model = objectMapper.readValue(super.getURL(), GeometryModel.class);

            geoLoc = model.getTopResult().getGeometry().getLocation().get();

        } catch (IOException e) {
            e.printStackTrace();
            errorMessages.add(e.toString());
        } catch (NullPointerException e){
            e.printStackTrace();
            errorMessages.add("not found location");
        }
        return geoLoc;
    }
}
