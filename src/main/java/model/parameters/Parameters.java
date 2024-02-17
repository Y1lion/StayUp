package model.parameters;

/**
 * The type Parameters.
 */
public class Parameters  {
    private String email;
    private Double weight;
    private Double lean_mass;
    private Double fat_mass;
    private Double arm_mis;
    private Double leg_mis;
    private Double chest_mis;
    private Double hips_mis;
    private Double shoulders_mis;
    private Integer workoutYears;

    /**
     * Instantiates a new Parameters.
     *
     * @param email the email
     */
    public Parameters(String email) {
        this.email = email;
    }

    /**
     * Gets email.
     *
     * @return the email
     */
    public String getEmail() {
        return email;
    }

    /**
     * Sets email.
     *
     * @param email the email
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * Gets weight.
     *
     * @return the weight
     */
    public Double getWeight() {
        return weight;
    }

    /**
     * Sets weight.
     *
     * @param weight the weight
     */
    public void setWeight(Double weight) {
        this.weight = weight;
    }

    /**
     * Gets lean mass.
     *
     * @return the lean mass
     */
    public Double getLean_mass() {
        return lean_mass;
    }

    /**
     * Sets lean mass.
     *
     * @param lean_mass the lean mass
     */
    public void setLean_mass(Double lean_mass) {
        this.lean_mass = lean_mass;
    }

    /**
     * Gets fat mass.
     *
     * @return the fat mass
     */
    public Double getFat_mass() {
        return fat_mass;
    }

    /**
     * Sets fat mass.
     *
     * @param fat_mass the fat mass
     */
    public void setFat_mass(Double fat_mass) {
        this.fat_mass = fat_mass;
    }

    /**
     * Gets arm misuration.
     *
     * @return the arm misuration
     */
    public Double getArm_mis() {
        return arm_mis;
    }

    /**
     * Sets arm misuration.
     *
     * @param arm_mis the arm misuration
     */
    public void setArm_mis(Double arm_mis) {
        this.arm_mis = arm_mis;
    }

    /**
     * Gets leg misuration.
     *
     * @return the leg misuration
     */
    public Double getLeg_mis() {
        return leg_mis;
    }

    /**
     * Sets leg misuration.
     *
     * @param leg_mis the leg misuration
     */
    public void setLeg_mis(Double leg_mis) {
        this.leg_mis = leg_mis;
    }

    /**
     * Gets chest misuration.
     *
     * @return the chest misuration
     */
    public Double getChest_mis() {
        return chest_mis;
    }

    /**
     * Sets chest misuration.
     *
     * @param chest_mis the chest misuration
     */
    public void setChest_mis(Double chest_mis) {
        this.chest_mis = chest_mis;
    }

    /**
     * Gets hips misuration.
     *
     * @return the hips misuration
     */
    public Double getHips_mis() {
        return hips_mis;
    }

    /**
     * Sets hips misuration.
     *
     * @param hips_mis the hips misuration
     */
    public void setHips_mis(Double hips_mis) {
        this.hips_mis = hips_mis;
    }

    /**
     * Gets shoulders misuration.
     *
     * @return the shoulders misuration
     */
    public Double getShoulders_mis() {
        return shoulders_mis;
    }

    /**
     * Sets shoulders misuration.
     *
     * @param shoulders_mis the shoulders misuration
     */
    public void setShoulders_mis(Double shoulders_mis) {
        this.shoulders_mis = shoulders_mis;
    }

    /**
     * Gets workout years.
     *
     * @return the workout years
     */
    public Integer getWorkoutYears() {
        return workoutYears;
    }

    /**
     * Sets workout years.
     *
     * @param workoutYears the workout years
     */
    public void setWorkoutYears(Integer workoutYears) {
        this.workoutYears = workoutYears;
    }

    @Override
    public String toString() {
            return "Parameters{"
                + "email=" + email
                + "weight=" + weight
                + ", leanMass=" + lean_mass
                + ", fatMass=" + fat_mass
                + ", armMis=" + arm_mis
                + ", legMis=" + leg_mis
                + ", chestMis=" + chest_mis
                + ", hipsMis=" + hips_mis
                + ", shouldersMis=" + shoulders_mis
                + ", workoutYears=" + workoutYears
                + '}';
    }
}
