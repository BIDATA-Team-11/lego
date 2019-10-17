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
    Retning newState;

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

      System.out.println("FORWARD");
    }

    public void leftTurn() {
        this.recalculateSpeeds();
        left.setSpeed(minSpeed);
        right.setSpeed(midSpeed);
        left.forward();
        //left.flt();
        right.forward();

        System.out.println("LEFT");
    }

    public void rightTurn() {
        this.recalculateSpeeds();
        left.setSpeed(midSpeed);
        right.setSpeed(minSpeed);
        left.forward();
        right.forward();
        //right.flt();

        System.out.println("RIGHT");
    }

    public void update() {
        /*switch(state) {
          case FRAM:    this.forward(); break;
          case VENSTRE: this.leftTurn(); break;
          case HØYRE:   this.rightTurn(); break;
        }*/
        if (this.state == Retning.FRAM) {
          this.forward();
        } else if (this.state == Retning.VENSTRE) {
          this.leftTurn();
        } else if (this.state == Retning.HØYRE) {
          this.rightTurn();
        }
    }

    public void setState(Retning state) {
      this.state = state;
    }

    public Retning getState() {
      return this.state;
    }

    public void printCalculatedSpeeds() {
      this.recalculateSpeeds();

      System.out.println("Max: " + this.maxSpeed);
      System.out.println("Mid: " + this.midSpeed);
      System.out.println("Min: " + this.minSpeed);
    }
}
