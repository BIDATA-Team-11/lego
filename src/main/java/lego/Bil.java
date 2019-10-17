package lego;

import lejos.hardware.port.Port;
import lejos.robotics.MirrorMotor;
import lejos.robotics.RegulatedMotor;
import lejos.hardware.motor.Motor;

/**
 * Hjelpeklasse som speiler motorene. Bedrer lesbarheten.
 * @author Torbjørn Øverås
 * @version 1.0.0
 */
public class Bil {
    RegulatedMotor A;
    RegulatedMotor C;

    /**
     * Reverserer motorene. Det som er fram er nå bak og det som er høyre er nå venstre.
     */
    public Bil() {
        A = MirrorMotor.invertMotor(Motor.A);  // Inverter motorene p� grunn av omdreiningen av v�res kj�ret�y for �
        C = MirrorMotor.invertMotor(Motor.C);  // Gj�re det lettere for oss � kode ved � bruke rett retning
    }
}
