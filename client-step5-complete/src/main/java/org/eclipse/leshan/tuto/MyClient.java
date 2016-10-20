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
import org.eclipse.leshan.util.Hex;

public class MyClient {

    public static void main(String[] args) {

        LeshanClientBuilder builder = new LeshanClientBuilder(MangohUtil.getImei());

        byte[] pskIdentity = ("CHANGEME").getBytes(); // must be unique
        byte[] privateKey = Hex.decodeHex("0123456709CAFE".toCharArray());

        // Initialize objects list
        ObjectsInitializer initializer = new ObjectsInitializer();
        initializer.setInstancesForObject(LwM2mId.SECURITY,
                Security.psk("coaps://leshan.eclipse.org:5684", 123, pskIdentity, privateKey));
        initializer.setInstancesForObject(LwM2mId.SERVER, new Server(123, 30, BindingMode.U, false));
        initializer.setInstancesForObject(LwM2mId.DEVICE, new MyDevice());

        List<LwM2mObjectEnabler> enablers = initializer.create(LwM2mId.SECURITY, LwM2mId.SERVER, LwM2mId.DEVICE);
        builder.setObjects(enablers);

        LeshanClient client = builder.build();

        client.start();
    }

}
