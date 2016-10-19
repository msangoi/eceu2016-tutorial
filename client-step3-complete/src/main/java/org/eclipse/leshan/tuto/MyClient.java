package org.eclipse.leshan.tuto;

import java.util.List;

import org.eclipse.leshan.LwM2mId;
import org.eclipse.leshan.client.californium.LeshanClient;
import org.eclipse.leshan.client.californium.LeshanClientBuilder;
import org.eclipse.leshan.client.object.Security;
import org.eclipse.leshan.client.object.Server;
import org.eclipse.leshan.client.resource.LwM2mObjectEnabler;
import org.eclipse.leshan.client.resource.ObjectsInitializer;
import org.eclipse.leshan.core.request.BindingMode;

public class MyClient {

    public static void main(String[] args) {

        LeshanClientBuilder builder = new LeshanClientBuilder(MangohUtil.getImei());

        // Initialize objects list
        ObjectsInitializer initializer = new ObjectsInitializer();
        initializer.setInstancesForObject(LwM2mId.SECURITY, Security.noSec("coap://leshan.eclipse.org:5683", 123));
        initializer.setInstancesForObject(LwM2mId.SERVER, new Server(123, 30, BindingMode.U, false));
        initializer.setInstancesForObject(LwM2mId.DEVICE, new MyDevice());
        initializer.setInstancesForObject(Accelerometer.OBJECT_ID, new Accelerometer());

        List<LwM2mObjectEnabler> enablers = initializer.create(LwM2mId.SECURITY, LwM2mId.SERVER, LwM2mId.DEVICE,
                Accelerometer.OBJECT_ID);
        builder.setObjects(enablers);

        LeshanClient client = builder.build();

        client.start();
    }

}
