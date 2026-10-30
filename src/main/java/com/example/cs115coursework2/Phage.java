package com.example.cs115coursework2;

public class Phage extends Viroid {

    /**
     * Constructor to set the attributes for each Viroid
     *
     * @param epicentre the starting location
     * @param endPoint the end location
     * @param spreadChance chance of pathogen spreading
     *
     */
    public Phage(DiseaseCentre epicentre, DiseaseCentre endPoint, double spreadChance) {
        super(epicentre, endPoint, spreadChance);
    }

    /**
     * alawys adds location the phage spread to first then checks
     * if it will multiply by comparing threshold with population density
     * @param diseaseCentre the disease centre
     */
    @Override
    public void spread(DiseaseCentre diseaseCentre) {
        getLocations().add(diseaseCentre);
        double threshold = getSpreadChance() * 10000;
        if (diseaseCentre.getPopDensity() > threshold) {
            setMultiplying(true);
        }
    }


    /**
     * method that returns trace, method is called from the super class
     * @return the trace message
     * */
    @Override
    public String getTrace() {
        return super.getTrace();
    }
}
