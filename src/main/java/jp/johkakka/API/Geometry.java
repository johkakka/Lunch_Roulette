package jp.johkakka.API;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

@JsonIgnoreProperties (ignoreUnknown = true)
public class Geometry {
    private Location location;
    private List<String> types;

    public Location getLocation() {
        return location;
    }

    public List<String> getTypes() {
        return types;
    }
}
