package org.ruhlendavis.meta.listener;

import java.net.Socket;

import org.ruhlendavis.meta.interpreter.CommandInterpreter;
import org.ruhlendavis.meta.interpreter.commands.CommandConnect;

public class Connection implements Runnable {
    private CommandInterpreter interpreter = new CommandInterpreter();

    @Override
    public void run() {
        interpreter.putCommand("@c", new CommandConnect());
        interpreter.putCommand("c", new CommandConnect());
        // display welcome
        // respond to a command valid for unauthenticated connections.
        // @connect/@authenticate/@login/login/connect/authenticate <playername> <password>
        // @register/@signup/@create/register/signup/create <playername> ...
    }

    public Connection withSocket(Socket socket) {
        return this;
    }

    public CommandInterpreter getInterpreter() {
        return interpreter;
    }
}
