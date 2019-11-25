// Copyright (c) 2019 Several authors; see javadoc comment
//
// GNU GENERAL PUBLIC LICENSE
//    Version 3, 29 June 2007
//
// This program is free software: you can redistribute it and/or modify
// it under the terms of the GNU General Public License as published by
// the Free Software Foundation, either version 3 of the License, or
// (at your option) any later version.
//
// This program is distributed in the hope that it will be useful,
// but WITHOUT ANY WARRANTY; without even the implied warranty of
// MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
// GNU General Public License for more details.
//
// You should have received a copy of the GNU General Public License
// along with this program.  If not, see <http://www.gnu.org/licenses/>.

/**
 * Klasse som beskriver motorene og inneholder alle metoder for disse.
 * @author Stian Selvåg
 * @author Herman Aagaard
 * @author Henrik Hafsø
 * @author Joakim Skogø Langvand
 * @author Erling Sletta
 * @author Torbjørn Øverås
 * @author Gruppe 11, dataingeniør NTNU, første semester.
 * @version 1.1.0
 */

package lego;

import lejos.robotics.MirrorMotor;
import lejos.robotics.RegulatedMotor;
import lejos.hardware.motor.Motor;
import lejos.robotics.navigation.MovePilot;

/**
 * Klasse som abstraherer bilen. Forenkler programmering og bedrer lesbarhet.
 */
public class Car {
  private RegulatedMotor left;
  private RegulatedMotor right;

  private MovePilot pilot;

  private int leftRadius = 20;
  private int rightRadius = -60;

  /*
   * Flagg som bestemmer om vi kalkulerer hastigheter eller bruker hardkoding.
   */
  private boolean actualMax;

  private float maxSpeed;
  private float midSpeed;
  private float minSpeed;

  private Direction state;
  private Direction lastState;

  /**
   * Konstuerer en ny Car.
   *
   * @param actualMax Boolean som bestemmer om vi skal bruke kalkulerte
   *                  hastigheter.
   */
  public Car(boolean actualMax) {
    /*
     * Inverterer motorene, siden de er montert motsatt av hva som er tiltenkt i
     * APIet.
     */
    // left = MirrorMotor.invertMotor(Motor.B);
    // right = MirrorMotor.invertMotor(Motor.C);

    // Wheel wheel1 = WheeledChassis.modelWheel(Motor.B, 3.0).offset(-8.6);
    // Wheel wheel2 = WheeledChassis.modelWheel(Motor.C, 3.0).offset(8.6);
    // Chassis chassis = (Chassis) new WheeledChassis(new Wheel[] { wheel1, wheel2
    // }, WheeledChassis.TYPE_DIFFERENTIAL);
    // this.pilot = new MovePilot(chassis);
    this.pilot = new MovePilot(3.0, 8.6 * 2, left, right);

    // Set linear speed to EV3s reported max speed. Alkaline batteries will provide
    // higher potential speeds.
    pilot.setLinearSpeed(pilot.getMaxLinearSpeed());

    this.actualMax = actualMax; // Bruker kalkulerte verdier hvis satt til true.

    // Initialise direction states.
    this.state = Direction.FORWARD;
    this.lastState = state;

    left.setAcceleration(SpeedSettings.maxAcc);
    right.setAcceleration(SpeedSettings.maxAcc);
  }

  /**
   * Rekalkulerer hastigheter. Denne er aktuell dersom actualMax ser satt til
   * true.
   */
  private void recalculateSpeeds() {
    if (actualMax) {
      int maxLeft = (int) left.getMaxSpeed();
      int maxRight = (int) right.getMaxSpeed();
      this.maxSpeed = maxLeft < maxRight ? maxLeft : maxRight;
      this.midSpeed = this.maxSpeed * SpeedSettings.midSpeedFactor;
      this.minSpeed = this.maxSpeed * SpeedSettings.minSpeedFactor;
    } else {
      this.maxSpeed = SpeedSettings.max;
      this.midSpeed = SpeedSettings.mid;
      this.minSpeed = SpeedSettings.min;
    }
  }

  /**
   * Setter motorene til å gå framover. Full hastighet.
   */
  public void forward() {
    this.pilot.forward();

    System.out.println("FORWARD");
  }

  /**
   * Setter motorene til å svinge mot venstre.
   */
  public void leftTurn() {
    this.pilot.arcForward(leftRadius);

    System.out.println("LEFT");
  }

  /**
   * Setter motorene til å svinge mot høyre.
   */
  public void rightTurn() {
    this.pilot.arcForward(rightRadius);

    System.out.println("RIGHT");
  }

  /**
   * Sjekker status og oppdaterer motorer deretter.
   */
  public void update() {
    if (this.lastState != this.state) {
      if (this.state == Direction.FORWARD) {
        this.forward();
      } else if (this.state == Direction.LEFT) {
        this.leftTurn();
      } else if (this.state == Direction.RIGHT) {
        this.rightTurn();
      }

      this.lastState = this.state;
    }
  }

  /**
   * Setter status for objektet, ingen forandring før update() kalles.
   *
   * @param state Status i form av en Direction-verdi.
   */
  public void setState(Direction state) {
    // TODO: Dersom vi bruker f.eks. state og nextState kan vi roe ned på API-calls
    // til motorer.
    // Avhengig av API er implementert i API kan bevegelsene kanskje bli mindre
    // hakkete.
    this.state = state;
  }

  /**
   * Returnerer status til objektet. OBS: dette er ikke nødvendigvis aktiv status,
   * dersom status er forandra siden forrige update().
   *
   * @return state Status som vil være aktiv etter neste update().
   */
  public Direction getState() {
    return this.state;
  }

  /**
   * Printer maks hastighet rapportert av EV3, multiplisert med faktorer oppgitt i
   * konfigurasjonsfil, til display. Returnerer ingenting. Denne kan brukes til
   * debugging, og er aktuell dersom det brukes kalkulerte hastigheter i stedet
   * for hastigheter satt i konfigurasjonsfil.
   */
  public void printCalculatedSpeeds() {
    this.recalculateSpeeds();

    System.out.println("Max: " + this.maxSpeed);
    System.out.println("Mid: " + this.midSpeed);
    System.out.println("Min: " + this.minSpeed);
  }
}
