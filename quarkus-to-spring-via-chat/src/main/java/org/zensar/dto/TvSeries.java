package org.zensar.dto;

import java.net.URL;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TvSeries {

    private int id;
    private URL url;
    private String name;
    private String type;
    private String language;

}
