package org.mumue.mumue.importer.components;

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
        setId(reference);
        return this;
    }
}
