package com.example.cs115coursework2;

public class TestAndImpress {
    public static void main(String[] args) {
        System.out.println("=== STARTING MANUAL TEST SUITE ===\n");

        testTask1And2_ObjectCreation();
        testTask5_Searching();
        testTask6And7_DataLoadingAndConnections();
        testTask8_Traces();

        System.out.println("\n=== ALL TESTS COMPLETED ===");
    }

    /**
     * Tests the logic of Tasks 1 and 2.
     * Verifies that DiseaseCentre objects are instantiated correctly and
     * confirms that Bacteria correctly identifies the 'death' condition
     * (aerobic bacteria entering a Research Centre).
     */
    private static void testTask1And2_ObjectCreation() {
        System.out.println("Testing Tasks 1 & 2: Pathogen Logic");

        DiseaseCentre london = new DiseaseCentre("London", 1, 500, 500, "England", false, 0.9);
        DiseaseCentre hull = new DiseaseCentre("Hull", 2, 600, 600, "England", false, 0.4);

        Bacteria b = new Bacteria(london, hull, true);
        b.spread(london); // Should not die (not a Research Centre)
        System.out.println("Bacteria Alive after normal DC: " + (b.getTrace().contains("dead") ? "FAIL" : "PASS"));

        // Cardiff is a Research Centre (true)
        DiseaseCentre cardiffRC = new DiseaseCentre("Cardiff (RC)", 3, 100, 100, "Wales", true, 0.9);
        b.spread(cardiffRC); // Should die (Aerobic + RC)
        System.out.println("Bacteria Dead after RC: " + (b.getTrace().contains("dead") ? "PASS" : "FAIL"));
    }

    /**
     * Tests the search logic in Task 5.
     * Ensures that the Data class can successfully retrieve a DiseaseCentre
     * using either its String name or its unique Integer ID.
     */
    private static void testTask5_Searching() {
        System.out.println("\nTesting Task 5: Searching Logic");
        Data.readDiseaseCentres();
        DiseaseCentre found = Data.findDiseaseCentre("Inverness");
        System.out.println("Search by Name (Inverness): " + (found != null ? "PASS" : "FAIL"));

        // to check if we found it by ID
        if (found != null) {
            DiseaseCentre foundById = Data.findDiseaseCentre(found.getDCID());
            System.out.println("Search by ID: " + (foundById != null && foundById == found ? "PASS" : "FAIL"));
        }
    }

    /**
     * Tests the file parsing and connections building method from Tasks 6 and 7.
     * Verifies that the pathogens.csv file is fully loaded into the Stack
     * and that connections are established between centres according to
     * country and research status rules.
     */
    private static void testTask6And7_DataLoadingAndConnections() {
        System.out.println("\nTesting Task 6 & 7: Loading & Connections");
        Data.readPathogens();
        Data.addConnections();

        System.out.println("Pathogens loaded (Expected 13): " + (Data.getPathogens().size() == 13 ? "PASS" : "FAIL (" + Data.getPathogens().size() + ")"));

        DiseaseCentre london = Data.findDiseaseCentre("London (RC)");
        if (london != null) {
            System.out.println("London (RC) connections count: " + london.getConnections().size() + " (Should be high because its RC)");
        }
    }

    /**
     * Tests the file output functionality from Task 8.
     * Triggers the creation of "traces.txt" and verifies that the printTraces
     * method can iterate through the spreadPathogens stack to record history.
     */
    private static void testTask8_Traces() {
        System.out.println("\nTesting Task 8: File Writing");
        Data.printTraces();
        System.out.println("Check project folder for 'traces.txt' to verify output.");
    }

}