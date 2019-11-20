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
 * LejOS Klient for Legobilprosjekt 2019
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

import lejos.hardware.Brick;
import lejos.hardware.BrickFinder;
import lejos.hardware.port.Port;
import lejos.hardware.Button;

/**
 * Hovedklasse. Starter programmet og tar seg av enkel styringslogikk.
 */
public class App {
  /**
   * Main-metode for klienten. Her får man valget om å printe ut de fargene
   * sensorene ser, kalibrere fargesensorene eller å få bilen til å kjøre.
   *
   * @param args Argumenter blir ignorert. Heh.
   * @throws Exception Gir EV3 mulighet til å catche eventuelle feil.
   */
  public static void main(String[] args) throws Exception {
    Brick brick = BrickFinder.getDefault();

    /*
     * Oppsett av fargesensorer.
     */
    Port mainSensorPort = brick.getPort("S2");
    Port correctionSensorPort = brick.getPort("S4");
    Farge mainSensor = new Farge(mainSensorPort);
    Farge correctionSensor = new Farge(correctionSensorPort);

    System.out.println("1.1.0-awesomebot");
    System.out.println("Ned:    Les farge");
    System.out.println("Enter:  Start");
    System.out.println("Opp:  Hardkoda");

    Bil bil;

    do {
      int knapp = Button.waitForAnyPress();

      if (knapp == Button.ID_RIGHT) {
      } else if (knapp == Button.ID_LEFT) {
        bil = new Bil(true); // true: kalkulerte hastigheter, false: hardkoda hastigheter.
        start(mainSensor, correctionSensor, bil);
      } else if (knapp == Button.ID_DOWN) {
        mainSensor.printFargeID();
        correctionSensor.printFargeID();
      } else if (knapp == Button.ID_UP) {
        bil = new Bil(false); // true: kalkulerte hastigheter, false: hardkoda hastigheter.
        start(mainSensor, correctionSensor, bil);
      } else if (knapp == Button.ID_ENTER) {
        bil = new Bil(false); // true: kalkulerte hastigheter, false: hardkoda hastigheter.
        start(mainSensor, correctionSensor, bil);
      }
    } while (true);
    /*
     * TODO: Bruker while-løkke her så det kan gjøres mulig å legge inn en escape i
     * hovedløkka, sånn at det kan bli mulig å stoppe og starte roboten uten å drepe
     * programmet.
     */
  }

  /**
   * Starter selve legobilen.
   *
   * @param mainSensor       Hovedfargesensor. Står midt på fronten på roboten..
   * @param correctionSensor Korrigeringssensor. Står til høyre for
   *                         hovedfargesensor.
   * @param bil              Hjelpeklasse for motorene.
   * @see Farge
   * @see Bil
   */
  public static void start(Farge mainSensor, Farge correctionSensor, Bil bil) {

    /*
     * Flagg som indikerer at linja befinner seg mellom sensorene. Dette løser en
     * del problemer med hunting ved at vi unngår å svinge tilbake mot venstre så
     * snart korrigeringssensoren mister linja. Flagget settes med en gang
     * korrigeringssensoren ser linja, og fjernes så snart hovedsensoren finner
     * linja igjen.
     */
    boolean lineIsBetweenSensors = false;

    /*
     * "Hovedløkka" i programmet. Denne kjører til vi dreper den.
     *
     * Her ligger logikken som styrer retning - fram, sving til venstre, sving til
     * høyre.
     */
    while (true) {
      if (mainSensor.lostLine()) {
        if (bil.getState() == Direction.FORWARD || correctionSensor.hasLine()) {
          if (correctionSensor.hasLine()) {
            bil.setState(Direction.RIGHT);
          } else if (!lineIsBetweenSensors) {
            bil.setState(Direction.LEFT);
          }
        }
      }

      if (mainSensor.hasLine()) {
        bil.setState(Direction.FORWARD);
        lineIsBetweenSensors = false;
      }

      if (mainSensor.lostLine()) {
        if (correctionSensor.hasLine()) {
          bil.setState(Direction.RIGHT);
          lineIsBetweenSensors = true;
        }
      }

      bil.update();
    }
  }
}
