package com.example.cs115coursework2;

import java.text.DecimalFormat;
import java.util.ArrayList;

/**
 * Class to represent a Sorting Office object.
 * You will heavily edit this class and therefore no further comments have been added.
 * Ensure you add comments when submitting.
 */
public class DiseaseCentre {

    private String cityName;
    private int dcid;
    private int x;
    private int y;
    private String country;
    private boolean research;
    private double popDensity;
    private ArrayList<DiseaseCentre> connections;

    /**
     * a constructor that initializes all the attributes for the DiseaseCentre class
     */
    public DiseaseCentre (String cityName, int dcid, int x, int y, String country,
                          boolean research, double popDensity) {
        this.cityName = cityName;
        this.dcid = dcid;
        this.x = x;
        this.y = y;
        this.country = country;
        this.research = research;
        this.popDensity = popDensity;
        this.connections = new ArrayList<>();
    }

    /**
     * a method that adds a centre to an array list called Connections
     * @param anotherCentre is the other centre being added
     * */
    public void addConnection (DiseaseCentre anotherCentre) {
        this.connections.add(anotherCentre);
    }

    /**
     * a method that returns location of the city and adds "(RC)" if its a disease centre
     * @Return city name
     * */
    public String getLocation () {
        if (research) {
            return cityName + " (RC)";
        } else {
            return cityName;
        }
    }

    public String getCountry () {
        return country;
    }

    /**
     * a method that returns location detail like x and y coordinates and country
     * @return coordinates and country
     * */
    public String getLocationDetail () {
        return "X" + x + "Y" + y + "C" + country;
    }

    /**
     * a getter method that returns the x coordinate
     * @return x the coordinate
     * */
    public int getX() {
        return x;
    }

    /**
     * a getter method that returns the y coordinate
     * @return y the coordinate
     * */
    public int getY() {
        return y;
    }

    /**
     * a getter method that returns True or False for research
     * @return research the boolean
     * */
    public boolean isResearch() {
        return research;
    }

    /**
     * a getter method that returns the array list of the disease centre connected
     * @return connections the array list
     * */
    public ArrayList<DiseaseCentre> getConnections() {
        return connections;
    }

    /**
     * a private method only to be used by the toString method,
     * it formats population density to match what spec wants
     * @Return the formatted string
     * */
    private String formattedDensity () {
        return String.format("%,.2f", popDensity);
    }

    /**
     * a toString method that returns a string of info about the object
     * @return the final string
     * */
    public String toString () {
        return getLocation() + " " + getLocationDetail() + " " + formattedDensity();
    }

    public double getPopDensity() {
        return  popDensity;
    }

    public int getDCID() {
        return dcid;
    }

    public String getCityName() {
        return cityName;
    }
}
