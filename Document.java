package hk.edu.polyu.comp.comp2021.cvfs.model;

import java.util.HashSet;
import java.util.Objects;

public class Document {
    private String docName;
    private String docType;
    private String docContent;
    private int size;
    public HashSet<String> nameSet;

    public Document(String name,String type,String content){
        nameSet = new HashSet<>();
        setDocName(name);
        setType(type);
        setDocContent(content);
        size=40+content.length()*2;
    }
    public int getSize(){
        return size;
    }
    public String getDocContent(){
        return docContent;
    }
    public String listString(){
        String result="Name: "+docName+" type: "+docType+" size: "+size;
        return result;
    }
    public String getDocName(){
        return docName;
    }
    public String getDocType(){
        return docType;
    }


    public void setDocName(String name){
        if(docName.length()>10 ||!nameSet.contains(name)||docName.matches("^[a-zA-Z0-9]+$")|| docName.isEmpty()){
            System.out.println("the docName is wrong");
        }else{
            this.docName = name;
            nameSet.add(name);
        }
    }

    public void setType(String type){
        if (Objects.equals(type, "txt") ||Objects.equals(type, "java")||Objects.equals(type, "html")||Objects.equals(type, "css")){
            this.docType = type;
        }else {
            System.out.println("the type is wrong");
        }
    }
    public void setDocContent(String content){
        this.docContent=content;
    }
}
