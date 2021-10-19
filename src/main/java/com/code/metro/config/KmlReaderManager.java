package com.code.metro.config;

import com.code.metro.model.Line;
import com.code.metro.model.Station;
import de.micromata.opengis.kml.v_2_2_0.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

@Component
public class KmlReaderManager {
  private static final Logger log = LoggerFactory.getLogger(KmlReaderManager.class);

  private static final String LINES_SUBWAY = "Líneas de Metro";
  private static final String STATIONS_LINE = "Estaciones de Metro";

  private List<Line> lines = new ArrayList<>();
  private List<Station> stations = new ArrayList<>();

  @Value("${kml.target.path}")
  private String kmlPath;

  @PostConstruct
  public void parseKmlFile() {
    try(InputStream is = getClass().getClassLoader().getResourceAsStream(kmlPath)){
      Kml kml = Kml.unmarshal(is);
      Feature feature = kml.getFeature();
      parseFeature(feature);
    } catch (IOException e) {
     log.error(e.getMessage());
    }
  }

  private void parseFeature(Feature feature) {
    List<Line> lines = new ArrayList<>();
    List<Station> stations = new ArrayList<>();

    if (feature != null) {
      if (feature instanceof Document) {
        log.info("Parsing Document ..");
        List<Feature> featureList = ((Document) feature).getFeature();
        for (Feature documentFeature : featureList) {
          log.info("Parsing Feature ..");
          switch (documentFeature.getName()) {
            case LINES_SUBWAY:
              retrieveLines(((Folder) documentFeature).getFeature());
              break;
            case STATIONS_LINE:
              retrieveStations(((Folder) documentFeature).getFeature());
              break;
            default:
              break;
          }
        }
        assignStationsToLine();
      }
    }
  }

  private void retrieveLines(List<Feature> folderFeatureList) {
    for (Feature folderFeature : folderFeatureList) {
      if (folderFeature instanceof Placemark) {
        log.info("Parsing Folder Lines ..");
        Line line = new Line();
        Placemark placemark = (Placemark) folderFeature;
        log.info("Parsing Placemark ..");
        line.setName(placemark.getName());
        Geometry geometry = placemark.getGeometry();
        log.info("Getting Coordinates from Geometry ..");
        line.setCoordinates(((LineString) geometry).getCoordinates());
        lines.add(line);
      }
    }
  }

  private void retrieveStations(List<Feature> folderFeatureList) {
    for (Feature folderFeature : folderFeatureList) {
      if (folderFeature instanceof Placemark) {
        log.info("Parsing Folder Stations ..");
        Station station = new Station();
        Placemark placemark = (Placemark) folderFeature;
        log.info("Getting Coordinates from Point ..");
        Point point = (Point) placemark.getGeometry();
        station.setName(placemark.getName());
        station.setDescription(placemark.getDescription());
        station.setCoordinate(point.getCoordinates().get(0));
        stations.add(station);
      }
    }
  }

  private void assignStationsToLine() {
    log.info("Set stations to lines ..");
    for (Line line : lines) {
      List<Station> stationsOfline = new ArrayList<>();
      for(Coordinate coordinate : line.getCoordinates()) {
        Station found = stations.stream()
          .filter(station -> coordinate.getLatitude() == station.getCoordinate().getLatitude()
                             && coordinate.getAltitude() == station.getCoordinate().getAltitude()
                             && coordinate.getLongitude() == station.getCoordinate().getLongitude())
          .findAny()
          .orElse(null);

        if(null != found) {
          stationsOfline.add(found);
          found.getLines().add(line);
        }
      }
      line.setStations(stationsOfline);
    }
    log.info(lines.toString());
  }

  public List<Line> getLines() {
    return lines;
  }

  public void setLines(List<Line> lines) {
    this.lines = lines;
  }

  public List<Station> getStations() {
    return stations;
  }

  public void setStations(List<Station> stations) {
    this.stations = stations;
  }
}
