package org.ruhlendavis.meta.components;

import java.util.ArrayList;
import java.util.List;

public class Link extends Component {
    private List<Component> destinations = new ArrayList<>();
    private List<Long> destinationIds = new ArrayList<>();

    public List<Long> getDestinationIds() {
        return destinationIds;
    }

    public List<Component> getDestinations() {
        return destinations;
    }
}
