package model.personalTrainer;

import model.user.UserBean;

import java.util.List;

public class PersonalTrainerFacade {
    public synchronized PersonalTrainer personalTrainerRegistration(UserBean ub){
        return new PersonalTrainerDAO().personalTrainerRegistration(ub);
    }
    public synchronized PersonalTrainer changeDescription(PersonalTrainer pt, String newDescription){
        return new PersonalTrainerDAO().changeDescription(pt, newDescription);
    }
    public synchronized PersonalTrainer changePTYears(PersonalTrainer pt, Integer newYears){
        return new PersonalTrainerDAO().changePTYears(pt, newYears);
    }
    public synchronized PersonalTrainer retrieveInfo(String email){
        return new PersonalTrainerDAO().retrieveInfo(email);
    }
    public synchronized List<PersonalTrainer> retrieveAll(){
        return new PersonalTrainerDAO().retrieveAll();
    }
    public synchronized Boolean deletePT(String email){
        return new PersonalTrainerDAO().deletePT(email);
    }
}
