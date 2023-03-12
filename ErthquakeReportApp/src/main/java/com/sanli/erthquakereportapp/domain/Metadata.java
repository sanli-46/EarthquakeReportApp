package com.sanli.erthquakereportapp.domain;

import lombok.Data;

@Data
public class Metadata{
    public long generated;
    public String url;
    public String title;
    public int status;
    public String api;
    public int count;
}
