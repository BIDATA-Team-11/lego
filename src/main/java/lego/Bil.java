package lego;

import lejos.hardware.port.Port;
import lejos.robotics.MirrorMotor;
import lejos.robotics.RegulatedMotor;
import lejos.hardware.motor.*;

public class Bil {
    RegulatedMotor A;
    RegulatedMotor C;

    public Bil() {
        A = MirrorMotor.invertMotor(Motor.A);
        C = MirrorMotor.invertMotor(Motor.C);
    }
}
