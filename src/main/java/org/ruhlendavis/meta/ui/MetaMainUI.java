package org.ruhlendavis.meta.ui;

import org.apache.pivot.wtk.DesktopApplicationContext;

public class MetaMainUI {
    public static void main(String[] arguments) {
        DesktopApplicationContext.main(MetaApplication.class, arguments);
    }
//    private static int portNumber = 2050;
//    private static boolean listening = true;
//    public static void main(String[] arguments) {
//        try {
//            ServerSocket serverSocket = new ServerSocket(portNumber);
//            while (listening) {
//                new MetaThread(serverSocket.accept()).start();
//            }
//        } catch (IOException e) {
//            System.err.println("Could not listen on port " + portNumber);
//            System.exit(-1);
//        }
//        while (listening) {
//
//        }
//    }
}
