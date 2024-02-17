package model.personalTrainer;

import model.user.UserBean;

/**
 * The type Personal trainer.
 */
public class PersonalTrainer {
    private UserBean user;
    private String description;
    private Integer ptYears;

    /**
     * Instantiates a new Personal trainer.
     *
     * @param user        the user
     * @param description the description
     * @param ptYears     the pt years
     */
    public PersonalTrainer(UserBean user, String description, Integer ptYears) {
        this.user = user;
        this.description = description;
        this.ptYears = ptYears;
    }

    /**
     * Instantiates a new Personal trainer.
     *
     * @param user the user
     */
    public PersonalTrainer(UserBean user) {
        this.user = user;
    }

    /**
     * Gets user.
     *
     * @return the user
     */
    public UserBean getUser() {
        return user;
    }

    /**
     * Sets user.
     *
     * @param user the user
     */
    public void setUser(UserBean user) {
        this.user = user;
    }

    /**
     * Gets description.
     *
     * @return the description
     */
    public String getDescription() {
        return description;
    }

    /**
     * Sets description.
     *
     * @param description the description
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * Gets pt years.
     *
     * @return the personal trainer working years
     */
    public Integer getPtYears() {
        return ptYears;
    }

    /**
     * Sets pt years.
     *
     * @param ptYears the personal trainer working years
     */
    public void setPtYears(Integer ptYears) {
        this.ptYears = ptYears;
    }

    @Override
    public String toString() {
        return "PersonalTrainer{" +
                "user=" + user +
                ", description='" + description + '\'' +
                ", ptYears=" + ptYears +
                '}';
    }
}
