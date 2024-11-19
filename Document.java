package hk.edu.polyu.comp.comp2021.cvfs.model;

public class Document {
    private String docName;
    private String docType;
    private String docContent;
    private int size;
    public Document(String name,String type,String content){
        docName=name;
        docType=type;
        docContent=content;
        size=40+content.length()*2;
    }
    public int getSize(){
        return size;
    }
}
