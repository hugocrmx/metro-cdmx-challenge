package com.code.metro.model;

import de.micromata.opengis.kml.v_2_2_0.Coordinate;

import java.util.List;

public class Line {
    private String name;
    private List<Coordinate> coordinates;
    private List<Station> stations;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Coordinate> getCoordinates() {
        return coordinates;
    }

    public void setCoordinates(List<Coordinate> coordinates) {
        this.coordinates = coordinates;
    }

    public List<Station> getStations() {
        return stations;
    }

    public void setStations(List<Station> stations) {
        this.stations = stations;
    }

    @Override public String toString() {
        return "\n\t Line {" +
          "name='" + name + '\'' +
          ", stations=" + stations +
          "}";
    }
}
