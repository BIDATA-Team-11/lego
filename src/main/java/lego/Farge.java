package lego;

import lejos.hardware.sensor.EV3ColorSensor;
import lejos.robotics.SampleProvider;
import lejos.hardware.port.Port;

public class Farge {
    private EV3ColorSensor sensor;
    private SampleProvider fargeLeser;
    private float[] fargeSample;

    private int svart = 0;

    public Farge(Port port) {
        sensor = new EV3ColorSensor(port);
        fargeLeser = sensor.getMode("RGB");
        fargeSample = new float[fargeLeser.sampleSize()];
    }

    public float getFarge() {
        this.fargeLeser.fetchSample(this.fargeSample, 0);
        return fargeSample[0]*100;
    }

    public float kalibrering() {
        for (int i = 0; i < 100; ++i) {
            this.fargeLeser.fetchSample(this.fargeSample, 0);
            svart += this.fargeSample[0] * 100;
        }

        return ((svart / 100) + 5);
    }
}
