package com.example.cs115coursework2;

public class Bacteria implements Pathogen {

    private DiseaseCentre epicentre;
    private DiseaseCentre endpoint;
    private final boolean aerobic;
    private boolean alive;

    /**
     * Constructor to set the attributes for each bacteria while setting alive to always true
     * */
    public Bacteria (DiseaseCentre epicentre, DiseaseCentre endpoint, boolean aerobic) {
        this.epicentre = epicentre;
        this.endpoint = endpoint;
        this.aerobic = aerobic;
        this.alive = true;
    }

    /**
     * method that checks if an aerobic bacteria spreads to a reserach disease centre,
     * if so then they're killed (not alive)
     * @param diseaseCentre the disease centre
     * */
    @Override
    public void spread(DiseaseCentre diseaseCentre) {
        if (aerobic && diseaseCentre.isResearch()) {
            alive = false;
        }
    }

    /**
     * Gets the starting location of this bacterium.
     * @return the epicentre disease centre.
     */
    @Override
    public DiseaseCentre getEpicentre() {
        return epicentre;
    }

    /**
     * Gets the end location of this bacterium.
     * @return the endpoint disease centre.
     */
    @Override
    public DiseaseCentre getEndPoint() {
        return endpoint;
    }

    /**
     * method that returns trace, message changes if
     * bacteria is aerobic or not, multiplying or not
     * @return the trace message
     * */
    @Override
    public String getTrace() {
        String trace = "Bacteria spread to " + endpoint.getLocation() + ".";

        if (aerobic) {
            trace = "Aerobic bacteria spread to " + endpoint.getLocation() + ".";
        }

        if (!alive) {
            trace += " Bacteria is dead.";
        }

        return trace;
    }

    /**
     * @return method that returns info about bacteria
     * */
    public String toString() {
        return getTrace() + " " + getEndPoint() + " " + getEpicentre();
    }

}
