package com.code.metro.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import de.micromata.opengis.kml.v_2_2_0.Coordinate;

import java.util.ArrayList;
import java.util.List;

public class Station {

    private String name;
    private String description;
    @JsonIgnore
    private Coordinate coordinate;
    @JsonIgnore
    private List<Line> lines = new ArrayList<>();

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Coordinate getCoordinate() {
        return coordinate;
    }

    public void setCoordinate(Coordinate coordinate) {
        this.coordinate = coordinate;
    }

    public List<Line> getLines() {
        return lines;
    }

    public void setLines(List<Line> lines) {
        this.lines = lines;
    }

    @Override
    public String toString() {
        return "\n\t\t Station {" +
          "name='" + name + '\'' +
          ", description='" + description + '\'' +
          '}';
    }
}
