package org.ruhlendavis.meta.components;

import java.util.ArrayList;
import java.util.List;

public class Link extends Component {
    private List<Component> destinations = new ArrayList<>();

    public List<Component> getDestinations() {
        return destinations;
    }

    @Override
    public Link withId(Long id) {
        setId(id);
        return this;
    }
}
