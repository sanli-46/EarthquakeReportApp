package com.sanli.erthquakereportapp.domain;

import lombok.Data;

import java.util.ArrayList;
@Data
public class EartquakeApiResponse {
    public String type;
    public Metadata metadata;
    public ArrayList<Feature> features;
    public ArrayList<Double> bbox;
}
