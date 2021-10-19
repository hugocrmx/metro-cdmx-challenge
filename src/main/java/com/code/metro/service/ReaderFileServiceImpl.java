package com.code.metro.service;

import com.code.metro.config.KmlReaderManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ReaderFileServiceImpl implements ReaderFileService {

    @Autowired
    private KmlReaderManager kmlReaderManager;

    public void parseFileKml() {
        kmlReaderManager.parseKmlFile();
    }
}
