package com.code.metro.model;

import java.util.List;

public class Response {

  private Station origin;
  private Station destination;
  private int stations;
  private List<Station> list;

  public int getStations() {
    return stations;
  }

  public void setStations(int stations) {
    this.stations = stations;
  }

  public List<Station> getList() {
    return list;
  }

  public void setList(List<Station> list) {
    this.list = list;
  }

  public Station getOrigin() {
    return origin;
  }

  public void setOrigin(Station origin) {
    this.origin = origin;
  }

  public Station getDestination() {
    return destination;
  }

  public void setDestination(Station destination) {
    this.destination = destination;
  }

  @Override public String toString() {
    return "Response{" +
      "origin=" + origin +
      ", destination=" + destination +
      ", stations=" + stations +
      ", list=" + list +
      '}';
  }
}
