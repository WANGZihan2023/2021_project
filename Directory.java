package hk.edu.polyu.comp.comp2021.cvfs.model;

import java.util.HashMap;
import java.util.Map;

public class Directory {
    private String DirStr;
    private Map<String,Object> map;
    private int size;
    public Directory(String dirname){
        DirStr=dirname;
        map= new HashMap<>();
        size=40;
    }
    public void put(String name,Document doc){
        map.put(name,doc);
        size+=doc.getSize();
    }
    public void put(String name,Directory dir){
        map.put(name,dir);
        size+=dir.getSize();
    }
    public void delete(String name){
        int objSize;
        if (map.get(name).getClass()==Document.class){
            Document obj=(Document) map.get(name);
            objSize=obj.getSize();
        }else{
            Directory obj=(Directory) map.get(name);
            objSize=obj.getSize();
        }
        size-=objSize;
        map.remove(name);
    }
    public Object get(String name){
        return map.get(name);
    }
    public int getSize(){
        return size;
    }
    public String toString(){
        return DirStr;
    }
}
