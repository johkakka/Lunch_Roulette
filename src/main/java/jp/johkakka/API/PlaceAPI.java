package jp.johkakka.API;

import jp.johkakka.JSON.Place;

public class PlaceAPI extends GoogleAPI{
    public Place result(String loc){
        Place res = null;

        String key = super.getKey();
        if (key == null){
            return res;
        }
        super.setPath("https://maps.googleapis.com/maps/api/place/nearbysearch/json?location="+loc+"&radius=1000&type=restaurant&language=ja&key="+key);

        return res;
    }

}
