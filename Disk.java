package hk.edu.polyu.comp.comp2021.cvfs.model;

public class Disk {
    private int size;
    private Directory rootDir;
    public Disk(int diskSize){
        size=diskSize;
        rootDir=new Directory("Disk");
    }
    public Directory getDir(){
        return rootDir;
    }
}
