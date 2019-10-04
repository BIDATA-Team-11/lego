import lejos.hardware.motor.*; //bytt ut wildcards
import lejos.hardware.lcd.*; // :((
import lejos.hardware.sensor.EV3GyroSensor;
import lejos.hardware.sensor.EV3TouchSensor;
import lejos.robotics.SampleProvider;
import lejos.hardware.port.Port;
import lejos.hardware.port.SensorPort;
import lejos.utility.Delay;
import java.lang.Math;

class Test2 { // test for å snu hvis bilen kræsjer
    public static void main(String[] args) {
		Motor.A.setSpeed(251);
		Motor.C.setSpeed(251);

        LCD.clear();

        Port trykkPortV = SensorPort.S1;
        Port trykkPortH = SensorPort.S4;
        EV3TouchSensor ev3TrykkSensorH = new EV3TouchSensor(trykkPortH);
        EV3TouchSensor ev3TrykkSensorV = new EV3TouchSensor(trykkPortV);

        SampleProvider tpH = ev3TrykkSensorH.getTouchMode();
        SampleProvider tpV = ev3TrykkSensorV.getTouchMode();

        int trykkValueH = 0;
        int trykkValueV = 0;

        Motor.A.backward();
        Motor.C.backward();

        while(true){
			float[] sampleH = new float[tpH.sampleSize()];
			float[] sampleV = new float[tpV.sampleSize()];
			tpH.fetchSample(sampleH, 0);
			tpV.fetchSample(sampleV, 0);
			trykkValueH = (int)sampleH[0];
			trykkValueV = (int)sampleV[0];

			if(trykkValueH != 0){
				Motor.C.stop();
				Motor.A.forward();

				Delay.msDelay(1500);

				Motor.C.backward();
				Motor.A.backward();
			}

			if(trykkValueV != 0){
				Motor.C.forward();
				Motor.A.stop();

				Delay.msDelay(1500);

				Motor.C.backward();
				Motor.A.backward();
			}
		}

    } //main
} //class
