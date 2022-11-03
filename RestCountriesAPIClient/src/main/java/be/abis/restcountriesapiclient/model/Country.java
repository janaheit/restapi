package be.abis.restcountriesapiclient.model;

import java.util.List;

public class Country {

    private Name name;
    private String region;
    private String subregion;
    private List<String> borders;
    private String cca2;

    public Country() {
    }

    @Override
    public String toString() {
        return "Country{" +
                "name=" + name +
                ", region='" + region + '\'' +
                ", subRegion='" + subregion + '\'' +
                ", borders=" + borders +
                ", cca2='" + cca2 + '\'' +
                '}';
    }

    public void setBorders(List<String> borders) {
        this.borders = borders;
    }

    public String getRegion() {
        return region;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public String getSubregion() {
        return subregion;
    }

    public void setSubregion(String subregion) {
        this.subregion = subregion;
    }

    public List<String> getBorders() {
        return borders;
    }



    public String getCca2() {
        return cca2;
    }

    public void setCca2(String cca2) {
        this.cca2 = cca2;
    }

    public Name getName() {
        return name;
    }

    public void setName(Name name) {
        this.name = name;
    }
}
