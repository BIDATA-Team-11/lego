package lego.test;

import lejos.hardware.motor.*;
import lejos.hardware.lcd.*;
import lejos.hardware.sensor.EV3GyroSensor;
import lejos.robotics.SampleProvider;
import lejos.hardware.port.Port;
import lejos.hardware.port.SensorPort;
import lejos.utility.Delay;
import java.lang.Math;

class Test {
  public static void main(String[] args) {
        Motor.A.setSpeed(450);  // sett hastighet (toppfart = 900)
        Motor.C.setSpeed(450);

        LCD.clear();

        Port vinkelPort = SensorPort.S3;
        EV3GyroSensor ev3VinekelSensor = new EV3GyroSensor(vinkelPort);
        ev3VinekelSensor.reset();
        SampleProvider vinkelSensor = ev3VinekelSensor.getAngleMode();
        
        int value = 0;

        System.out.println("Snu 180 grader");
        Motor.C.stop();
        while (true) {
            float[] sample = new float[vinkelSensor.sampleSize()];
            vinkelSensor.fetchSample(sample, 0);
            value = (int) sample[0];

            if (Math.abs(value) >= 180) {
                break;
            }

            Motor.A.backward();
            Delay.msDelay(500);
        }

        // Kjr framover
        LCD.clear();
        System.out.println("Fram 1000");
        Motor.A.forward();
        Motor.C.forward();

        Thread.sleep(1000);
        Motor.A.stop();
        Motor.C.stop();
    }
}
