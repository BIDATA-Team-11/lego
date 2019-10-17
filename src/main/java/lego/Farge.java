package lego;

import lejos.hardware.sensor.EV3ColorSensor;
import lejos.robotics.SampleProvider;
import lejos.hardware.port.Port;
import lejos.robotics.Color;
import lejos.hardware.Button;
import lejos.hardware.BrickFinder;
import lejos.hardware.Keys;
import lejos.hardware.ev3.EV3;

/**
 * Hjelpeklasse for fargesensorene i LejOS API.
 * @author Stian Selvåg
 * @author Herman Aagaard
 * @author Henrik Hafsø
 * @author Joakim Skogø Langvand
 * @author Erling Sletta
 * @author Torbjørn Øverås
 * @author Gruppe 11, dataingeniør NTNU, første semester.
 * @version 1.0.0
 */
public class Farge {
    private EV3ColorSensor sensor;

    /**
     * Konstuerer en ny EV3ColorSensor fra LejOS API.
     * @param port Fysisk port på EV3-maskinen.
     * @see Port
     */
    public Farge(Port port) {
        sensor = new EV3ColorSensor(port);
    }

    /**
     * Testmetode. Printer fargeverdi.
     */
     public void printFargeID() {
         EV3 ev3 = (EV3) BrickFinder.getLocal();
         Keys keys = ev3.getKeys();
         SampleProvider colorSample = this.sensor.getColorIDMode();
         float[] sample = new float[colorSample.sampleSize()];
         colorSample.fetchSample(sample, 0);
         int colorId = (int)sample[0];
         String colorName = "";
         switch(colorId){
             case Color.NONE: colorName = "NONE"; break;
             case Color.BLACK: colorName = "BLACK"; break;
             case Color.BLUE: colorName = "BLUE"; break;
             case Color.GREEN: colorName = "GREEN"; break;
             case Color.YELLOW: colorName = "YELLOW"; break;
             case Color.RED: colorName = "RED"; break;
             case Color.WHITE: colorName = "WHITE"; break;
             case Color.BROWN: colorName = "BROWN"; break;
         }
         System.out.println(colorId + " - " + colorName);
     }

     /**
      * Metode for å se om sensoren ser svart.
      * @return True hvis sensoren ser svart, false ellers.
      */
     public boolean hasLine() {
       SampleProvider colorSample = this.sensor.getColorIDMode();
       float[] sample = new float[colorSample.sampleSize()];
       colorSample.fetchSample(sample, 0);
       return (int)sample[0] == Color.BLACK ? true : false;
     }

     /**
      * Invers av hasLine()
      * @return True hvis sensoren ikke ser svart, false ellers.
      */
     public boolean lostLine() {
       return !this.hasLine();
     }

     /**
      * Metode for å se om sensoren ser svart. Denne er ubrukt i Awesomebot!
      * @return True hvis sensoren ser svart, false ellers.
      */
     public boolean erUbestemt() {
       SampleProvider colorSample = this.sensor.getColorIDMode();
       float[] sample = new float[colorSample.sampleSize()];
       colorSample.fetchSample(sample, 0);
       int color = (int)sample[0];
       boolean notBlack = color == Color.BLACK ? false : true;
       boolean notWhite = color != Color.WHITE ? true : false;
       return notWhite && notBlack ? true : false;
     }
}
