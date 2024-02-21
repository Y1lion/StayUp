package model.parameters;

import java.util.ArrayList;

public class ParametersFacade {
    public synchronized Parameters getParameters(String email){
        return new ParametersDAO().getParameters(email);
    }
    public synchronized Parameters setParameters(String email, ArrayList<Double> params){
        return new ParametersDAO().setParameters(email,params);
    }
    public synchronized Parameters changeParameters(Parameters params){
        return new ParametersDAO().changeParameters(params);
    }
}
