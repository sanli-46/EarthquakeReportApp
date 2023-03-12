package com.sanli.erthquakereportapp.service;

import com.sanli.erthquakereportapp.domain.EartquakeApiResponse;
import com.sanli.erthquakereportapp.domain.Feature;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;


import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class EartquakeApiService {
    private static final Double TURKEY_LAT_MIN  = 35.9025;
    private static final Double TURKEY_LAT_MAX  = 42.02683;
    private static final Double TURKEY_LONG_MIN = 25.90902;
    private static final Double TURKEY_LONG_MAX = 44.5742;

    public List<Feature> getErthquakeReport(String start , String end) {
        RestTemplate restTemplate=new RestTemplate();
        HttpHeaders httpHeaders =new HttpHeaders();
        httpHeaders.add("Content-Type", "application/json");

        HttpEntity request = new HttpEntity<String>("", httpHeaders);
        ResponseEntity<EartquakeApiResponse> responseEntity = restTemplate.exchange("https://earthquake.usgs.gov/" + "fdsnws/event/1/query?"+ "format=geojson&starttime="+start+"&endtime="+end, HttpMethod.GET, request, EartquakeApiResponse.class);

        EartquakeApiResponse body = responseEntity.getBody();

        List<Feature> features = new ArrayList<>();

        //Kordinatlar arasındaysa .
        if (body != null) {
             features = body.getFeatures().stream()
                    .filter(feature -> isPointInTurkey(feature.getGeometry().getCoordinates().get(0),feature.getGeometry().getCoordinates().get(1)))
                    .collect(Collectors.toList());
        }

        return features;



    }
    // kordinatları al.
    public Boolean isPointInTurkey(Double longtit,Double latit){
        if ((longtit<=TURKEY_LONG_MAX && longtit>=TURKEY_LONG_MIN)&&(latit<=TURKEY_LAT_MAX && latit>=TURKEY_LAT_MIN)){
            return true;
        }else {
            return false;
        }

    }
}
