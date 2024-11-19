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

    public String getDocName(){
        return docName;
    }
    public String getDocType(){
        return docType;
    }
    public String getDocContent(){
        return docContent;
    }
    public void setDocName(String name){
        this.docName=name;
    }
    public void setDocType(String type){
        this.docType=type;
    }
    public void setDocContent(String content){
        this.docContent=content;
    }

}
