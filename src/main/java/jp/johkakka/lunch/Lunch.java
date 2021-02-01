package jp.johkakka.lunch;

import jp.johkakka.API.GeocodingAPI;
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
        double[] geoLoc = geocoding.result(name);
        if (geocoding.getErrorMessages().size() > 0){
            StringBuilder message = new StringBuilder();
            for (String s : geocoding.getErrorMessages()) {
                message.append(s).append("\n");
            }
            modelMap.addAttribute("message", new String(message));
            return "error";
        }

        modelMap.addAttribute("from", name);
        modelMap.addAttribute("location", "(" + geoLoc[0] + "," + geoLoc[1] + ")");
        return "roulette";
    }
}
