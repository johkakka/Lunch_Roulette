package jp.johkakka.API;

import com.fasterxml.jackson.databind.ObjectMapper;
import jp.johkakka.JSON.GeometryModel;
import jp.johkakka.JSON.Location;

import java.io.IOException;

public class GeocodingAPI extends GoogleAPI{

    public Location result(String name){
        Location location = null;

        String key = super.getKey();
        if (key == null){
            return location;
        }
        super.setPath("https://maps.googleapis.com/maps/api/geocode/json?address="+name+"&region=jp&key="+key);

        //Get from Geocoding API
        try {
            //to Java class
            ObjectMapper objectMapper = new ObjectMapper();
            GeometryModel model = objectMapper.readValue(super.getURL(), GeometryModel.class);

            location = model.getTopResult().getGeometry().getLocation();

        } catch (IOException e) {
            e.printStackTrace();
            errorMessages.add(e.toString());
        } catch (NullPointerException e){
            e.printStackTrace();
            errorMessages.add("not found location");
        }
        return location;
    }
}
