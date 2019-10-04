package lego.test;

import lejos.robotics.MirrorMotor;
import lejos.robotics.RegulatedMotor;
import lejos.hardware.lcd.*;
import lejos.hardware.motor.*;
import lejos.hardware.sensor.EV3ColorSensor;
import lejos.robotics.SampleProvider;
import lejos.hardware.Brick;
import lejos.hardware.BrickFinder;
import lejos.hardware.port.Port;
import lejos.hardware.port.SensorPort;
import lejos.utility.Delay;
import java.lang.Math;

public class Test {
    public static void main (String[] args)  throws Exception {
        RegulatedMotor MotorA = MirrorMotor.invertMotor(Motor.A);
        RegulatedMotor MotorC = MirrorMotor.invertMotor(Motor.C);

        Brick brick = BrickFinder.getDefault();
        Port fargePort = brick.getPort("S2"); // fargesensor
        Port fargePortKorrigering = brick.getPort("S4"); // fargesensor

        EV3ColorSensor fargeSensor = new EV3ColorSensor(fargePort); 
        SampleProvider fargeLeser = fargeSensor.getMode("RGB"); 
        float[] fargeSample = new float[fargeLeser.sampleSize()];  

        EV3ColorSensor fargeKorrigering = new EV3ColorSensor(fargePortKorrigering); 
        SampleProvider fargeLeserKorrigering = fargeKorrigering.getMode("RGB");
        float[] fargeSampleKorrigering = new float[fargeLeserKorrigering.sampleSize()];  

        float svart = kalibrering(fargeLeser, fargeSample);
        float svartKorr = kalibrering(fargeLeserKorrigering, fargeSampleKorrigering);
        float grå = (svart + svartKorr) / 2;
        Thread.sleep(3000);

        float farge = 0;
        float fargeKorr = 0;

        int topSpeed = 700;
        int midSpeed = 400;
        int minSpeed = 100;
        int accTopSpeed = 6000;
        int accMinSpeed = 1000;

        int retning = 0;

        MotorA.setSpeed(topSpeed);  // sett hastighet (toppfart = 900)
        MotorC.setSpeed(topSpeed);

        MotorA.setAcceleration(accTopSpeed);
        MotorC.setAcceleration(accTopSpeed);

        while (true) {
            fargeLeser.fetchSample(fargeSample, 0);
            fargeLeserKorrigering.fetchSample(fargeSampleKorrigering, 0);

            farge = fargeSample[0]*100;
            fargeKorr = fargeSampleKorrigering[0]*100;

            System.out.println("svart - hvit");
            if (farge > grå) {
                if (fargeKorr > grå) {
                    System.out.println("Hvit - Hvit");
                    retning = -1;
                } else if (fargeKorr < grå) {
                    System.out.println("Hvit - svart");
                    retning = 1;
                }

                MotorA.setAcceleration(accMinSpeed);
                MotorC.setAcceleration(accMinSpeed);
            } else {
                MotorA.setSpeed(topSpeed);
                MotorC.setSpeed(topSpeed);

                MotorA.setAcceleration(accTopSpeed);
                MotorC.setAcceleration(accTopSpeed);
            }

            if (retning > 0) {
                MotorC.setSpeed(midSpeed);
                MotorA.setSpeed(minSpeed);
            }

            if (retning < 0) {
                MotorC.setSpeed(minSpeed);
                MotorA.setSpeed(midSpeed);

            }

            MotorA.forward();
            MotorC.forward();
        }
    }

    private static float kalibrering(SampleProvider fargeLeser, float[] fargeSample) {
        int svart = 0;

        for (int i = 0; i < 100; ++i) {
            fargeLeser.fetchSample(fargeSample, 0);
            svart += fargeSample[0] * 100;
        }

        svart = (svart / 100);
        return svart;
    }
}
