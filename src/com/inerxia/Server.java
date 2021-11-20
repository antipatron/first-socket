package com.inerxia;

import com.inerxia.threads.ProcessConnection;
import com.inerxia.threads.StreamSocket;

import java.io.IOException;
import java.net.BindException;
import java.net.ServerSocket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Server {

    public static String END = "END";

    private static void listen(int port) {
        try (ServerSocket connection = new ServerSocket(port)) {
            System.out.printf("Server running on port %d\n", port);
            ExecutorService pool = Executors.newCachedThreadPool();
            while (true) {
                System.out.println("Waiting for connections...");
                StreamSocket socket = new StreamSocket(connection.accept());
                System.out.printf("New connection from %s\n", socket.host());
                pool.execute(new ProcessConnection(socket));
            }
        } catch (BindException e) {
            error("Port %d is not available\n", port);
        } catch (IOException e) {
            e.printStackTrace();
            error("Server crashed\n");
        }
    }

    private static void error(String msg, Object... args) {
        System.err.printf(msg, args);
        System.exit(-1);
    }

    public static void main(String[] args) {
	// write your code here
        int port = 0;
        if (args.length < 1) {
            port =  9000;
            //error("No port number recieved\n");
        }
        try {
            listen(port);
        } catch(NumberFormatException e) {
            error("Failed parsing port number '%s'\n", args[0]);
        }
    }
}
