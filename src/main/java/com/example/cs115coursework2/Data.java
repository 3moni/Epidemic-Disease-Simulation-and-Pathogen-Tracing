package com.example.cs115coursework2;


import java.util.ArrayList;
import java.util.Stack;
import java.util.Scanner;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.io.IOException;

/**
 * Class to hold data that is added to the "database".
 * You may add methods to this class.
 */
public class Data {

    /**
     * Attributes to save our data to the "database"
     */
    private static ArrayList<DiseaseCentre> diseaseCentres = new ArrayList<>();
    private static Stack<Pathogen> pathogens = new Stack<>();
    private static Stack<Pathogen> spreadPathogens = new Stack<>();

    /**
     * Method to return the Stack of pathogens
     *
     * @return stack of pathogens
     */
    public static Stack<Pathogen> getPathogens() {
        return pathogens;
    }

    /**
     * Method to return the disease centres
     *
     * @return array list of disease centres
     */
    public static ArrayList<DiseaseCentre> getDiseaseCentres() {
        return diseaseCentres;
    }

    /**
     * Method to return the pathogens which are already spread.
     *
     * @return stack of completed spread pathogens.
     */
    public static Stack<Pathogen> getSpreadPathogens() {
        return spreadPathogens;
    }

    /** DO NOT EDIT ANY CODE ABOVE THIS COMMENT. You may need to write additional methods below. **/

    /**
     * Method to search for disease centre by city name
     *
     * @param cityName the name of the city to look for
     * @return a disease centre object if search mached
     */
    public static DiseaseCentre findDiseaseCentre(String cityName) {

        for (DiseaseCentre diseaseCentre : diseaseCentres) {
            if (diseaseCentre.getCityName().equals(cityName)) {
                return diseaseCentre;
            }
        }
        return null;
    }

    /**
     * Method to search for disease centre by ID
     *
     * @param centreID the ID of the city to look for
     * @return a disease centre object if search mached
     */
    public static DiseaseCentre findDiseaseCentre(int centreID) {

        for (DiseaseCentre diseaseCentre : diseaseCentres) {
            if (diseaseCentre.getDCID() == centreID) {
                return diseaseCentre;
            }
        }
        return null;
    }

    /**
     * Method that reads a disease centres .txt file
     * and creates a DiseaseCentre object out of each line
     * before saving it into the diseaseCentres Arraylist
     */
    public static void readDiseaseCentres() {
        try {
            File myFile = new File("disease-centres.txt");
            Scanner reader = new Scanner(myFile);

            while (reader.hasNextLine()) {

                String line = reader.nextLine();

                String[] parts = line.split(" ");

                String cityName = parts[0];
                int id = Integer.parseInt(parts[1]);
                int x = Integer.parseInt(parts[2]);
                int y = Integer.parseInt(parts[3]);
                boolean research = Boolean.parseBoolean(parts[4]);
                String country = parts[5];

                //logic to seperate last part into 2 different parts,
                //sets int local variables for each to calculate population density
                //before saving it into the original array
                String[] popToAreaSplit = parts[6].split(";");
                double population = Double.parseDouble(popToAreaSplit[0]);
                double area = Double.parseDouble(popToAreaSplit[1]);
                double popDensity = (double) population / area;

                DiseaseCentre dc = new DiseaseCentre(cityName, id, x, y,
                        country, research, popDensity);
                diseaseCentres.add(dc);
            }

            reader.close();

        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Reads pathogen data from "pathogens.csv"
     * and pushes the created objects onto the pathogens stack
     */
    public static void readPathogens() {
        try {
            File myFile = new File("pathogens.csv");
            Scanner reader = new Scanner(myFile);

            while (reader.hasNextLine()) {
                String line = reader.nextLine();
                String[] parts = line.split(",");

                //stores first the parts of each line as strings for starters
                String type = parts[0];
                String stringEpicentre = parts[1];
                String stringEndpoint = parts[2];

                //logic that searches for Disease centre using ID first
                //if it fails it searches with string instead
                DiseaseCentre objEpicentre = null;
                DiseaseCentre objEndpoint = null;

                try {
                    int epiID = Integer.parseInt(stringEpicentre);
                    objEpicentre = findDiseaseCentre(epiID);
                } catch (NumberFormatException e) {
                    objEpicentre = findDiseaseCentre(stringEpicentre);
                }

                try {
                    int endID = Integer.parseInt(stringEndpoint);
                    objEndpoint = findDiseaseCentre(endID);
                } catch (NumberFormatException e) {
                    objEndpoint = findDiseaseCentre(stringEndpoint);
                }


            //logic that checks and stores Aerobic(true or false) for bacteria
            //and spread chance for viroids
            boolean isAerobic = false;
            double spreadChance = 0;
            if (type.equalsIgnoreCase("bacteria")) {
                isAerobic = Boolean.parseBoolean(parts[3]);
            } else {
                spreadChance = Double.parseDouble(parts[3]);
            }

            //logic that creates each objects, saves it to local variable p
            //to add it to the already initialised stack of pathogens
            Pathogen p;
            if (type.equalsIgnoreCase("Bacteria")) {
                p = new Bacteria(objEpicentre, objEndpoint, isAerobic);

            } else if (type.equalsIgnoreCase("Virus")) {
                p = new Virus(objEpicentre, objEndpoint, spreadChance);

            } else if (type.equalsIgnoreCase("Phage")) {
                p = new Phage(objEpicentre, objEndpoint, spreadChance);

            } else {
                p = null;
            }

            if (p != null) {
                pathogens.push(p);
            }

        }

            reader.close();

        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Creates a connection between Disease Centres only if:
     * they are in the same country OR
     * if both centres are research centres
     */
    public static void addConnections() {
        for (DiseaseCentre prevDc : diseaseCentres) {
            for (DiseaseCentre dc : diseaseCentres) {
                if (prevDc.getDCID() != dc.getDCID()) {
                    if (prevDc.getCountry().equalsIgnoreCase(dc.getCountry()) ||
                            (prevDc.isResearch() && dc.isResearch())) {

                        prevDc.addConnection(dc);
                    }
                }
            }
        }
    }

    /**
     * Prints traces of all the pathogens that began spreading
     * to traces.txt
     * Each pathogens trace is written on a new line and the file
     * is overwritten each time the method is called
     */
    public static void printTraces() {
        try {
            PrintWriter writer = new PrintWriter(new FileWriter("traces.txt"));

            for (Pathogen p : getSpreadPathogens()) {
                writer.println(p.getTrace());
            }

            writer.close();

        } catch (IOException e) {
            System.out.println("An error occurred while writing the traces file.");
            e.printStackTrace();
        }
    }

}
