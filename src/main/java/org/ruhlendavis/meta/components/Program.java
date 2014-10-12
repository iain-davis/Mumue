package org.ruhlendavis.meta.components;

public class Program extends Component implements Ownable {
    private Component owner;
    public Component getOwner() {
        return owner;
    }

    public void setOwner(Component owner) {
        this.owner = owner;
    }

    @Override
    public Program withId(Long id) {
        setId(id);
        return this;
    }
}
