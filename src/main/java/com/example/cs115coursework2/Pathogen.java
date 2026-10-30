package com.example.cs115coursework2;

/**
 * DO NOT EDIT
 * Interface to represent a Pathogen object.
 * You will not need to edit this class at all.
 */
public interface Pathogen {
    public void spread(DiseaseCentre diseaseCentre);
    public DiseaseCentre getEpicentre();
    public DiseaseCentre getEndPoint();
    public String getTrace();
}
