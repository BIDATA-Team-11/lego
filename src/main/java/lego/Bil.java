package lego;

import lejos.hardware.port.Port;
import lejos.robotics.MirrorMotor;
import lejos.robotics.RegulatedMotor;
import lejos.hardware.motor.Motor;

/**
 * Hjelpeklasse som speiler motorene. Bedrer lesbarheten.
 * @author TorbjÃ¸rn Ã˜verÃ¥s
 * @version 1.0.0
 */
public class Bil {
    RegulatedMotor A;
    RegulatedMotor C;

    /**
     * Reverserer motorene. Det som er fram er nÃ¥ bak og det som er hÃ¸yre er nÃ¥ venstre.
     */
    public Bil() {
        A = MirrorMotor.invertMotor(Motor.A);  // Inverter motorene på grunn av omdreiningen av våres kjøretøy for å
        C = MirrorMotor.invertMotor(Motor.C);  // Gjøre det lettere for oss å kode ved å bruke rett retning
    }
}
