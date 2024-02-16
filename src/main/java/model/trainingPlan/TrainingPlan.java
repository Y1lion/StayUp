package model.trainingPlan;

import java.sql.Date;

/**
 * The type Training plan.
 */
public class TrainingPlan {
    private String emailUser;
    private String emailPt;
    private String exercises;
    private Date dateStart;
    private Date dateEnd;

    /**
     * Instantiates a new Training plan.
     *
     * @param emailUser the email user
     * @param emailPt   the email personal trainer
     * @param exercises the exercises
     * @param dateStart the date start
     * @param dateEnd   the date end
     */
    public TrainingPlan(String emailUser, String emailPt, String exercises, Date dateStart, Date dateEnd) {
        this.emailUser = emailUser;
        this.emailPt = emailPt;
        this.exercises = exercises;
        this.dateStart = dateStart;
        this.dateEnd = dateEnd;
    }

    /**
     * Instantiates a new Training plan.
     *
     * @param emailUser the email user
     */
    public TrainingPlan(String emailUser) {
        this.emailUser = emailUser;
    }

    /**
     * Gets email user.
     *
     * @return the email user
     */
    public String getEmailUser() {
        return emailUser;
    }

    /**
     * Sets email user.
     *
     * @param emailUser the email user
     */
    public void setEmailUser(String emailUser) {
        this.emailUser = emailUser;
    }

    /**
     * Gets email pt.
     *
     * @return the email personal trainer
     */
    public String getEmailPt() {
        return emailPt;
    }

    /**
     * Sets email pt.
     *
     * @param emailPt the email personal trainer
     */
    public void setEmailPt(String emailPt) {
        this.emailPt = emailPt;
    }

    /**
     * Gets exercises.
     *
     * @return the exercises
     */
    public String getExercises() {
        return exercises;
    }

    /**
     * Sets exercises.
     *
     * @param exercises the exercises
     */
    public void setExercises(String exercises) {
        this.exercises = exercises;
    }

    /**
     * Gets date start.
     *
     * @return the date start
     */
    public Date getDateStart() {
        return dateStart;
    }

    /**
     * Sets date start.
     *
     * @param dateStart the date start
     */
    public void setDateStart(Date dateStart) {
        this.dateStart = dateStart;
    }

    /**
     * Gets date end.
     *
     * @return the date end
     */
    public Date getDateEnd() {
        return dateEnd;
    }

    /**
     * Sets date end.
     *
     * @param dateEnd the date end
     */
    public void setDateEnd(Date dateEnd) {
        this.dateEnd = dateEnd;
    }
}
