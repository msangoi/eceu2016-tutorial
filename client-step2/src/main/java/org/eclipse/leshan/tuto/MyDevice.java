package org.eclipse.leshan.tuto;

import org.eclipse.leshan.client.resource.BaseInstanceEnabler;
import org.eclipse.leshan.core.response.ReadResponse;

/**
 * Object 3
 */
public class MyDevice extends BaseInstanceEnabler {

    // Resource IDs
    private static final int MANUFACTURER = 0;
    private static final int MODEL_NUMBER = 1;
    private static final int SERIAL_NUMBER = 2;
    private static final int FIRMWARE_VERSION = 3;
    private static final int REBOOT = 4; // executable resource
    private static final int FACTORY_RESET = 5; // executable resource
    private static final int CURRENT_TIME = 13;
    private static final int UTC_OFFSET = 14; // writable resource
    private static final int TIMEZONE = 15; // writable resource
    private static final int SUPPORTED_BINDING = 16;


    @Override
    public ReadResponse read(int resourceId) {
        System.out.println("Device: read on resource " + resourceId);

        // TODO

        // Build a ReadResponse to return a value for some of the above resources.
        // tip: The ReadResponse class provides some static constructors.

        // Use the MangohUtil class to read the model number, the serial number and the firmware version.

        // super.read(resourceId) should be called for not-implemented resources.

        return super.read(resourceId);

    }

    // Bonus: override the write() or execute() method to provide an implementation for writable/executable resources

}
