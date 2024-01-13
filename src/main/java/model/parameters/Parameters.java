package model.parameters;

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

    public Parameters(String email) {
        this.email = email;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Double getWeight() {
        return weight;
    }

    public void setWeight(Double weight) {
        this.weight = weight;
    }

    public Double getLean_mass() {
        return lean_mass;
    }

    public void setLean_mass(Double lean_mass) {
        this.lean_mass = lean_mass;
    }

    public Double getFat_mass() {
        return fat_mass;
    }

    public void setFat_mass(Double fat_mass) {
        this.fat_mass = fat_mass;
    }

    public Double getArm_mis() {
        return arm_mis;
    }

    public void setArm_mis(Double arm_mis) {
        this.arm_mis = arm_mis;
    }

    public Double getLeg_mis() {
        return leg_mis;
    }

    public void setLeg_mis(Double leg_mis) {
        this.leg_mis = leg_mis;
    }

    public Double getChest_mis() {
        return chest_mis;
    }

    public void setChest_mis(Double chest_mis) {
        this.chest_mis = chest_mis;
    }

    public Double getHips_mis() {
        return hips_mis;
    }

    public void setHips_mis(Double hips_mis) {
        this.hips_mis = hips_mis;
    }

    public Double getShoulders_mis() {
        return shoulders_mis;
    }

    public void setShoulders_mis(Double shoulders_mis) {
        this.shoulders_mis = shoulders_mis;
    }

    public Integer getWorkoutYears() {
        return workoutYears;
    }

    public void setWorkoutYears(Integer workoutYears) {
        this.workoutYears = workoutYears;
    }
}
