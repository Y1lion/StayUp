package model.subscription;

import java.sql.Date;

/**
 * The type Subscription.
 */
public class Subscription {
    private String emailPt;
    private String emailUser;
    private Date dateStart;
    private Date dateEnd;
    private Integer isActive;

    /**
     * Instantiates a new Subscription.
     *
     * @param emailPt   the email personal trainer
     * @param emailUser the email user
     * @param dateStart the date start
     * @param dateEnd   the date end
     * @param isActive  the is active
     */
    public Subscription(String emailPt, String emailUser, Date dateStart, Date dateEnd, Integer isActive) {
        this.emailPt = emailPt;
        this.emailUser = emailUser;
        this.dateStart = dateStart;
        this.dateEnd = dateEnd;
        this.isActive = isActive;
    }

    /**
     * Instantiates a new Subscription.
     *
     * @param emailUser the email user
     */
    public Subscription(String emailUser) {
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

    /**
     * Gets active.
     *
     * @return the active
     */
    public Integer getActive() {
        return isActive;
    }

    /**
     * Sets active.
     *
     * @param active the active
     */
    public void setActive(Integer active) {
        isActive = active;
    }
}
