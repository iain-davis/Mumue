package org.ruhlendavis.meta.components;

import java.util.ArrayList;
import java.util.List;

public class LinkSourceComponent extends Component {
    private List<Link> links = new ArrayList<>();
    public List<Link> getLinks() {
        return links;
    }
}
