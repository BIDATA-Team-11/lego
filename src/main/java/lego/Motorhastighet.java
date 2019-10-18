/**
 * Hjelpeklasse for å sette motorhastigheter og akselerasjon.
 * @author Stian Selvåg
 * @author Herman Aagaard
 * @author Henrik Hafsø
 * @author Joakim Skogø Langvand
 * @author Erling Sletta
 * @author Torbjørn Øverås
 * @author Gruppe 11, dataingeniør NTNU, første semester.
 * @version 1.1.0
 */

 package lego;

public class Motorhastighet {
    /**
     * Størst hastighet
     */
    public final static int max = 1000; // Full fart framover
    /**
     * Middels hastighet
     */
    public final static int mid = 600; // Svinghastighet (høy)
    /**
     * Laveste hastighet
     */
    public final static int min = 250; // Svinghastighet (lav)

    /*
     * Disse brukes dersom flagget accelrationTest settes i Bil.java.
     *
     * maxAcc brukes når roboten går fra svinghastighet og opp.
     * minAcc brukes når roboten går fra topphastighet ned til svinghastighet.
     */
    /**
     * Størst akelerasjon  hastighet
     */
    public final static int maxAcc = 8000; // Akselrasjon lav->høy
    /**
     * Størst akelerasjon  hastighet
     */
    public final static int minAcc = 4000; // Akselrasjon høy->lav

    /*
     * Disse brukes kun dersom vi bruker kalkulerte hastigheter. max, mid, min vil da bli ignorert.
     *
     * Hver variabel representerer en andel av EV3s rapporterte makshastighet basert på batteri, etc.
     */
    /**
     * Størst hastighet (faktor)
     */
    public final static float maxSpeedFactor = 1.2f;
    /**
     * Middels hastighet (faktor)
     */
    public final static float midSpeedFactor = 0.85f;
    /**
     * Lavest hastighet (faktor)
     */
    public final static float minSpeedFactor = 0.30f;
}
