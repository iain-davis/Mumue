package org.ruhlendavis.meta.componentsold;

import java.util.ArrayList;
import java.util.List;

import org.ruhlendavis.meta.components.Component;

public class GameCharacter extends Component implements Homeable, LinkSource {
    private Component home = new Component();
    private Long wealth = 0L;
    private List<Link> links = new ArrayList<>();

    public Component getHome() {
        return home;
    }

    public void setHome(Component home) {
        this.home = home;
    }

    public Long getWealth() {
        return wealth;
    }

    public void setWealth(Long wealth) {
        this.wealth = wealth;
    }

    public List<Link> getLinks() {
        return links;
    }

    @Override
    public GameCharacter withId(Long reference) {
        setId(reference);
        return this;
    }
}
