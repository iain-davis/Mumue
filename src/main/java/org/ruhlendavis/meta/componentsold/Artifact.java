package org.ruhlendavis.meta.componentsold;

import java.util.ArrayList;
import java.util.List;

public class Artifact extends Component implements Homeable, LinkSource, Ownable {
    private Component home = new Component();
    private Long value = 0L;
    private List<Link> links = new ArrayList<>();
    private Component owner;

    public Component getHome() {
        return home;
    }

    public void setHome(Component home) {
        this.home = home;
    }

    public Long getValue() {
        return value;
    }

    public void setValue(Long value) {
        this.value = value;
    }

    public List<Link> getLinks() {
        return links;
    }

    public Component getOwner() {
        return owner;
    }

    public void setOwner(Component owner) {
        this.owner = owner;
    }

    @Override
    public Artifact withId(Long reference) {
        setId(reference);
        return this;
    }
}
