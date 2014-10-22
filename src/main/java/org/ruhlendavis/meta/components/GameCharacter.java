package org.ruhlendavis.meta.components;

import java.util.ArrayList;
import java.util.List;

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
        setReference(reference);
        return this;
    }
}
