package org.eclipse.leshan.tuto;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.Timer;
import java.util.TimerTask;

import org.eclipse.leshan.client.resource.BaseInstanceEnabler;
import org.eclipse.leshan.core.response.ReadResponse;

import com.pi4j.io.i2c.I2CBus;
import com.pi4j.io.i2c.I2CDevice;
import com.pi4j.io.i2c.I2CFactory;

/**
 * Object 3313 (IPSO object)
 * 
 * Access Accelerometer data via the I2C bus.
 */
public class Accelerometer extends BaseInstanceEnabler {

    public static final int OBJECT_ID = 3313;

    // Resource IDs
    private static final int X_VALUE_RSC = 5702;
    private static final int Y_VALUE_RSC = 5703;
    private static final int Z_VALUE_RSC = 5704;

    private I2CDevice i2cDevice;

    private double xValue = 0d;
    private double yValue = 0d;
    private double zValue = 0d;

    public Accelerometer() {
        i2cInit();

        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                updateAccelerometerData();

                // fire changes to trigger an observe notification
                fireResourcesChange(X_VALUE_RSC, Y_VALUE_RSC, Z_VALUE_RSC);
            }
        }, 500, 500);
    }

    @Override
    public ReadResponse read(int resourceId) {

        switch (resourceId) {
        case X_VALUE_RSC:
            return ReadResponse.success(resourceId, xValue);

        case Y_VALUE_RSC:
            return ReadResponse.success(resourceId, yValue);

        case Z_VALUE_RSC:
            return ReadResponse.success(resourceId, zValue);

        default:
            return super.read(resourceId);
        }
    }

    private void i2cInit() {
        System.setProperty("pi4j.armel", ""); // load soft float native library

        try {
            I2CBus i2cBus = I2CFactory.getInstance(I2CBus.BUS_0);

            // LSM6DS3 chip (address = 0x6a)
            i2cDevice = i2cBus.getDevice(0x6a);
            System.out.println("Connected to LSM6DS3: ok");

            // enable accelerometer (CTRL address = 0x10)
            i2cDevice.write(0x10, (byte) 0b01000100);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void updateAccelerometerData() {
        try {
            byte[] buffer = new byte[6];
            i2cDevice.read(0x28, buffer, 0, 6);

            ByteBuffer bb = ByteBuffer.wrap(buffer).order(ByteOrder.LITTLE_ENDIAN);
            xValue = convertAccerelationValue(bb.getShort());
            yValue = convertAccerelationValue(bb.getShort());
            zValue = convertAccerelationValue(bb.getShort());

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private double convertAccerelationValue(int rawValue) {
        return ((double) rawValue) * 0.061d * 8d / 1000d;
    }

}
