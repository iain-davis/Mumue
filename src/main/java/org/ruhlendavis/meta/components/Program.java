package org.ruhlendavis.meta.components;

public class Program extends Component {
    @Override
    public Program withId(Long id) {
        setId(id);
        return this;
    }
}
