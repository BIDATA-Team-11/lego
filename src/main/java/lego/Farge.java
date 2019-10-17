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
 * @author Torbjørn Øverås
 * @version 1.0.0
 */
public class Farge {
    private EV3ColorSensor sensor;
    private SampleProvider fargeLeser;
    private float[] fargeSample;

    private int svart = 0;

<<<<<<< HEAD
    public Farge(Port port) {                               // Lager klar fargesensor til � bruke RGB resultat
        sensor = new EV3ColorSensor(port);        
        fargeLeser = sensor.getMode("RGB");  
        fargeSample = new float[fargeLeser.sampleSize()];   //Sier til sensor at den skal hente inn en ny samplesize
    }

    public float getFarge() {                               //get metode for � f� inn farge under sensor
=======
    /**
     * Konstuerer en ny EV3ColorSensor fra LejOS API.
     * @param port Fysisk port på EV3-maskinen.
     * @see Port
     */
    public Farge(Port port) {
        sensor = new EV3ColorSensor(port);
        fargeLeser = sensor.getMode("RGB");
        fargeSample = new float[fargeLeser.sampleSize()];
    }

    /**
     * Henter ut fargeverdi som sensoren ser.
     * @return en float som representerer den fargen sensoren ser på i dette
     * øyeblikket.
     */
    public float getFarge() {
>>>>>>> jokkolainen
        this.fargeLeser.fetchSample(this.fargeSample, 0);
        return fargeSample[0]*100;
    }

<<<<<<< HEAD
    public float kalibrering() {                            //utf�rer kalibrering slik at det vi definerer som svart finnes p� ny hver gang
        for (int i = 0; i < 100; ++i) {                     //Slik at lysforhold ikke kan �delegge for koden. 
=======
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
     public boolean erSvart() {
       SampleProvider colorSample = this.sensor.getColorIDMode();
       float[] sample = new float[colorSample.sampleSize()];
       colorSample.fetchSample(sample, 0);
       return (int)sample[0] == Color.BLACK ? true : false;
     }

     /**
      * Metode for å se om sensoren ser svart.
      * @return True hvis sensoren ser svart, false ellers.
      */
     public boolean erUbestemt() {
       SampleProvider colorSample = this.sensor.getColorIDMode();
       float[] sample = new float[colorSample.sampleSize()];
       colorSample.fetchSample(sample, 0);
       int color = (int)sample[0];
       boolean notBlack = color == Color.BLACK ? false : true;
       boolean notWhite = color != Color.WHITE ? true : false;
       //return notWhite && notBlack ? true : false;
       return true;
     }

    /**
     * Kalibrerer hva fargesensoren ser på i det øyeblikket denne funksjonen
     * blir utløst.
     * @return en float som representerer den fargen som fargesensoren ser på.
     */
    public float kalibrering() {
        for (int i = 0; i < 100; ++i) {
>>>>>>> jokkolainen
            this.fargeLeser.fetchSample(this.fargeSample, 0);
            svart += this.fargeSample[0] * 100;
        }

        // return ((svart / 100) + 2);
        return ((svart / 100) + 2);
        // return (svart / 100);
    }
}
