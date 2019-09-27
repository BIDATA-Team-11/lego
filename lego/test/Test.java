package lego.test;

import lejos.robotics.MirrorMotor;
import lejos.robotics.RegulatedMotor;
import lejos.hardware.lcd.*;
import lejos.hardware.motor.*;
import lejos.hardware.sensor.EV3GyroSensor;
import lejos.hardware.sensor.EV3TouchSensor;
import lejos.robotics.SampleProvider;
import lejos.hardware.port.Port;
import lejos.hardware.port.SensorPort;
import lejos.utility.Delay;
import java.lang.Math;

class Test {
  public static void main(String[] args) {
        RegulatedMotor MotorA = MirrorMotor.invertMotor(Motor.A);
        RegulatedMotor MotorC = MirrorMotor.invertMotor(Motor.C);
        MotorA.setSpeed(450);  // sett hastighet (toppfart = 900)
        MotorC.setSpeed(450);

        MotorA.forward();
        MotorC.forward();
        Thread.sleep(2000);
        MotorA.stop();
        MotorC.stop();

        LCD.clear();

        // Port vinkelPort = SensorPort.S3;
        // EV3GyroSensor ev3VinekelSensor = new EV3GyroSensor(vinkelPort);
        // ev3VinekelSensor.reset();
        // SampleProvider vinkelSensor = ev3VinekelSensor.getAngleMode();
        
        // int value = 0;

        // System.out.println("Snu 180 grader");
        // Motor.C.stop();
        // while (true) {
        //     float[] sample = new float[vinkelSensor.sampleSize()];
        //     vinkelSensor.fetchSample(sample, 0);
        //     value = (int) sample[0];

        //     if (Math.abs(value) >= 180) {
        //         break;
        //     }

        //     Motor.A.backward();
        //     Delay.msDelay(500);
        // }


        // Port trykkPortV = SensorPort.S1;
        // Port trykkPortH = SensorPort.S4;
        // EV3TouchSensor ev3TrykkSensorH = new EV3TouchSensor(trykkPortH);
        // EV3TouchSensor ev3TrykkSensorV = new EV3TouchSensor(trykkPortV);

        // SampleProvider tpH = ev3TrykkSensorH.getTouchMode();
        // SampleProvider tpV = ev3TrykkSensorV.getTouchMode();

        // int trykkValueH = 0;
        // int trykkValueV = 0;

        // while(trykkValueH == 0 && trykkValueV == 0){
			// float[] sampleH = new float[tpH.sampleSize()];
			// float[] sampleV = new float[tpV.sampleSize()];
			// tpH.fetchSample(sampleH, 0);
			// tpV.fetchSample(sampleV, 0);
			// trykkValueH = (int)sampleH[0];
			// trykkValueV = (int)sampleV[0];

			// Delay.msDelay(500);

			// System.out.println("Ikke rørt");
		// } //while

		// System.out.println("Nå er den rørt");
    }
}
