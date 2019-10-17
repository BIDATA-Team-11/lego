package lego;


import lejos.hardware.sensor.EV3ColorSensor;
import lejos.robotics.SampleProvider;
import lejos.hardware.port.Port;

/**
 * Fargeklassen representerer en Farge sensor fra LejOS API'et.
 * @author Torbjørn Øverås
 * @version 1.0.0
 */
public class Farge {
    private EV3ColorSensor sensor;
    private SampleProvider fargeLeser; 
    private float[] fargeSample; 
    
    private int svart = 0;

    /**
     * Konstuerer en ny farge sensor fra LejOS API (EV3ColorSensor)
     * @param port den fysiske porten som blir brukt på EV3 maskinen.
     * @see Port
     */
    public Farge(Port port) {
        sensor = new EV3ColorSensor(port); 
        fargeLeser = sensor.getMode("RGB");  
        fargeSample = new float[fargeLeser.sampleSize()];  
    }

    /**
     * Henter den fargen sensoren ser på i dette øyeblikket.
     * @return en float som representerer den fargen sensoren ser på i dette
     * øyeblikket.
     */
    public float getFarge() {
        this.fargeLeser.fetchSample(this.fargeSample, 0);
        return fargeSample[0]*100;
    }

    /**
     * Kalibrerer hva fargesensoren ser på i det øyeblikket denne funksjonen
     * blir utløst.
     * @return en float som representerer den fargen som fargesensoren ser på.
     */
    public float kalibrering() {
        for (int i = 0; i < 100; ++i) {
            this.fargeLeser.fetchSample(this.fargeSample, 0);
            svart += this.fargeSample[0] * 100;
        }

        // return ((svart / 100) + 2);
        return ((svart / 100) + 2);
        // return (svart / 100);
    }
}
