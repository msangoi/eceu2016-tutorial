package org.eclipse.leshan.tuto;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.concurrent.TimeUnit;

public class MangohUtil {

    public static String getModelNumber() {
        return command("cm", "info", "device");
    }

    public static String getSerialNumber() {
        return command("cm", "info", "fsn");
    }

    public static String getImei() {
        return command("cm", "info", "imei");
    }

    public static String getFirmwareVersion() {
        return command("cm", "info", "firmware");
    }


    private static String command(String... command) {
        try {
            Process process = new ProcessBuilder(command).start();
            if (process.waitFor(5, TimeUnit.SECONDS)) {
                if (process.getErrorStream().available() > 0) {
                    try (BufferedReader r = new BufferedReader(new InputStreamReader(process.getErrorStream()))) {
                        System.err.println(command + ": " + r.readLine());
                    }
                } else {
                    try (BufferedReader r = new BufferedReader(new InputStreamReader(process.getInputStream()))) {
                        return r.readLine();
                    }
                }
            }
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
        return "-";
    }

}
