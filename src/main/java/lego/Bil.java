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
    RegulatedMotor left;
    RegulatedMotor right;

    boolean actualMax;

    int maxSpeed;
    int midSpeed;
    int minSpeed;

    Retning state;

    RegulatedMotor[] synchronizedMotors;

    /**
     * Reverserer motorene. Det som er fram er nå bak og det som er høyre er nå venstre.
     */
    public Bil(boolean actualMax) {
        left = MirrorMotor.invertMotor(Motor.A);  // Inverter motorene p* grunn av omdreiningen av v*res kj*ret*y for *
        right = MirrorMotor.invertMotor(Motor.C);  // Gj*re det lettere for oss * kode ved * bruke rett retning
//        synchronizedMotors = new RegulatedMotor[2];
//        synchronizedMotors[0] = left;
//        synchronizedMotors[1] = right;
//        left.synchronizeWith(synchronizedMotors);
        this.actualMax = actualMax;
        state = Retning.FRAM;
    }

/*    private void sync() {
      this.left.startSynchronization();
    }

    public void desync() {
      this.left.endSynchronization();
    }*/

    private void recalculateSpeeds() {
      if (actualMax) {
        int maxLeft = (int)left.getMaxSpeed();
        int maxRight = (int)right.getMaxSpeed();
        this.maxSpeed = maxLeft < maxRight ? maxLeft : maxRight;
        this.midSpeed = (int)(this.maxSpeed * (Motorhastighet.midSpeedPercentage/100));
        this.minSpeed = (int)(this.maxSpeed * (Motorhastighet.minSpeedPercentage/100));
      } else {
        this.maxSpeed = Motorhastighet.max;
        this.midSpeed = Motorhastighet.mid;
        this.minSpeed = Motorhastighet.min;
      }
    }

    public void forward() {
      this.recalculateSpeeds();

      left.setSpeed(maxSpeed);
      right.setSpeed(maxSpeed);
      left.forward();
      right.forward();
    }

    public void leftTurn() {
      if (state != Retning.VENSTRE) {
        this.recalculateSpeeds();
        left.setSpeed(midSpeed);
        left.forward();
      } else {
        state = Retning.VENSTRE;
      }
    }

    public void rightTurn() {
      if (state != Retning.HØYRE) {
        this.recalculateSpeeds();
        right.setSpeed(midSpeed);
        right.forward();
      } else {
        state = Retning.HØYRE;
      }
    }

    public void printCalculatedSpeeds() {
      this.recalculateSpeeds();

      System.out.println("Max: " + this.maxSpeed);
      System.out.println("Mid: " + this.midSpeed);
      System.out.println("Min: " + this.minSpeed);
    }
}
