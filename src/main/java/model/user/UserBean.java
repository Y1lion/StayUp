package model.user;

/**
 * The type User bean.
 */
public class UserBean {
    private String email;
    private String psw;
    private String nome;
    private String cognome;
    private String role;
    private String telefono;
    private String gender;

    /**
     * Instantiates a new User bean.
     *
     * @param email the email
     * @param psw   the password
     */
    public UserBean(String email, String psw) {
        this.email = email;
        this.psw = psw;
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
     * Gets email.
     *
     * @return the email
     */
    public String getEmail() {
        return email;
    }

    /**
     * Sets password.
     *
     * @param psw the password
     */
    public void setPsw(String psw) {
        this.psw = psw;
    }

    /**
     * Gets password.
     *
     * @return the password
     */
    public String getPsw() {
        return psw;
    }

    /**
     * Gets nome.
     *
     * @return the nome
     */
    public String getNome() {
        return nome;
    }

    /**
     * Gets cognome.
     *
     * @return the cognome
     */
    public String getCognome() {
        return cognome;
    }

    /**
     * Get telefono string.
     *
     * @return the string
     */
    public String getTelefono(){ return telefono;}

    /**
     * Gets role.
     *
     * @return the role
     */
    public String getRole() {
        return role;
    }

    /**
     * Sets nome.
     *
     * @param n the nome
     */
    public void setNome(String n) {
        nome = n;
    }

    /**
     * Sets cognome.
     *
     * @param c the cognome
     */
    public void setCognome(String c) {
        cognome = c;
    }

    /**
     * Set telefono.
     *
     * @param tel the telefono
     */
    public void setTelefono(String tel){ telefono=tel;}

    /**
     * Sets role.
     *
     * @param role the role
     */
    public void setRole(String role) {
        this.role = role;
    }

    /**
     * Gets gender.
     *
     * @return the gender
     */
    public String getGender() {
        return gender;
    }

    /**
     * Sets gender.
     *
     * @param gender the gender
     */
    public void setGender(String gender) {
        this.gender = gender;
    }
    public String toString() {
        return "User{"
                + "Nome='" + nome + '\''
                + ", Cognome='" + cognome + '\''
                + ", Numero di Telefono='" + telefono + '\''
                + ", email='" + email + '\''
                + ", password='" + psw + '\''
                + ", gender='" + gender + '\''
                + ", ruolo=" + role
                + '}';
    }
}