package org.ruhlendavis.meta.components;

import java.util.ArrayList;
import java.util.List;

public class Space extends Component implements LinkSource {
    private Component dropTo = new Component();
    private List<Link> links = new ArrayList<>();

    public Component getDropTo() {
        return dropTo;
    }

    public void setDropTo(Component dropTo) {
        this.dropTo = dropTo;
    }

    public List<Link> getLinks() {
        return links;
    }

    @Override
    public Space withId(Long id) {
        setId(id);
        return this;
    }
}
