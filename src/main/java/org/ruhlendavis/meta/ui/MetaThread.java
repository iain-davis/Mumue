package org.ruhlendavis.meta.ui;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class MetaThread extends Thread {
    private Socket socket = null;

    public MetaThread(Socket accept) {
        super("MetaThread");
        this.socket = accept;
    }

    public void run() {
        PrintWriter out;
        BufferedReader in;
        try {
            out = new PrintWriter(socket.getOutputStream(), true);
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            String inputLine;
            while ((inputLine = in.readLine()) != null) {
                if (inputLine.equals("QUIT")) {
                    break;
                }
            }
            socket.close();
        } catch (IOException e) {
        }
    }
}