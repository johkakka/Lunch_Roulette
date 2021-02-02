package jp.johkakka.API;

import com.fasterxml.jackson.databind.ObjectMapper;
import jp.johkakka.JSON.Place;
import jp.johkakka.JSON.PlaceModel;

import java.io.IOException;

public class PlaceAPI extends GoogleAPI{
    public Place result(String loc){
        Place res = null;

        String key = super.getKey();
        if (key == null){
            return res;
        }
        super.setPath("https://maps.googleapis.com/maps/api/place/nearbysearch/json?location="+loc+"&radius=1000&type=restaurant&language=ja&key="+key);

        //Get from Place API
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            PlaceModel placeModel = objectMapper.readValue(super.getURL(), PlaceModel.class);

            res = placeModel.getTopResult();

        } catch (IOException e) {
            e.printStackTrace();
            errorMessages.add(e.toString());
        } catch (NullPointerException e) {
            e.printStackTrace();
            errorMessages.add("not found location");
        }

        return res;
    }

}
