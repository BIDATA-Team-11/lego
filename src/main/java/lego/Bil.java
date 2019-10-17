package lego;

import lejos.hardware.port.Port;
import lejos.robotics.MirrorMotor;
import lejos.robotics.RegulatedMotor;
import lejos.hardware.motor.Motor;

/**
 * Klasse som beskriver motorene og inneholder alle metoder for disse.
 * @author Stian Selvåg
 * @author Herman Aagaard
 * @author Henrik Hafsø
 * @author Joakim Skogø Langvand
 * @author Erling Sletta
 * @author Torbjørn Øverås
 * @author Gruppe 11, dataingeniør NTNU, første semester.
 * @version 1.0.0
 */
public class Bil {
    RegulatedMotor left;
    RegulatedMotor right;

    boolean actualMax; // Flagg som bestemmer om vi kalkulerer hastigheter eller bruker hardkoding.

    float maxSpeed;
    float midSpeed;
    float minSpeed;

    Direction state;
    Direction newState;

    boolean accelrationTest;

    /**
     * Konstuerer en ny Bil.
     * @param actualMax Boolean som bestemmer om vi skal bruke kalkulerte hastigheter.
     */
    public Bil(boolean actualMax) {
        /*
        * Inverterer motorene, siden de er montert motsatt av hva som er tiltenkt i APIet.
        */
        left = MirrorMotor.invertMotor(Motor.A);
        right = MirrorMotor.invertMotor(Motor.C);

        this.actualMax = actualMax; // Bruker kalkulerte verdier hvis satt til true.
        state = Direction.FORWARD;
        left.setAcceleration(Motorhastighet.maxAcc);
        right.setAcceleration(Motorhastighet.maxAcc);

        accelrationTest = false;
    }

    /**
     * Rekalkulerer hastigheter. Denne er aktuell dersom actualMax ser satt til true.
     * @see maxSpeed
     * @see midSpeed
     * @see minSpeed
     * @see left
     * @see right
     */
    private void recalculateSpeeds() {
      if (actualMax) {
        int maxLeft = (int)left.getMaxSpeed();
        int maxRight = (int)right.getMaxSpeed();
        this.maxSpeed = maxLeft < maxRight ? maxLeft : maxRight;
        this.midSpeed = this.maxSpeed * Motorhastighet.midSpeedFactor;
        this.minSpeed = this.maxSpeed * Motorhastighet.minSpeedFactor;
      } else {
        this.maxSpeed = Motorhastighet.max;
        this.midSpeed = Motorhastighet.mid;
        this.minSpeed = Motorhastighet.min;
      }
    }

    public void forward() {
      this.recalculateSpeeds();

      if (accelrationTest) {
          left.setAcceleration(Motorhastighet.maxAcc);
          right.setAcceleration(Motorhastighet.maxAcc);
      }

      this.left.setSpeed((int)maxSpeed);
      this.right.setSpeed((int)maxSpeed);
      this.left.forward();
      this.right.forward();

      System.out.println("FORWARD");
    }

    public void leftTurn() {
        this.recalculateSpeeds();

        if (accelrationTest) {
          this.setAcceleration(Motorhastighet.minAcc);
        }

        this.left.setSpeed((int)minSpeed);
        this.right.setSpeed((int)midSpeed);
        this.left.forward();
        this.right.forward();

        System.out.println("LEFT");
    }

    public void rightTurn() {
        this.recalculateSpeeds();

        if (accelrationTest) {
          this.setAcceleration(Motorhastighet.minAcc);
        }

        this.left.setSpeed((int)midSpeed);
        this.right.setSpeed((int)minSpeed);
        this.left.forward();
        this.right.forward();

        System.out.println("RIGHT");
    }

    private void setAcceleration(int accelration) {
      this.left.setAcceleration(accelration);
      this.right.setAcceleration(accelration);
    }

    public void update() {
        if (this.state == Direction.FORWARD) {
          this.forward();
        } else if (this.state == Direction.LEFT) {
          this.leftTurn();
        } else if (this.state == Direction.RIGHT) {
          this.rightTurn();
        }
    }

    public void setState(Direction state) {
      this.state = state;
    }

    public Direction getState() {
      return this.state;
    }

    public void printCalculatedSpeeds() {
      this.recalculateSpeeds();

      System.out.println("Max: " + this.maxSpeed);
      System.out.println("Mid: " + this.midSpeed);
      System.out.println("Min: " + this.minSpeed);
    }
}
