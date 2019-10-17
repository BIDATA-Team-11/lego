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
 * @author Torbjørn Øverås
 * @author Gruppe 11, dataingeniør NTNU, første semester.
 * @version 1.0.0
 */
public class App {
    /**
     * Main-metode for klienten. Her får man valget om å printe ut de fargene sensorene ser, kalibrere fargesensorene eller å få bilen til å kjøre.
     * @param args argumenter man kan gi main funksjonen. Disse blir ignorert.
     * @throws Exception om en feil oppstår på EV3 maskinen vil en feil bli
     * kastet.
     */
    public static void main (String[] args) throws Exception {
        Brick brick = BrickFinder.getDefault();

        Port fargePort = brick.getPort("S2");               // Hovedfargesensor
        Port fargePortKorrigering = brick.getPort("S4");    // Korrigeringsfargesensor

        Farge mainSensor = new Farge(fargePort);
        Farge correctionSensor = new Farge(fargePortKorrigering);

        Bil bil = new Bil(false);

        boolean fortsett = false;
        float svart = 0;
        float hvit = 0;

        System.out.println("Versjon 1.0.0-awesomebot");
        System.out.println("Ned:    Les farge (debug)");
        System.out.println("Enter:  Start");
        //System.out.println();
        //System.out.println("Debug:");
        //bil.printCalculatedSpeeds();

        do {
            int knapp = Button.waitForAnyEvent();

            if (knapp == Button.ID_RIGHT) {
                svart = mainSensor.kalibrering();
                hvit = correctionSensor.kalibrering();
                System.out.println("Ferdig kalibrert");
            } else if (knapp == Button.ID_LEFT) {
                fortsett = true;
                start(svart, hvit, mainSensor, correctionSensor, bil);
            } else if (knapp == Button.ID_DOWN) {
                mainSensor.printFargeID();
                correctionSensor.printFargeID();
            } else if (knapp == Button.ID_ENTER) {
                start(svart, hvit, mainSensor, correctionSensor, bil);
            }
        } while (!fortsett);
    }

    /**
     * Starter selve legobilen.
     * @param svart Floatverdi som representerer svart.
     * @param hvit Floatverdi som representerer hvit.
     * @param mainSensor Hovedfargesensor. Står midt på fronten på roboten..
     * @param correctionSensor Korrigeringssensor. Står til høyre for hovedfargesensor.
     * @param bil Hjelpeklasse for motorene.
     * @see Farge
     * @see Bil
     */
    public static void start(float svart, float hvit, Farge mainSensor,
            Farge correctionSensor, Bil bil) {

        Retning retning = Retning.FRAM;

        //bil.left.setSpeed(Motorhastighet.max);
        //bil.right.setSpeed(Motorhastighet.max);

        bil.left.setAcceleration(Motorhastighet.maxAcc);
        bil.right.setAcceleration(Motorhastighet.maxAcc);

        Stopwatch timer = new Stopwatch();
        timer.reset();

        boolean corrSensorActivated = false;

        while (true) {
            if (mainSensor.lostLine()) {
                if (bil.getState() == Retning.FRAM || correctionSensor.hasLine()) {
                    if (correctionSensor.hasLine()) {
                      bil.setState(Retning.HØYRE);
                    } else if (!corrSensorActivated) {
                      bil.setState(Retning.VENSTRE);
                    }
                }
            }

            if (mainSensor.hasLine()) {
              bil.setState(Retning.FRAM);
              corrSensorActivated = false;
            }

            if (mainSensor.lostLine()) {
              //if (correctionSensor.lostLine() && bil.getState() == Retning.HØYRE) {
              //  bil.setState(Retning.VENSTRE);
              //}

              if (correctionSensor.hasLine()) {
                bil.setState(Retning.HØYRE);
                corrSensorActivated = true;
              }
            }

            bil.update();

            //while (mainSensor.hasLine());
        }
    }
}
