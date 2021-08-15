package jp.johkakka.lunch;

import jp.johkakka.API.PlaceAPI;
import jp.johkakka.JSON.Location;
import jp.johkakka.JSON.Place;

import java.util.ArrayList;
import java.util.List;

import static java.lang.Math.*;

public class Roulette {
    private final int MAX_DISTANCE = 1000;

    public Place result(PlaceAPI placeAPI, Location location){
        List<Place> _ps = placeAPI.result(location.toString(), MAX_DISTANCE);
        if (_ps == null) {
            return null;
        }

        List<Place> places = new ArrayList<>();
        for (Place place: _ps){
            if (place.getOpeningHours() == null) places.add(place);
            else if (place.getOpeningHours().isOpenNow()) places.add(place);
        }

        if(places.isEmpty()){
            return null;
        }

        double[] scores = new double[places.size()];
        double sum = 0.0;
        for (int i = 0; i < places.size(); i++){
            Place place = places.get(i);
            double score = place.getRatingDouble()*
                    google_distance(place.getGeometry().getLocation(), location)/MAX_DISTANCE;
            sum += score;
            scores[i] = score;
        }

        for (int i = 0; i < scores.length; i++){
            scores[i] = scores[i]/sum;
        }

        double rand = random();
        for (int i = 0; i < scores.length; i++){
            rand -= scores[i];
            if (rand < 0){
                return places.get(i);
            }
        }

        return null;
    }


    /**
     * ２地点間の距離を求める
     *   GoogleMapAPIのgeometory.computeDistanceBetweenのロジック
     *   浮動小数点の精度が足りないためGoogleより桁数が少ないかもしれません
     *
     * @param loc1 地点１
     * @param loc2 地点２
     * @return result 距離(m)
     */
    public static double google_distance(Location loc1, Location loc2){
        if (loc1 == null || loc2 == null) {
            return Double.NaN;
        }

        return google_distance(loc1.getLat(), loc1.getLng(), loc2.getLat(), loc2.getLng());
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
     * @return result 距離(m)
     */
    public static double google_distance(double lat1, double lon1, double lat2, double lon2) {
        // 緯度経度をラジアンに変換
        double radLat1 = toRadians(lat1); // 緯度１
        double radLon1 = toRadians(lon1); // 経度１
        double radLat2 = toRadians(lat2); // 緯度２
        double radLon2 = toRadians(lon2); // 経度２

        double r = 6378137.0; // 赤道半径

        double averageLat = (radLat1 - radLat2) / 2;
        double averageLon = (radLon1 - radLon2) / 2;
        return r * 2 * asin(sqrt(pow(sin(averageLat), 2) + cos(radLat1) * cos(radLat2) * pow(sin(averageLon), 2)));
    }
}
