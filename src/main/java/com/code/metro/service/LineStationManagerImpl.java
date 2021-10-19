package com.code.metro.service;

import com.code.metro.config.KmlReaderManager;
import com.code.metro.business.ShortestPath;
import com.code.metro.model.Line;
import com.code.metro.model.Response;
import com.code.metro.model.Station;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class LineStationManagerImpl implements LineStationManagerService {
  private static final Logger log = LoggerFactory.getLogger(LineStationManagerImpl.class);

  private List<Line> lines = new ArrayList<>();
  private List<Station> stations = new ArrayList<>();
  private int[][] path;
  private int[][] subwayline;
  private List<Integer> passStationsIndex = new ArrayList<>();
  private List<Station> passStations = new ArrayList<>();

  private List<String> list1 = new ArrayList<>();
  private List<List<String>> lists = new ArrayList<>();

  private ShortestPath result;

  @Autowired
  private KmlReaderManager kmlReaderManager;

  @Override
  public Response findPath(String startStationName, String endStationName) {
    Response response = new Response();
    clearListPassStations();
    lines = kmlReaderManager.getLines();
    stations = kmlReaderManager.getStations();

    Station startStation = isStation(startStationName);
    Station endStation = isStation(endStationName);

    //TODO Add null validation for start and end stations
    log.info("Map Subwaylines and Paths ..");
    mapLinePath();
    for(int i=0;i<lines.size();i++) {
      for(int j=0;j<lines.get(i).getStations().size()-1;j++) {
        this.initDis(lines.get(i).getStations().get(j), lines.get(i).getStations().get(j+1));
      }
    }

    result = new ShortestPath(subwayline);
    log.info("Calculate shortest path ...");
    int i = this.getIndex(startStation);
    int j = this.getIndex(endStation);
    int shortest = result.getMinDis(i,j);
    shortest++;
    //TODO: Validation for shortest

    passStationsIndex = result.indexToList(i,j);
    for(int k = 0; k< passStationsIndex.size(); k++) {
      Station passedStation = getStationByIndex(passStationsIndex.get(k));
      passStations.add(passedStation);
    }

    response.setOrigin(startStation);
    response.setDestination(endStation);
    response.setStations(shortest);
    response.setList(passStations);
    log.info(response.toString());
    return response;
  }

  public void mapLinePath() {
    this.subwayline = new int[stations.size()][stations.size()];
    this.path = new int[stations.size()][stations.size()];
    for(int i=0;i<stations.size();i++) {
      for(int j=0;j<stations.size();j++) {
        if(i==j) {
          subwayline[i][j] = 0;
        }
        else {
          subwayline[i][j] = 999999;
          subwayline[j][i] = 999999;
        }
      }
    }
  }

  public void initDis(Station start, Station end) {
    this.subwayline[getIndex(start)][getIndex(end)] = 1;
    this.subwayline[getIndex(end)][getIndex(start)] = 1;
  }

  public int getIndex(Station station) {
    return stations.indexOf(station);
  }

  public Station isStation(String stationName) {
    for(Station station : stations) {
      if(station.getName().equals(stationName)){
        return station;
      }
    }
    return null;
  }

  public Station getStationByIndex(int index) {
    return stations.get(index);
  }

  private void clearListPassStations() {
    passStationsIndex.clear();
    passStations.clear();
  }
}
