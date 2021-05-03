package jp.johkakka.JSON;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties (ignoreUnknown = true)
public class GeometryResult {
    private Geometry geometry;

    public Geometry getGeometry() {
        return geometry;
    }
}
