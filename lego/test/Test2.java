import lejos.hardware.motor.*;
import lejos.hardware.lcd.*;
import lejos.hardware.sensor.EV3GyroSensor;
import lejos.robotics.SampleProvider;
import lejos.hardware.port.Port;
import lejos.hardware.port.SensorPort;
import lejos.utility.Delay;
import java.lang.Math;

class Test2 {
    public static void main(String[] args) {
        Motor.A.setSpeed(450);  // sett hastighet (toppfart = 900)
        Motor.C.setSpeed(450);

        LCD.clear();

        Port trykkPortV = SensorPort.S1;
        Port trykkPortH = SensorPort.S4;
        EV3TouchSensor ev3TrykkSensorH = new EV3TouchSensor(trykkPortH);
        EV3TouchSensor ev3TrykkSensorV = new EV3TouchSensor(trykkPortV);

        SampleProvider tpH = ev3TrykkSensorH.getTouchMode();
        SampleProvider tpV = ev3TrykkSensorV.getTouchMode();

        int trykkValueH = 0;
        int trykkValueV = 0;

        while(trykkValueH == 0 && trykkValueV == 0){
			float[] sampleH = new float[tpH.sampleSize()];
			float[] sampleV = new float[tpV.sampleSize()];
			tpH.fetchSample(sample, 0);
			tpV.fetchSample(sample, 0);
			trykkValueH = (int)sample[0];
			trykkValueV = (int)sample[0];

			Delay.msDelay(500);

			System.out.println("Ikke rørt");
		} //while

		System.out.println("Nå er den rørt");

    } //main
} //class
