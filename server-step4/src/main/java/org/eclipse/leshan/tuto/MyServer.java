package org.eclipse.leshan.tuto;

public class MyServer {

    // object ID
    private static final int ACCELEROMETER_ID = 3313;

    public static void main(String[] args) {

        // TODO

        // Use the LeshanServerBuilder to create your server.

        // Add a listener to the ObservationRegistry to be notified with new notification values.

        // Add a listener the the ClientRegistry to start an observation on the Accelerometer object (if supported).

        // tip: The org.eclipse.leshan.server.client.Client.getObjectLinks() method returns the list of supported
        // objects. The Accelerometer object is supported if this list contains a link with url "/3313/0".

    }

}
