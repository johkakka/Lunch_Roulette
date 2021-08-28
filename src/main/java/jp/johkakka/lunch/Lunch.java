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
        return "redirect:index";
    }

    @GetMapping("/credit")
    public String credit(){
        return "credit";
    }

    @GetMapping("/more")
    public String more(){
        return "more";
    }


    @PostMapping("/roulette")
    public String from(ModelMap modelMap,
                       @RequestParam("from") String name,
                       @RequestParam("point") String loc)
            throws MalformedURLException {
        if (name == null || name.isBlank()){
            return "index";
        }

        Location location;
        String[] locString;
        if (loc.equals("")) {
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
            String[] l = loc.split(",");
            location = new Location(l[0], l[1]);
            locString = location.getStrings();
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

        modelMap.addAttribute("to", sanitize(place.getName()));
        modelMap.addAttribute("vin", place.getVicinity());

        modelMap.addAttribute("star", "<span class=\"star5_rating\" data-rate=\""+place.getRating()+"\"></span>");
        modelMap.addAttribute("rate", place.getRating());

        String tw = "<a href=\"https://twitter.com/share\" class=\"twitter-share-button\" data-url=\"https://johkakka.dev/lunch/\" " +
                "data-text=\"【\uD83C\uDFAFルーレット結果】\n"
                +sanitize(name+"(" + locString[0] + ", " + locString[1] + ")")+"から……\n\n"
                +"「"+sanitize(place.getName())+"」が選ばれました！\n\n"+
                "#飯どうするーれっと"+"\">Tweet</a>";

        modelMap.addAttribute("tweet", tw);

        return "roulette";
    }

    private String sanitize(String s){
        String r = s.replace("&", "&amp;").replace("\"", "&quot;").replace("<", "&lt;").replace(">", "&gt;").replace("'", "&#39;");
        return r;
    }
}
