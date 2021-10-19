package com.code.metro.web;

import com.code.metro.config.KmlReaderManager;
import com.code.metro.model.Response;
import com.code.metro.service.LineStationManagerService;
import com.code.metro.service.ReaderFileService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.NonNull;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MetroController {

  private static final Logger LOG = LoggerFactory.getLogger(KmlReaderManager.class);

  @Autowired
  private LineStationManagerService lineStationsService;

  @RequestMapping(value = {"/metrocdmx/lines/path"},
                  method = RequestMethod.GET,
                  produces = "application/json")
  public ResponseEntity<Response> getlines(@RequestParam String origin,
                                           @RequestParam String destination) {
    return new ResponseEntity<Response>(lineStationsService.findPath(origin, destination),
                                        HttpStatus.OK);
  }
}
