package com.sanli.erthquakereportapp.controller;

import com.sanli.erthquakereportapp.domain.EartquakeApiResponse;
import com.sanli.erthquakereportapp.domain.Feature;
import com.sanli.erthquakereportapp.service.EartquakeApiService;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/earthquakeapi")
public class EarthquakeApiController {


    EartquakeApiService eartquakeApiService;
    public EarthquakeApiController(EartquakeApiService eartquakeApiService) {
        this.eartquakeApiService=eartquakeApiService;
    }
    //Tarih aralıkları ile arama.
    @GetMapping(params = {"startDate","endDate"})
    public List<Feature> getEartquakeReport(@RequestParam(value = "startDate")String startDate , @RequestParam(value = "endDate")String endDate){
        // Verileri Çek
       return this.eartquakeApiService.getErthquakeReport(startDate,endDate);
    }


    //Güne göre .
    @GetMapping(params = {"days"})
    public ResponseEntity getEartquakeReport(@RequestParam(value = "days") Integer days){

        //Calender sınıfından oluşturduğumuz nesneyle gün verilerini tutuyoruz.
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DATE, -days);
        Date startDate = cal.getTime();
        Date endDate = new Date();

        SimpleDateFormat dt = new SimpleDateFormat("yyyy-MM-dd");

        List<Feature> features = this.eartquakeApiService.getErthquakeReport(dt.format(startDate),dt.format(endDate));

        //Hatta mesajı .
        if(features.size() > 0 ) {
            return new ResponseEntity<>(features, HttpStatus.OK);
        } else {
            return new ResponseEntity<>("No recorded earthquake for given "+days+" days", HttpStatus.OK);
        }

    }

}
