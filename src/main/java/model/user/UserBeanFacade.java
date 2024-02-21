package model.user;

import java.util.ArrayList;
import java.util.List;

public class UserBeanFacade {
    public synchronized UserBean loginUser(String email, String password){
        return new UserBeanDAO().loginUser(email,password);
    }
    public synchronized UserBean changeEmail(UserBean ub, String oldemail, String newemail) {
        return new UserBeanDAO().changeEmail(ub, oldemail, newemail);
    }
    public synchronized UserBean changeName(UserBean ub, String newName, String newSurname) {
        return new UserBeanDAO().changeName(ub, newName, newSurname);
    }
    public synchronized UserBean changeNumber(UserBean ub, String newNumber) {
        return new UserBeanDAO().changeNumber(ub, newNumber);
    }
    public synchronized UserBean changeGender(UserBean ub, String newGender) {
        return new UserBeanDAO().changeGender(ub, newGender);
    }
    public synchronized UserBean recoverInfos(String email) {
        return new UserBeanDAO().recoverInfos(email);
    }
    public synchronized UserBean changePsw(UserBean ub, String oldpsw, String newpsw) {
        return new UserBeanDAO().changePsw(ub, oldpsw, newpsw);
    }
    public synchronized UserBean forgotPsw(UserBean ub, String newpsw) {
        return new UserBeanDAO().forgotPsw(ub, newpsw);
    }
    public synchronized UserBean userRegistration(String email, String password, String name, String surname, String telephone, String gender){
        return new UserBeanDAO().userRegistration(email,password,name,surname,telephone,gender);
    }
    public synchronized UserBean checkEmail(String email){
        return new UserBeanDAO().checkEmail(email);
    }
    public synchronized void deleteUser(String uEmail) {
        new UserBeanDAO().deleteUser(uEmail);
    }
    public synchronized ArrayList<UserBean> allUsers(){
        return new UserBeanDAO().allUsers();
    }
    public synchronized UserBean requestRolePT(UserBean ub){
        return new UserBeanDAO().requestRolePT(ub);
    }
    public synchronized List<UserBean> retrieveAllPending(){
        return new UserBeanDAO().retrieveAllPending();
    }
    public synchronized void upgradeToPT(UserBean ub){
        new UserBeanDAO().upgradeToPT(ub);
    }
}
