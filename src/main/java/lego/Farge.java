package lego;


import lejos.hardware.sensor.EV3ColorSensor;
import lejos.robotics.SampleProvider;
import lejos.hardware.port.Port;

public class Farge {
    private EV3ColorSensor sensor;
    private SampleProvider fargeLeser; 
    private float[] fargeSample; 
    
    private int svart = 0;

    public Farge(Port port) {                               // Lager klar fargesensor til å bruke RGB resultat
        sensor = new EV3ColorSensor(port);        
        fargeLeser = sensor.getMode("RGB");  
        fargeSample = new float[fargeLeser.sampleSize()];   //Sier til sensor at den skal hente inn en ny samplesize
    }

    public float getFarge() {                               //get metode for å få inn farge under sensor
        this.fargeLeser.fetchSample(this.fargeSample, 0);
        return fargeSample[0]*100;
    }

    public float kalibrering() {                            //utfører kalibrering slik at det vi definerer som svart finnes på ny hver gang
        for (int i = 0; i < 100; ++i) {                     //Slik at lysforhold ikke kan ødelegge for koden. 
            this.fargeLeser.fetchSample(this.fargeSample, 0);
            svart += this.fargeSample[0] * 100;
        }

        return ((svart / 100) + 5);
        // return (svart / 100);
    }
}
