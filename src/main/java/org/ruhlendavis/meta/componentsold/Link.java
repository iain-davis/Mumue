package org.ruhlendavis.meta.componentsold;

import java.util.ArrayList;
import java.util.List;

public class Link extends Component implements Ownable {
    private List<Component> destinations = new ArrayList<>();
    private Component owner;

    public List<Component> getDestinations() {
        return destinations;
    }

    public Component getOwner() {
        return owner;
    }

    public void setOwner(Component owner) {
        this.owner = owner;
    }

    @Override
    public Link withId(Long reference) {
        setId(reference);
        return this;
    }
}
