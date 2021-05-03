package jp.johkakka.lunch;

import jp.johkakka.API.GeocodingAPI;
import jp.johkakka.API.PlaceAPI;
import jp.johkakka.JSON.Location;
import jp.johkakka.JSON.Place;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.net.MalformedURLException;

@Controller
public class Lunch {

    @GetMapping(value={"/", "/index"})
    public String index(){
        return "index";
    }

    @GetMapping("/roulette")
    public String here(){
        return "roulette";
    }

    @PostMapping("/roulette")
    public String from(ModelMap modelMap, @RequestParam("from") String name) throws MalformedURLException {
        if (name.isBlank()){
            return "index";
        }

        GeocodingAPI geocoding = new GeocodingAPI();

        //Get from Geocoding API
        Location location = geocoding.result(name);
        if (geocoding.getErrorMessages().size() > 0){
            StringBuilder message = new StringBuilder();
            for (String s : geocoding.getErrorMessages()) {
                message.append(s).append("\n");
            }
            modelMap.addAttribute("message", new String(message));
            return "error";
        } else if (location == null){
            String message = "Not found location result";
            modelMap.addAttribute("message", message);
            return "error";
        }

        modelMap.addAttribute("from", name);
        modelMap.addAttribute("location", "(" + location + ")");

        //Get from Place API
        PlaceAPI placeAPI = new PlaceAPI();
        Place place = new Roulette().result(placeAPI, location);

        if (placeAPI.getErrorMessages().size() > 0) {
            StringBuilder message = new StringBuilder();
            for (String s : placeAPI.getErrorMessages()) {
                message.append(s).append("\n");
            }
            modelMap.addAttribute("message", new String(message));
            return "error";
        } else if (place == null) {
            String message = "Not found place result";
            modelMap.addAttribute("message", message);
            return "error";
        }

        modelMap.addAttribute("to", place.getName());

        return "roulette";
    }
}
