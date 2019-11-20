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
 * Hjelpeklasse for fargesensorene i LejOS API.
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

import lejos.hardware.sensor.EV3ColorSensor;
import lejos.robotics.SampleProvider;
import lejos.hardware.port.Port;
import lejos.robotics.Color;

public class Farge {
  private EV3ColorSensor sensor;

  /**
   * Konstuerer en ny EV3ColorSensor fra LejOS API.
   *
   * @param port Fysisk port på EV3-maskinen.
   */
  public Farge(Port port) {
    sensor = new EV3ColorSensor(port);
  }

  /**
   * Testmetode. Printer fargeverdi. TODO: Kanskje fjerne denne. Da kan vi ta bort
   * mange imports. Må da også fjerne call fra App.java.
   */
  public void printFargeID() {
    SampleProvider colorSample = this.sensor.getColorIDMode();
    float[] sample = new float[colorSample.sampleSize()];
    colorSample.fetchSample(sample, 0);
    int colorId = (int) sample[0];
    String colorName = "";
    switch (colorId) {
    case Color.NONE:
      colorName = "NONE";
      break;
    case Color.BLACK:
      colorName = "BLACK";
      break;
    case Color.BLUE:
      colorName = "BLUE";
      break;
    case Color.GREEN:
      colorName = "GREEN";
      break;
    case Color.YELLOW:
      colorName = "YELLOW";
      break;
    case Color.RED:
      colorName = "RED";
      break;
    case Color.WHITE:
      colorName = "WHITE";
      break;
    case Color.BROWN:
      colorName = "BROWN";
      break;
    }
    System.out.println(colorId + " - " + colorName);
  }

  /**
   * Metode for å se om sensoren ser svart.
   *
   * @return True hvis sensoren ser svart, false ellers.
   */
  public boolean hasLine() {
    SampleProvider colorSample = this.sensor.getColorIDMode();
    float[] sample = new float[colorSample.sampleSize()];
    colorSample.fetchSample(sample, 0);
    return (int) sample[0] == Color.BLACK ? true : false;
  }

  /**
   * Invers av hasLine(). Kun for å gjøre logikk for styring mer lesbar.
   *
   * @return True hvis sensoren ikke ser svart, false ellers.
   */
  public boolean lostLine() {
    return !this.hasLine();
  }

  /**
   * Metode for å se om sensoren ser svart. Denne er ubrukt i Awesomebot!
   *
   * @return True hvis sensoren ser noe annet enn svart eller hvit, false ellers.
   */
  public boolean erUbestemt() {
    SampleProvider colorSample = this.sensor.getColorIDMode();
    float[] sample = new float[colorSample.sampleSize()];
    colorSample.fetchSample(sample, 0);
    int color = (int) sample[0];
    boolean notBlack = color == Color.BLACK ? false : true;
    boolean notWhite = color != Color.WHITE ? true : false;
    return notWhite && notBlack ? true : false;
  }
}
