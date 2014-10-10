package org.ruhlendavis.meta.components;

import java.util.ArrayList;
import java.util.List;

public class Link extends Component {
    private List<Component> destinations = new ArrayList<>();

    public List<Component> getDestinations() {
        return destinations;
    }
}
