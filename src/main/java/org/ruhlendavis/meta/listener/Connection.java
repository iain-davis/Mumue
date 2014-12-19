package org.ruhlendavis.meta.listener;

import java.io.IOException;
import java.io.OutputStream;
import java.net.Socket;

import org.ruhlendavis.meta.configuration.startup.StartupConfiguration;
import org.ruhlendavis.meta.datastore.DataStore;
import org.ruhlendavis.meta.interpreter.CommandInterpreter;
import org.ruhlendavis.meta.interpreter.commands.CommandConnect;

public class Connection implements Runnable {
    private StartupConfiguration startupConfiguration = new StartupConfiguration();
    private Socket socket;
    private CommandInterpreter interpreter = new CommandInterpreter();

    @Override
    public void run() {
        DataStore dataStore = new DataStore();
        OutputStream output;
        try {
            output = socket.getOutputStream();
            String welcomeText = dataStore.getText(startupConfiguration, "welcome-screen");
            output.write(welcomeText.getBytes());
        } catch (IOException e) {
            e.printStackTrace();
            closeSocket();
        }
        interpreter.putCommand("@c", new CommandConnect());
        interpreter.putCommand("c", new CommandConnect());
        // display welcome
        // respond to a command valid for unauthenticated connections.
        // @connect/@authenticate/@login/login/connect/authenticate <playername> <password>
        // @register/@signup/@create/register/signup/create <playername> ...
    }

    private void closeSocket() {
        try {
            socket.close();
        } catch (IOException exception) {
            exception.printStackTrace();
        }
    }

    public Connection withSocket(Socket socket) {
        this.socket = socket;
        return this;
    }

    public CommandInterpreter getInterpreter() {
        return interpreter;
    }
}
