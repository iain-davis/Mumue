package org.ruhlendavis.meta.ui;

import org.apache.pivot.beans.BXMLSerializer;
import org.apache.pivot.wtk.*;

public class MetaApplication implements Application {
    private Window window;
    @Override
    public void startup(Display display, org.apache.pivot.collections.Map<String, String> properties) throws Exception {
        BXMLSerializer bxmlSerializer = new BXMLSerializer();
        window = (Window)bxmlSerializer.readObject(getClass().getClassLoader().getResource("mainWindow.bxml"));
        window = new MenuBars();
        window.open(display);
    }

    @Override
    public boolean shutdown(boolean optional) throws Exception {
        return false;
    }

    @Override
    public void suspend() throws Exception {

    }

    @Override
    public void resume() throws Exception {

    }
}