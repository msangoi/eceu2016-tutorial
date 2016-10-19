package org.eclipse.leshan.tuto;

import org.eclipse.leshan.client.californium.LeshanClient;
import org.eclipse.leshan.client.californium.LeshanClientBuilder;

public class MyClient {

    public static void main(String[] args) {

        LeshanClientBuilder builder = new LeshanClientBuilder(MangohUtil.getImei());
        LeshanClient client = builder.build();

        client.start();

    }

}
