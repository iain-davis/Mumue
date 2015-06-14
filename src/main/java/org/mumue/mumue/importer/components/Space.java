package org.mumue.mumue.importer.components;

import java.util.ArrayList;
import java.util.List;

public class Space extends Component implements LinkSource, Ownable {
    private Component dropTo;
    private Component owner;
    private List<Link> links = new ArrayList<>();

    public Component getOwner() {
        return owner;
    }

    public void setOwner(Component owner) {
        this.owner = owner;
    }

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
    public Space withId(Long reference) {
        setId(reference);
        return this;
    }
}
