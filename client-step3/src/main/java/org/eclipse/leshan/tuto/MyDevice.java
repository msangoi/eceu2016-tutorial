package org.eclipse.leshan.tuto;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

import org.eclipse.leshan.client.resource.BaseInstanceEnabler;
import org.eclipse.leshan.core.node.LwM2mResource;
import org.eclipse.leshan.core.request.BindingMode;
import org.eclipse.leshan.core.response.ExecuteResponse;
import org.eclipse.leshan.core.response.ReadResponse;
import org.eclipse.leshan.core.response.WriteResponse;

/**
 * Object 3
 */
public class MyDevice extends BaseInstanceEnabler {

    // Resource IDs
    private static final int MANUFACTURER = 0;
    private static final int MODEL_NUMBER = 1;
    private static final int SERIAL_NUMBER = 2;
    private static final int FIRMWARE_VERSION = 3;
    private static final int REBOOT = 4;
    private static final int FACTORY_RESET = 5;
    private static final int CURRENT_TIME = 13;
    private static final int UTC_OFFSET = 14;
    private static final int TIMEZONE = 15;
    private static final int SUPPORTED_BINDING = 16;

    private final String manufacturer;
    private final String modelNumber;
    private final String supportedBinding;
    private final String serialNumber;
    private String timezone;
    private String utcOffset;

    public MyDevice() {
        manufacturer = "Sierra Wireless";
        modelNumber = MangohUtil.getModelNumber();
        serialNumber = MangohUtil.getSerialNumber();
        supportedBinding = BindingMode.U.toString();
        timezone = TimeZone.getDefault().getID();
        utcOffset = new SimpleDateFormat("X").format(Calendar.getInstance().getTime());
    }

    @Override
    public ReadResponse read(int resourceId) {
        System.out.println("Device: read on resource " + resourceId);

        switch (resourceId) {
        case MANUFACTURER:
            return ReadResponse.success(resourceId, manufacturer);

        case MODEL_NUMBER:
            return ReadResponse.success(resourceId, modelNumber);

        case SERIAL_NUMBER:
            return ReadResponse.success(resourceId, serialNumber);

        case FIRMWARE_VERSION:
            return ReadResponse.success(resourceId, MangohUtil.getFirmwareVersion());

        case CURRENT_TIME:
            return ReadResponse.success(resourceId, new Date());

        case UTC_OFFSET:
            return ReadResponse.success(resourceId, utcOffset);

        case TIMEZONE:
            return ReadResponse.success(resourceId, timezone);

        case SUPPORTED_BINDING:
            return ReadResponse.success(resourceId, supportedBinding);

        default:
            return super.read(resourceId);
        }
    }

    @Override
    public WriteResponse write(int resourceId, LwM2mResource value) {
        System.out.println("Device: write on resource " + resourceId);

        switch (resourceId) {

        case UTC_OFFSET:
            utcOffset = (String) value.getValue();
            fireResourcesChange(resourceId);
            return WriteResponse.success();

        case TIMEZONE:
            timezone = (String) value.getValue();
            fireResourcesChange(resourceId);
            return WriteResponse.success();

        default:
            return super.write(resourceId, value);
        }
    }

    @Override
    public ExecuteResponse execute(int resourceId, String params) {
        System.out.println("Device: exec on resource " + resourceId);

        switch (resourceId) {

        case REBOOT:
            System.out.println("\n\n REBOOT ! \n\n");
            return ExecuteResponse.success();

        case FACTORY_RESET:
            System.out.println("\n\n FACTORY RESET ! \n\n");
            return ExecuteResponse.success();

        default:
            return super.execute(resourceId, params);
        }
    }

}
