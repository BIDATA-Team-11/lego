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

        Farge fargeSensor = new Farge(fargePort);
        Farge fargeKorrigering = new Farge(fargePortKorrigering);

        Bil bil = new Bil();

        boolean fortsett = false;
        float svart = 0;
        float hvit = 0;

        System.out.println("Da kan du velge!");
        do {
            int knapp = Button.waitForAnyEvent();

            if (knapp == Button.ID_RIGHT) {
                svart = fargeSensor.kalibrering();
                hvit = fargeKorrigering.kalibrering();
                System.out.println("Ferdig kalibrert");
            } else if (knapp == Button.ID_LEFT) {
                fortsett = true;
                start(svart, hvit, fargeSensor, fargeKorrigering, bil);
            }else if (knapp == Button.ID_UP) {
                //printFarge(fargeSensor, fargeKorrigering);
            } else if (knapp == Button.ID_DOWN) {
                fortsett = true;
            } else if (knapp == Button.ID_ENTER) {
                fargeSensor.printFargeID();
            }
        } while (!fortsett);
    }

    /**
     * Starter selve legobilen.
     * @param svart Floatverdi som representerer svart.
     * @param hvit Floatverdi som representerer hvit.
     * @param fargeSensor Hovedfargesensor. Står midt på fronten på roboten..
     * @param fargeKorrigering Korrigeringssensor. Står til høyre for hovedfargesensor.
     * @param bil Hjelpeklasse for motorene.
     * @see Farge
     * @see Bil
     */
    public static void start(float svart, float hvit, Farge fargeSensor,
            Farge fargeKorrigering, Bil bil) {

        Retning retning = Retning.FRAM;

        bil.A.setSpeed(Motorhastighet.max);
        bil.C.setSpeed(Motorhastighet.max);

        bil.A.setAcceleration(Motorhastighet.maxAcc);
        bil.C.setAcceleration(Motorhastighet.maxAcc);
        Stopwatch timer = new Stopwatch();
        timer.reset();

        while (true) {
            if (!fargeSensor.erSvart()) {
                // TODO: Eventuelt fjerne
                bil.A.setAcceleration(Motorhastighet.minAcc);
                bil.C.setAcceleration(Motorhastighet.minAcc);

                if (retning == Retning.FRAM) {
                    if (!fargeKorrigering.erSvart()) {
                        retning = Retning.VENSTRE;
                    } else {
                        retning = Retning.HØYRE;
                    }
                } else  {
                    if (retning == Retning.VENSTRE) {
                        if (timer.elapsed() > 20) {
                            if (fargeSensor.erUbestemt()) {
                                bil.C.setSpeed(Motorhastighet.max);
                                bil.A.setSpeed(Motorhastighet.min);
                            } else {
                                bil.C.setSpeed(Motorhastighet.max);
                                bil.A.setSpeed(Motorhastighet.mid);
                            }

                            if (fargeKorrigering.erSvart()) {
                                retning = Retning.FRAM;
                            }

                            timer.reset();
                        }
                    } else if (retning == Retning.HØYRE) {
                        if (fargeSensor.erUbestemt()) {
                            bil.A.setSpeed(Motorhastighet.max);
                            bil.C.setSpeed(Motorhastighet.min);
                        } else {
                            bil.A.setSpeed(Motorhastighet.max);
                            bil.C.setSpeed(Motorhastighet.mid);
                        }
                    }

                }
            } else {
                retning = Retning.FRAM;

                // TODO: Eventuelt fjerne
                bil.A.setAcceleration(Motorhastighet.maxAcc);
                bil.C.setAcceleration(Motorhastighet.maxAcc);

                bil.A.setSpeed(Motorhastighet.max);
                bil.C.setSpeed(Motorhastighet.max);
            }

            bil.A.forward();
            bil.C.forward();
        }
    }
}
