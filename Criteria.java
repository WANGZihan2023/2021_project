package hk.edu.polyu.comp.comp2021.cvfs.model;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class Criteria {
    private String criName;
    private String attrName;
    private String op;
    private String val;
    public static Map<String,Criteria> name2cri = new HashMap<>();
    private Criteria criteria;

    public Criteria(String criName, String attrName, String op, String val) {
        this.criName = criName;
        this.attrName = attrName;
        this.op = op;
        this.val = val;
        name2cri.put(criName,this);
    }


    public String getCriName() {
        return criName;
    }
    public void setCriName(String criName) {
        this.criName = criName;
    }
    public String getAttrName() {
        return attrName;
    }
    public void setAttrName(String attrName) {
        this.attrName = attrName;
    }
    public String getOp() {
        return op;
    }
    public void setOp(String op) {
        if (Objects.equals(op, "<")|| (Objects.equals(op, ">"))||(Objects.equals(op, ">="))||(Objects.equals(op, "<="))||(Objects.equals(op, "=="))||(Objects.equals(op, "!="))) {
            this.op = op;
        }
    }
    public String getVal() {
        return val;
    }
    public void setVal(String val) {
        this.val = val;
    }
    public void addnamecri(String criName,Criteria criteria){
        name2cri.put(criName,this);
    }

    public static String notOp(String op){
        String notOp = "";
        if (Objects.equals(op, "<")){
            notOp =  ">";
        }
        if (Objects.equals(op, ">")){
            notOp =  "<";
        }
        if (Objects.equals(op, ">=")){
            notOp =  "<=";
        }
        if (Objects.equals(op, "<=")){
            notOp =  ">=";
        }
        if (Objects.equals(op, "==")){
            notOp =  " !=";
        }
        if (Objects.equals(op, " !=")){
            notOp =  "==";
        }
        return notOp;
    }

    


    public static Criteria getCriteria(String attrName){
        return name2cri.get(attrName);
    }

    public static Collection<Criteria> getAllCriteria(){return name2cri.values();}


    public boolean IsDocument(Object file) {
        boolean isDocument;
        if (file instanceof Document){ isDocument = true;}
        else{isDocument = false;}
        return isDocument;
    }

    public boolean matches(Document doc) {
        switch (attrName) {
            case "name":
                return op.equals("contains") && doc.getDocName().contains(val.replace("\"", ""));
            case "type":
                return op.equals("equals") && doc.getDocType().equals(val.replace("\"", ""));
            case "size":
                int docSize = doc.getSize();
                int criteriaValue = Integer.parseInt(val);
                switch (op) {
                    case ">":
                        return docSize > criteriaValue;
                    case "<":
                        return docSize < criteriaValue;
                    case ">=":
                        return docSize >= criteriaValue;
                    case "<=":
                        return docSize <= criteriaValue;
                    case "==":
                        return docSize == criteriaValue;
                    case "!=":
                        return docSize != criteriaValue;
                    default:
                        return false;
                }
            default:
                return false;
        }
    }
    

}
