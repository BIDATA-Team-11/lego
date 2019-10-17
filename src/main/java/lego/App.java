package lego;

import lejos.robotics.RegulatedMotor;
import lejos.robotics.SampleProvider;
import lejos.hardware.Brick;
import lejos.hardware.BrickFinder;
import lejos.hardware.port.Port;
import lejos.utility.Stopwatch;

import lejos.hardware.Button;

/**
 * LejOS Klient for Legobil prosjekt 2019
 * @author Torbjørn Øverås
 * @version 1.0.0
 */
public class App {
    /**
     * Start metoden til klienten. Her får man valget om å printe ut de fargene
     * sensorene ser, kalibrer farge sensorene eller få bilen til å kjøre.
     * @param args argumenter man kan gi main funksjonen. Disse blir ikke tatt
     * i betrakning.
     * @throws Exception om en feil oppstår på EV3 maskinen vil en feil bli
     * kastet.
     */
    public static void main (String[] args) throws Exception {
        Brick brick = BrickFinder.getDefault();

        Port fargePort = brick.getPort("S2");               // Hoved - fargesensor
        Port fargePortKorrigering = brick.getPort("S4");    // Korrigering - fargesensor

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
                printFarge(fargeSensor, fargeKorrigering);
            } else if (knapp == Button.ID_DOWN) {
                fortsett = true;
            }
        } while (!fortsett);
    }

    /**
     * Printer ut til LCD skjer hvilke farger de 2 fargesensorene ser.
     * @param fargeSensor Hoved Farge Sensore fra LejOS API (EV3ColorSensor).
     * @param fargeKorrigering Helpe Farge Sensor (EV3ColorSensor).
     * @see Farge
     */
    public static void printFarge(Farge fargeSensor, Farge fargeKorrigering) {
        float farge = 0;
        float fargeKorr = 0;

        farge = fargeSensor.getFarge();
        fargeKorr = fargeKorrigering.getFarge();

        System.out.printf("%.3f - %.3f\n", farge, fargeKorr);
    }

    /**
     * Starter selve legobilen.
     * @param svart en float verdi som representerer svart fargen.
     * @param hvit en float verdi som representerer hvit fargen.
     * @param fargeSensor hoved farge sensor.
     * @param fargeKorrigering hjelpe farge sensor for korrigering.
     * @param bil klassen som tilby motor egenskaper for legobilen.
     * @see Farge
     * @see Bil
     */
    public static void start(float svart, float hvit, Farge fargeSensor,
            Farge fargeKorrigering, Bil bil) {

        float farge = 0;
        float fargeKorr = 0;

        float fargeSpeed = ((hvit - svart) * 0.65f) + svart;

        int topSpeed = 900;
        int midSpeed = 750;
        int minSpeed = 200;

        int accTopSpeed = 8000;
        int accMinSpeed = 4000;

        Retning retning = Retning.FRAM;

        bil.A.setSpeed(topSpeed);
        bil.C.setSpeed(topSpeed);

        bil.A.setAcceleration(accTopSpeed);
        bil.C.setAcceleration(accTopSpeed);
        Stopwatch timer = new Stopwatch();
        timer.reset();

        while (true) {
            farge = fargeSensor.getFarge();
            fargeKorr = fargeKorrigering.getFarge();

            if (farge > svart) {
                // TODO: Eventuelt fjerne
                bil.A.setAcceleration(accMinSpeed);
                bil.C.setAcceleration(accMinSpeed);

                if (retning == Retning.FRAM) {
                    // Venstre
                    if (fargeKorr > svart) {
                        retning = Retning.VENSTRE;

                        // Høyre
                    } else if (fargeKorr < svart) {
                        retning = Retning.HØYRE;
                    }
                } else  {
                    // Venstre
                    if (retning == Retning.VENSTRE) {
                        if (timer.elapsed() > 20) {
                            if (farge > fargeSpeed) {
                                bil.C.setSpeed(topSpeed);
                                bil.A.setSpeed(minSpeed);
                            } else {
                                bil.C.setSpeed(topSpeed);
                                bil.A.setSpeed(midSpeed);
                            }

                            if (fargeKorr < svart) {
                                retning = Retning.FRAM;
                            }

                            timer.reset();
                        }
                        // Høyre
                    } else if (retning == Retning.HØYRE) {
                        if (farge > fargeSpeed) {
                            bil.A.setSpeed(topSpeed);
                            bil.C.setSpeed(minSpeed);
                        } else {
                            bil.A.setSpeed(topSpeed);
                            bil.C.setSpeed(midSpeed);
                        }
                    }

                }
            } else {
                retning = Retning.FRAM;

                // TODO: Eventuelt fjerne
                bil.A.setAcceleration(accTopSpeed);
                bil.C.setAcceleration(accTopSpeed);

                bil.A.setSpeed(topSpeed);
                bil.C.setSpeed(topSpeed);
            }

            bil.A.forward();
            bil.C.forward();
        }
    }
}
