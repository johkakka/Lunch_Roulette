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
import java.util.Optional;

@Controller
public class Lunch {

    @GetMapping(value={"/", "/index"})
    public String index(){
        return "index";
    }

    @GetMapping("/roulette")
    public String here(){
        return "redirect:index";
    }

    @GetMapping("/credit")
    public String credit(){
        return "credit";
    }

    @PostMapping("/roulette")
    public String from(ModelMap modelMap,
                       @RequestParam("from") String name,
                       @RequestParam("point") Optional<String> loc)
            throws MalformedURLException {
        if (name == null || name.isBlank()){
            return "index";
        }

        Location location;
        String[] locString;
        if (loc.isEmpty()) {
            GeocodingAPI geocoding = new GeocodingAPI();

            //Get from Geocoding API
            location = geocoding.result(name);
            if (geocoding.getErrorMessages().size() > 0) {
                StringBuilder message = new StringBuilder();
                for (String s : geocoding.getErrorMessages()) {
                    message.append(s).append("\n");
                }
                modelMap.addAttribute("message", new String(message));
                return "error";
            } else if (location == null) {
                String message = "Not found location result";
                modelMap.addAttribute("message", message);
                return "error";
            }

            locString = location.getStrings();
        } else {
            locString = loc.get().split(",");
            location = new Location(locString[0], locString[1]);
        }

        modelMap.addAttribute("from", name);
        modelMap.addAttribute("location", "(" + locString[0] + ", " + locString[1] + ")");

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
        modelMap.addAttribute("vin", place.getVicinity());

        modelMap.addAttribute("star", "<span class=\"star5_rating\" data-rate=\""+place.getRating()+"\"></span>");
        modelMap.addAttribute("rate", place.getRating());

        return "roulette";
    }
}
