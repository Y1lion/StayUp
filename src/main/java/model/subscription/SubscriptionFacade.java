package model.subscription;

import model.personalTrainer.PersonalTrainer;
import model.user.UserBean;

import java.sql.Date;
import java.util.ArrayList;

public class SubscriptionFacade {
    public synchronized Subscription addSubscription(UserBean ub, PersonalTrainer pt, Date dateEnd){
        return new SubscriptionDAO().addSubscription(ub,pt,dateEnd);
    }
    public synchronized Subscription acceptSubscription(Subscription s){
        return new SubscriptionDAO().acceptSubscription(s);
    }
    public synchronized Subscription refuseSubscription(Subscription s){
      return new SubscriptionDAO().refuseSubscription(s);
    }
    public synchronized Subscription getSubscription(UserBean ub){
        return new SubscriptionDAO().getSubscription(ub);
    }
    public synchronized ArrayList<Subscription> getAllSubscriptions(PersonalTrainer pt){
        return new SubscriptionDAO().getAllSubscriptions(pt);
    }
}
