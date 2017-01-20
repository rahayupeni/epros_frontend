package com.example.renaissance.epros;

/**
 * Created by Renaissance on 1/18/2017.
 */

public class Teman {
    private String urlFoto;
    private String namateman;
    private String statusteman;
    private String index;

    public Teman(){

    }

    public Teman(String name, String statusteman, String index, String thumbnailUrl ) {
        this.namateman = name;
        this.statusteman = statusteman;
        this.urlFoto = thumbnailUrl;
        this.index = index;
    }


    public void setNamateman(String namateman) {
        this.namateman = namateman;
    }

    public void setIndex(String index) { this.index = index; }

    public void setStatusteman(String statusteman) {
        this.statusteman = statusteman;
    }

    public void setUrlFoto(String urlFoto) {
        this.urlFoto = urlFoto;
    }

    public String getNamateman() {
        return namateman;
    }

    public String getIndex() { return index; }

    public String getStatusteman() {
        return statusteman;
    }

    public String getUrlFoto() {
        return urlFoto;
    }
}
