package hk.edu.polyu.comp.comp2021.cvfs.model;

import static hk.edu.polyu.comp.comp2021.cvfs.model.Criteria.name2cri;

public class compositeCriteria extends Criteria{
    private Criteria criteria1;
    private Criteria criteria2;
    private String logicOp;

    public compositeCriteria(String criName, Criteria criteria1,String  logicOp, Criteria criteria2){
        super(criName,null,logicOp,null);
        this.criteria1 = criteria1;
        this.criteria2 = criteria2;
        this.logicOp = logicOp;
        super.addnamecri(criName,this);
    }
    public Criteria getCriteria1(){return criteria1;}
    public Criteria getCriteria2(){return criteria2;}
    public String getLogicOp(){return logicOp;}
}
