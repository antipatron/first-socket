package com.inerxia.threads;

import com.inerxia.Server;

import java.io.*;
import java.net.Socket;

public class StreamSocket implements AutoCloseable {
    private Socket socket;
    private BufferedReader input;
    private PrintWriter output;

    public StreamSocket(Socket socket) throws IOException {
        this.socket = socket;
        input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        output = new PrintWriter(new OutputStreamWriter(socket.getOutputStream()));
    }

    public void send(String msg) {
        output.println(msg);
        output.flush();
    }

    public String receive() throws IOException {
        String msg = input.readLine();

        return msg != null ? msg : Server.END;
    }

    public String host() {
        return String.format("%s:%d", socket.getInetAddress(), socket.getPort());
    }

    @Override
    public void close() throws IOException {
        socket.close();
    }
}
