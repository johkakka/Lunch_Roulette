package jp.johkakka.API;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties (ignoreUnknown = true)
public class GeometryResult {
    Geometry geometry;
}
