package lego;

import lejos.hardware.port.Port;
import lejos.robotics.MirrorMotor;
import lejos.robotics.RegulatedMotor;
import lejos.hardware.motor.*;

public class Bil {
    RegulatedMotor A;
    RegulatedMotor C;

    public Bil() {
        A = MirrorMotor.invertMotor(Motor.A);  // Inverter motorene p� grunn av omdreiningen av v�res kj�ret�y for �
        C = MirrorMotor.invertMotor(Motor.C);  // Gj�re det lettere for oss � kode ved � bruke rett retning
    }
}
