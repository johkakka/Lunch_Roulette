package jp.johkakka.API;

import com.fasterxml.jackson.databind.ObjectMapper;
import jp.johkakka.JSON.Place;
import jp.johkakka.JSON.PlaceModel;

import java.io.IOException;
import static java.lang.Math.*;

public class PlaceAPI extends GoogleAPI{
    private final int MAX_DISTANCE = 1000;

    public Place result(String loc){
        Place res = null;

        String key = super.getKey();
        if (key == null){
            return res;
        }
        super.setPath("https://maps.googleapis.com/maps/api/place/nearbysearch/json?location="+loc+"&radius="+MAX_DISTANCE+"&type=restaurant&language=ja&key="+key);

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

    /**
     * ２地点間の距離を求める
     *   GoogleMapAPIのgeometory.computeDistanceBetweenのロジック
     *   浮動小数点の精度が足りないためGoogleより桁数が少ないかもしれません
     *
     * @param lat1 緯度１
     * @param lon1 経度１
     * @param lat2 緯度２
     * @param lon2 経度２
     * @return float 距離(m)
     */
    public static float google_distance(float lat1, float lon1, float lat2, float lon2) {
        // 緯度経度をラジアンに変換
        double radLat1 = toRadians(lat1); // 緯度１
        double radLon1 = toRadians(lon1); // 経度１
        double radLat2 = toRadians(lat2); // 緯度２
        double radLon2 = toRadians(lon2); // 経度２

        double r = 6378137.0; // 赤道半径

        double averageLat = (radLat1 - radLat2) / 2;
        double averageLon = (radLon1 - radLon2) / 2;
        Double d = r * 2 * asin(sqrt(pow(sin(averageLat), 2) + cos(radLat1) * cos(radLat2) * pow(sin(averageLon), 2)));

        return d.floatValue();
    }

}
