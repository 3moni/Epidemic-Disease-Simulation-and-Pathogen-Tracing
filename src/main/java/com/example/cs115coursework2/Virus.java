package com.example.cs115coursework2;

import java.util.ArrayList;

public class Virus extends Viroid {

    private String multHistory = "";

    /**
     * Constructor to set the attributes for each Viroid while setting multiplying to always false
     *
     * @param epicentre starting location
     * @param endPoint end location
     * @param spreadChance chance of disease spreading
     *
     */
    public Virus(DiseaseCentre epicentre, DiseaseCentre endPoint, double spreadChance) {
        super(epicentre, endPoint, spreadChance);
    }

    /**
     * alawys adds location the phage spread to first then checks
     * if it will multiply by comparing threshold with population density
     * @param diseaseCentre the disease centre
     * and adds every location virus spread to after multiplying in a string
     * seperated by >
     */
    @Override
    public void spread(DiseaseCentre diseaseCentre) {
        getLocations().add(diseaseCentre);
        double threshold = getSpreadChance() * 10000;
        if (diseaseCentre.getPopDensity() > threshold) {
            setMultiplying(true);
        }
        if (isMultiplying()) {
            if (multHistory.equals("")) {
                multHistory = diseaseCentre.getLocation();
            } else {
                multHistory += " > " + diseaseCentre.getLocation();
            }
        }
    }

    /**
     * method that returns trace and multiplying history
     * if virus is multiplying
     * method is called from the super class
     * @return the trace message
     * */
    @Override
    public String getTrace() {
        String trace = super.getTrace();

        if (isMultiplying()) {
            trace += " From: " + multHistory + ".";
        }

        return trace;
    }
}
