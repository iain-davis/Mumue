package org.ruhlendavis.meta.componentsold;

public class Program extends Component implements Ownable {
    private Component owner;

    public Component getOwner() {
        return owner;
    }

    public void setOwner(Component owner) {
        this.owner = owner;
    }

    @Override
    public Program withId(Long reference) {
        setReference(reference);
        return this;
    }
}
