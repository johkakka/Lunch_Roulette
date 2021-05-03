package jp.johkakka.API;

import com.fasterxml.jackson.databind.ObjectMapper;
import jp.johkakka.JSON.Place;
import jp.johkakka.JSON.PlaceModel;

import java.io.IOException;
import java.util.List;

public class PlaceAPI extends GoogleAPI{
    public List<Place> result(String loc){
        return getModel(loc, 1000).getResults();
    }

    public Place topResult(String loc, int d){
        return getModel(loc, d).getTopResult();
    }

    public List<Place> result(String loc, int d){
        return getModel(loc, d).getResults();
    }

    public PlaceModel getModel(String loc, int d){
        PlaceModel placeModel = null;

        String key = super.getKey();
        if (key == null){
            return placeModel;
        }
        super.setPath("https://maps.googleapis.com/maps/api/place/nearbysearch/json?location="+loc+"&radius="+d+"&type=restaurant&language=ja&key="+key);

        //Get from Place API
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            placeModel = objectMapper.readValue(super.getURL(), PlaceModel.class);

        } catch (IOException e) {
            e.printStackTrace();
            errorMessages.add(e.toString());
        } catch (NullPointerException e) {
            e.printStackTrace();
            errorMessages.add("not found location");
        }

        return placeModel;
    }

}
