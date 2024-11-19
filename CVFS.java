package hk.edu.polyu.comp.comp2021.cvfs.model;

public class CVFS {
    private Disk disk;
    private Directory workingDir;
    private Criteria criteria;
    public CVFS(){}
    public void setDisk(Disk disk){
        this.disk=disk;
    }
    public Disk getDisk(){
        return disk;
    }
    public void setDir(Directory dir){
        workingDir=dir;
    }
    public Directory getDir(){
        return workingDir;
    }
    public void setCriteria(Criteria criteria){this.criteria=criteria;}
    public Criteria getCriteria(){
        return criteria;
    }
}
