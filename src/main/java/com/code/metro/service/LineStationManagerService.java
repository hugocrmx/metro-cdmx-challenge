package com.code.metro.service;

import com.code.metro.model.Response;

public interface LineStationManagerService {
  Response findPath(String startStation, String endStation);
}
