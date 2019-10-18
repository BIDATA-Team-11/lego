package lego;

import lejos.robotics.RegulatedMotor;
import lejos.robotics.SampleProvider;
import lejos.hardware.Brick;
import lejos.hardware.BrickFinder;
import lejos.hardware.port.Port;
import lejos.utility.Stopwatch;

import lejos.hardware.Button;

/**
 * LejOS Klient for Legobilprosjekt 2019
 * @author Stian Selvåg
 * @author Herman Aagaard
 * @author Henrik Hafsø
 * @author Joakim Skogø Langvand
 * @author Erling Sletta
 * @author Torbjørn Øverås
 * @author Gruppe 11, dataingeniør NTNU, første semester.
 * @version 1.0.0
 */
public class App {
    /**
     * Main-metode for klienten. Her får man valget om å printe ut de fargene sensorene ser, kalibrere fargesensorene eller å få bilen til å kjøre.
     * @param args Argumenter blir ignorert. Heh.
     * @throws Exception Gir EV3 mulighet til å catche eventuelle feil.
     */
    public static void main (String[] args) throws Exception {
        Brick brick = BrickFinder.getDefault();

        /*
        * Oppsett av fargesensorer.
        */
        Port mainSensorPort = brick.getPort("S2");
        Port correctionSensorPort = brick.getPort("S4");
        Farge mainSensor = new Farge(mainSensorPort);
        Farge correctionSensor = new Farge(correctionSensorPort);

        System.out.println("v. 1.0.0-awesomebot");
        System.out.println("Ned:    Les farge (debug)");
        System.out.println("Enter:  Start");

        Bil bil;

        do {
            int knapp = Button.waitForAnyEvent();

            if (knapp == Button.ID_RIGHT) {
            } else if (knapp == Button.ID_LEFT) {
                bil = new Bil(true); // true: kalkulerte hastigheter, false: hardkoda hastigheter.
                start(mainSensor, correctionSensor, bil);
            } else if (knapp == Button.ID_DOWN) {
                mainSensor.printFargeID();
                correctionSensor.printFargeID();
            } else if (knapp == Button.ID_ENTER) {
                bil = new Bil(false); // true: kalkulerte hastigheter, false: hardkoda hastigheter.
                start(mainSensor, correctionSensor, bil);
            }
        } while (true);
    }

    /**
     * Starter selve legobilen.
     * @param mainSensor Hovedfargesensor. Står midt på fronten på roboten..
     * @param correctionSensor Korrigeringssensor. Står til høyre for hovedfargesensor.
     * @param bil Hjelpeklasse for motorene.
     * @see Farge
     * @see Bil
     */
    public static void start(Farge mainSensor, Farge correctionSensor, Bil bil) {

        /*
        * Flagg som indikerer at linja befinner seg mellom sensorene. Dette løser en del problemer
        * med hunting ved at vi unngår å svinge tilbake mot venstre så snart korrigeringssensoren
        * mister linja. Flagget settes med en gang korrigeringssensoren ser linja, og fjernes så
        * snart hovedsensoren finner linja igjen.
        */
        boolean lineIsBetweenSensors = false;

        /*
        * "Hovedløkka" i programmet. Denne kjører til vi dreper den.
        *
        * Her ligger logikken som styrer retning - fram, sving til venstre, sving til høyre.
        */
        while (true) {
            if (mainSensor.lostLine()) {
                if (bil.getState() == Direction.FORWARD || correctionSensor.hasLine()) {
                    if (correctionSensor.hasLine()) {
                      bil.setState(Direction.RIGHT);
                      lineIsBetweenSensors = true;
                    } else if (!lineIsBetweenSensors) {
                      bil.setState(Direction.LEFT);
                    }
                }
            } else {
              bil.setState(Direction.FORWARD);
              lineIsBetweenSensors = false;
            }

            bil.update(); // Gi nye instrukser til motorene.

            while (mainSensor.hasLine()); // Vi trenger ikke sjekke noe så lenge vi ser linja.
        }
    }
}
