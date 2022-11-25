package com.binarair.binarairrestapi.service.impl;

import com.binarair.binarairrestapi.exception.DataNotFoundException;
import com.binarair.binarairrestapi.model.entity.Airlines;
import com.binarair.binarairrestapi.model.request.AirlineRequest;
import com.binarair.binarairrestapi.model.response.AirlineResponse;
import com.binarair.binarairrestapi.repository.AirlineRepository;
import com.binarair.binarairrestapi.service.AirlineService;
import com.binarair.binarairrestapi.service.FirebaseStorageFileService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
public class AirlineServiceImpl implements AirlineService {

    private final static Logger log = LoggerFactory.getLogger(AirlineServiceImpl.class);

    private final AirlineRepository airlineRepository;

    private final FirebaseStorageFileService firebaseStorageFileService;

    @Autowired
    public AirlineServiceImpl(AirlineRepository airlineRepository, FirebaseStorageFileService firebaseStorageFileService) {
        this.airlineRepository = airlineRepository;
        this.firebaseStorageFileService = firebaseStorageFileService;
    }

    @Override
    public AirlineResponse save(AirlineRequest airlineRequest, MultipartFile multipartFile) {
        if (multipartFile.isEmpty()) {
            throw new DataNotFoundException("Opps, please choose a picture first");
        }
        Airlines airline = Airlines.builder()
                .id(String.format("al-%s", UUID.randomUUID().toString()))
                .name(airlineRequest.getAirlineName())
                .description(airlineRequest.getDescription())
                .logoURL(firebaseStorageFileService.doUploadFile(multipartFile))
                .createdAt(LocalDateTime.now())
                .build();
        log.info("Do save airline data into database");
        airlineRepository.save(airline);
        log.info("Successful save airline data");
        return AirlineResponse.builder()
                .id(airline.getId())
                .airlineName(airline.getName())
                .description(airline.getDescription())
                .logoURL(airline.getLogoURL())
                .createdAt(airline.getCreatedAt())
                .build();
    }
}