package lego;

import lejos.hardware.port.Port;
import lejos.robotics.MirrorMotor;
import lejos.robotics.RegulatedMotor;
import lejos.hardware.motor.*;

/**
 * Bil klassen representerer motor egenskapene til en EV3 maskin fra LejOS
 * Api'et.
 * @author Torbjørn Øverås
 * @version 1.0.0
 */
public class Bil {
    RegulatedMotor A;
    RegulatedMotor C;

    /**
     * Reverserer motorene. Det som er fram er nå bak og det som er høyre er nå
     * venstre
     */
    public Bil() {
        A = MirrorMotor.invertMotor(Motor.A);
        C = MirrorMotor.invertMotor(Motor.C);
    }
}
