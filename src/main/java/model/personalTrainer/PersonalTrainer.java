package model.personalTrainer;

import model.user.UserBean;

public class PersonalTrainer {
    private UserBean user;
    private String description;
    private Integer ptYears;

    public PersonalTrainer(UserBean user, String description, Integer ptYears) {
        this.user = user;
        this.description = description;
        this.ptYears = ptYears;
    }

    public PersonalTrainer(UserBean user) {
        this.user = user;
    }

    public UserBean getUser() {
        return user;
    }

    public void setUser(UserBean user) {
        this.user = user;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getPtYears() {
        return ptYears;
    }

    public void setPtYears(Integer ptYears) {
        this.ptYears = ptYears;
    }
}
