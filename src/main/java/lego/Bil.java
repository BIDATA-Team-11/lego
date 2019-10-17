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

    boolean actualMax; // Whether to use calculated speeds or speeds set in config

    float maxSpeed;
    float midSpeed;
    float minSpeed;

    Retning state;
    Retning newState;

    public Bil(boolean actualMax) {
        left = MirrorMotor.invertMotor(Motor.A);  // Inverter motorene p* grunn av omdreiningen av v*res kj*ret*y for *
        right = MirrorMotor.invertMotor(Motor.C);  // Gj*re det lettere for oss * kode ved * bruke rett retning
        this.actualMax = actualMax;
        state = Retning.FRAM;
        left.setAcceleration(Motorhastighet.maxAcc);
        right.setAcceleration(Motorhastighet.maxAcc);
    }

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

      left.setSpeed((int)maxSpeed);
      right.setSpeed((int)maxSpeed);
      left.forward();
      right.forward();

      System.out.println("FORWARD");
    }

    public void leftTurn() {
        this.recalculateSpeeds();
        left.setSpeed((int)minSpeed);
        right.setSpeed((int)midSpeed);
        left.forward();
        right.forward();

        System.out.println("LEFT");
    }

    public void rightTurn() {
        this.recalculateSpeeds();
        left.setSpeed((int)midSpeed);
        right.setSpeed((int)minSpeed);
        left.forward();
        right.forward();

        System.out.println("RIGHT");
    }

    public void update() {
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
