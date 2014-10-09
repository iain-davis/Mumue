package org.ruhlendavis.meta.components;

import java.util.ArrayList;
import java.util.List;

public class Space extends Component implements LinkSource {
    private Long dropTo = 0L;
    private List<Link> links = new ArrayList<>();

    public Long getDropTo() {
        return dropTo;
    }

    public void setDropTo(Long dropTo) {
        this.dropTo = dropTo;
    }

    public List<Link> getLinks() {
        return links;
    }
}
