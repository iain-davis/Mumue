package org.ruhlendavis.meta.components;

public class Artifact extends Component {
    private Long home = 0L;
    private Long value = 0L;

    public Long getHome() {
        return home;
    }

    public void setHome(Long home) {
        this.home = home;
    }

    public Long getValue() {
        return value;
    }

    public void setValue(Long value) {
        this.value = value;
    }
}
