package model;

public class UserBean {
    private String email;
    private String psw;
    private String nome;
    private String cognome;
    private String role;
    private String telefono;
    private String sex;
    private Integer workoutYears;
    private Integer ptYears;

    public UserBean(String email2, String psw2) {
        email = email2;
        psw = psw2;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getEmail() {
        return email;
    }

    public void setPsw(String psw) {
        this.psw = psw;
    }

    public String getPsw() {
        return psw;
    }

    public String getNome() {
        return nome;
    }

    public String getCognome() {
        return cognome;
    }

    public String getTelefono(){ return telefono;}
    public String getRole() {
        return role;
    }

    public void setNome(String n) {
        nome = n;
    }

    public void setCognome(String c) {
        cognome = c;
    }

    public void setTelefono(String tel){ telefono=tel;}

    public void setRole(String role) {
        this.role = role;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public Integer getWorkoutYears() {
        return workoutYears;
    }

    public void setWorkoutYears(Integer workoutYears) {
        this.workoutYears = workoutYears;
    }

    public Integer getPtYears() {
        return ptYears;
    }

    public void setPtYears(Integer ptYears) {
        this.ptYears = ptYears;
    }
}