package org.ruhlendavis.meta.listener;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;

public class Connection implements Runnable {
    private Socket socket;
    private boolean authenticated = false;

    @Override
    public void run() {
//        try {
//            PrintWriter output = new PrintWriter(new OutputStreamWriter(socket.getOutputStream()));
//            BufferedReader input = new BufferedReader(new InputStreamReader(socket.getInputStream()));
//
//            output.print("Welcome to MetaMuck\r\n");
//            output.flush();
//            while (true) {
//                String line = input.readLine();
//                if (input == null) {
//                    stop(output, input);
//                    return;
//                }
//                if ("QUIT".equals(line)) {
//                    stop(output, input);
//                    return;
//                }
//                if (authenticated) {
//                    if (line.startsWith("\"") || line.startsWith("'")) {
//                        sayIt(line.substring(1));
//                    } else if (line.startsWith("say ")) {
//                        sayIt(line.substring(4));
//                    } else if (line.startsWith(":") || line.startsWith(";")) {
//                        poseIt(line.substring(1));
//                    } else if (line.startsWith("pose ")) {
//                        poseIt(line.substring(5));
//                    }
//                } else {
//                    if (line.startsWith("con")) {
//                        String parts[] = line.split(" ");
//                        if (parts.length != 3) {
//                            output.println("con playername password");
//                            output.flush();
//                        } else {
//                            String name = parts[1];
//                            authenticated = true;
//                        }
//                    }
//                }
//            }
//        } catch (IOException exception) {
//            exception.printStackTrace();
//        }
    }

    private void poseIt(String substring) {

    }

    private void sayIt(String substring) {

    }

    private void stop(PrintWriter output, BufferedReader input) throws IOException {
//        input.close();
//        output.close();
//        socket.close();
    }

    public Connection withSocket(Socket socket) {
        this.socket = socket;
        return this;
    }
}
