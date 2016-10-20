package org.eclipse.leshan.tuto;

import java.util.Arrays;

import org.eclipse.leshan.core.node.LwM2mObject;
import org.eclipse.leshan.core.node.LwM2mPath;
import org.eclipse.leshan.core.observation.Observation;
import org.eclipse.leshan.core.request.ObserveRequest;
import org.eclipse.leshan.core.response.LwM2mResponse;
import org.eclipse.leshan.core.response.ObserveResponse;
import org.eclipse.leshan.server.californium.LeshanServerBuilder;
import org.eclipse.leshan.server.californium.impl.LeshanServer;
import org.eclipse.leshan.server.client.Client;
import org.eclipse.leshan.server.client.ClientRegistryListener;
import org.eclipse.leshan.server.client.ClientUpdate;
import org.eclipse.leshan.server.observation.ObservationRegistryListener;

public class MyServer {

    // object ID
    private static final int ACCELEROMETER_ID = 3313;

    // Resource IDs
    private static final int X_VALUE_RSC = 5702;
    private static final int Y_VALUE_RSC = 5703;
    private static final int Z_VALUE_RSC = 5704;

    public static void main(String[] args) {

        LeshanServerBuilder builder = new LeshanServerBuilder();

        LeshanServer server = builder.build();

        // observe accelerometer object for each new client
        server.getClientRegistry().addListener(new ClientRegistryListener() {

            @Override
            public void registered(Client client) {

                System.out.println("New client registration: " + client);

                // if the client supports the Accelerometer object
                if (Arrays.asList(client.getObjectLinks()).stream().anyMatch(l -> "/3313/0".equals(l.getUrl()))) {

                    try {
                        LwM2mResponse response = server.send(client, new ObserveRequest(ACCELEROMETER_ID), 5_000L);
                        System.out.println("Observe response: " + response);

                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                }
            }

            @Override
            public void updated(ClientUpdate update, Client clientUpdated) {
                //
            }

            @Override
            public void unregistered(Client client) {
                //
            }

        });

        // listen for notifications
        server.getObservationRegistry().addListener(new ObservationRegistryListener() {

            LwM2mPath accelerometerPath = new LwM2mPath(ACCELEROMETER_ID);

            @Override
            public void newValue(Observation observation, ObserveResponse response) {
                if (accelerometerPath.equals(observation.getPath())) {
                    LwM2mObject accObject = (LwM2mObject) response.getContent();
                    double x = (double) accObject.getInstance(0).getResource(X_VALUE_RSC).getValue();
                    double y = (double) accObject.getInstance(0).getResource(Y_VALUE_RSC).getValue();
                    double z = (double) accObject.getInstance(0).getResource(Z_VALUE_RSC).getValue();
                    System.out.println(String.format("New acceleration data from client %s: [%f, %f, %f]",
                            observation.getRegistrationId(), x, y, z));
                }
            }

            @Override
            public void newObservation(Observation observation) {
                System.out.println("Observing resource " + observation.getPath() + " from client "
                        + observation.getRegistrationId());
            }

            @Override
            public void cancelled(Observation observation) {
                //
            }

        });

        server.start();

    }

}
