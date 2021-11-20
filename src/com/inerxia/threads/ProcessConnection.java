package com.inerxia.threads;

import com.inerxia.Server;

import java.io.IOException;

public class ProcessConnection implements Runnable {
    private final StreamSocket socket;

    public ProcessConnection(StreamSocket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {
        String msg = "OK";
        try (this.socket) {
            while (!msg.equals(Server.END)) {
                msg = socket.receive();
                System.out.println(msg);
                socket.send("RECEIVED");
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            System.out.printf("Connection with %s closed\n", socket.host());
        }
    }
}
