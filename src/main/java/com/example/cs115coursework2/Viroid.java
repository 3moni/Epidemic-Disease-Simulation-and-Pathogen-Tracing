package com.example.cs115coursework2;

import java.util.ArrayList;

public abstract class Viroid implements Pathogen {

    private DiseaseCentre epicentre;
    private DiseaseCentre endpoint;
    protected double spreadChance;
    private boolean multiplying;
    private ArrayList<DiseaseCentre> locations;

    /**
     * Constructor to set the attributes for each Viroid while setting multiplying to always false
     * */
    public Viroid(DiseaseCentre epicentre, DiseaseCentre endpoint, double spreadChance) {
        this.epicentre = epicentre;
        this.endpoint = endpoint;
        this.spreadChance = spreadChance;
        this.multiplying = false;
        this.locations = new ArrayList<>();

    }

    /**
     * stub method to implement method defined in the Pathogen interface
     * @param diseaseCentre the disease centre
     * */
    public void spread(DiseaseCentre diseaseCentre) {

    }


    /**
     * Gets the starting location of this Viroid.
     * @return the epicentre disease centre.
     */
    public DiseaseCentre getEpicentre() {
        return epicentre;
    }

    /**
     * Gets the end location of this Viroid.
     * @return the endpoint disease centre.
     */
    public DiseaseCentre getEndPoint() {
        return endpoint;
    }

    /**
     * @return method that returns trace
     * */
    @Override
    public String getTrace() {
        String type = this.getClass().getSimpleName();
        String trace = type + " spread to " + endpoint.getLocation() + ".";

        if (isMultiplying()) {
            trace += " WARNING! " + type + " is multiplying!";
        }

        return trace;
    }

    /**
     * Gets the spread chance of this viroid.
     * @return the spread chance.
     */
    public double getSpreadChance() {
        return spreadChance;
    }

    /**
     * tells weather this viroid multiplies or not.
     * @return True or False.
     */
    public boolean isMultiplying() {
        return multiplying;
    }

    /**
     * setts weather this viroid multiplies or not.
     * @param multiplying true or false input.
     */
    public void setMultiplying(boolean multiplying) {
        this.multiplying = multiplying;
    }

    /**
     * Gets the locations where this viroid spread in an Array list.
     * @return returns an Array list.
     */
    public ArrayList<DiseaseCentre> getLocations() {
        return locations;
    }
}
