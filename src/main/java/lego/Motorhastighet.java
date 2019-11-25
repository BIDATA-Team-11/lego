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
 * Hjelpeklasse for å sette motorhastigheter og akselerasjon.
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

/**
 * Denne klassen inneholder flere alternative måter å sette hastigheter på
 * motorene. Dette fordi vi ønsket å prøve å forandre en del ting underveis.
 */
public class Motorhastighet {

  /**
   * Størst hastighet
   */
  public final static int max = 1000; // Full fart framover

  /**
   * Middels hastighet (hastighet på "ytterhjul" i svinger)
   */
  public final static int mid = 600;

  public final static int midright = 500;

  /**
   * Lav hastighet (hastighet på "innerhjul" i svinger)
   */
  public final static int min = 350;

  /*
   * Konstantene nedenfor brukes dersom flagget accelrationTest settes i Bil.java.
   *
   * maxAcc brukes når roboten går fra svinghastighet og opp. minAcc brukes når
   * roboten går fra topphastighet ned til svinghastighet.
   */

  /**
   * Største akelerasjonshastighet
   */
  public final static int maxAcc = 8000; // Akselrasjon lav->høy

  /**
   * Laveste akelerasjon shastighet
   */
  public final static int minAcc = 4000; // Akselrasjon høy->lav

  /*
   * Disse brukes kun dersom vi bruker kalkulerte hastigheter. max, mid, min vil
   * da bli ignorert.
   *
   * Hver variabel representerer en andel av EV3s rapporterte makshastighet basert
   * på batteri, etc.
   */

  /**
   * Størst hastighet (faktor)
   */
  public final static float maxSpeedFactor = 1.4f;

  /**
   * Middels hastighet (faktor)
   */
  public final static float midSpeedFactor = 0.95f;

  /**
   * Lav hastighet (faktor)
   */
  public final static float minSpeedFactor = 0.30f;
}
