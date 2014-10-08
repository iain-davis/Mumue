package org.ruhlendavis.meta.components;

public class Character extends Component {
    private Long home = 0L;
    private Long wealth = 0L;
    private String password = "";

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
}
