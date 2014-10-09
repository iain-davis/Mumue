package org.ruhlendavis.meta.components;

import java.util.ArrayList;
import java.util.List;

public class Character extends Component implements Homeable, LinkSource {
    private Long home = 0L;
    private Long wealth = 0L;
    private String password = "";
    private List<Link> links = new ArrayList<>();

    public Long getHome() {
        return home;
    }

    public void setHome(Long home) {
        this.home = home;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
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
}
