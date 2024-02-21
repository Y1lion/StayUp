package model.trainingPlan;

import model.personalTrainer.PersonalTrainer;
import model.user.UserBean;

import java.sql.Date;
import java.util.ArrayList;

public class TrainingPlanFacade {
    public synchronized Boolean addTrainingPlan(String emailPT, String emailUser, String exercises, Date dateStart, Date dateEnd) {
        return new TrainingPlanDAO().addTrainingPlan(emailPT, emailUser, exercises, dateStart, dateEnd);
    }

    public synchronized Boolean deleteTrainingPlan(String emailUser, String emailPT, String exercisesString) {
        return new TrainingPlanDAO().deleteTrainingPlan(emailUser, emailPT, exercisesString);
    }

    public synchronized ArrayList<TrainingPlan> getAvailableTrainingPlan(UserBean ub) {
        return new TrainingPlanDAO().getAvailableTrainingPlan(ub);
    }

    public synchronized ArrayList<TrainingPlan> getAvailablePtTrainingPlan(UserBean ub, PersonalTrainer pt) {
        return new TrainingPlanDAO().getAvailablePtTrainingPlan(ub, pt);
    }

    public synchronized ArrayList<TrainingPlan> getAllTrainingPlans(PersonalTrainer pt) {
        return new TrainingPlanDAO().getAllTrainingPlans(pt);
    }

    public synchronized ArrayList<TrainingPlan> getAllUserTrainingPlans(UserBean ub) {
        return new TrainingPlanDAO().getAllUserTrainingPlans(ub);
    }
}