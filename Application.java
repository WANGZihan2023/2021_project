package hk.edu.polyu.comp.comp2021.cvfs;

import hk.edu.polyu.comp.comp2021.cvfs.model.*;

import java.util.Scanner;
import java.util.Stack;

public class Application {

    public static void main(String[] args){
        CVFS cvfs = new CVFS();
        // Initialize and utilize the system
        Stack<Directory> stack=new Stack<>();//存储所有路径
        Scanner scanner = new Scanner(System.in);
        String command;
        String[] commandWords;

        System.out.println("Any command or 'quit' to quit.");

        while (true) {
            System.out.print("Command: ");
            command = scanner.nextLine();
            commandWords=command.split("\\s+");

            if (command.equalsIgnoreCase("quit")) {
                System.out.println("Terminate the execution of the system.");
                break;
            }

            if (commandWords[0].equals("newDisk")){
                int size=Integer.parseInt(commandWords[1]);
                Disk disk=new Disk(size);
                cvfs.setDisk(disk);
                cvfs.setDir(cvfs.getDisk().getDir());
                stack.push(cvfs.getDir());
                System.out.println("New disk created successfully.");
            }

            if (commandWords[0].equals("newDoc")){
                Document doc=new Document(commandWords[1],commandWords[2],commandWords[3]);
                cvfs.getDir().put(commandWords[1],doc);
                System.out.println("New document created successfully.");
            }

            if (commandWords[0].equals("newDir")){
                Directory dir=new Directory(commandWords[1]);
                cvfs.getDir().put(commandWords[1],dir);
                System.out.println("New directory created successfully.");
            }

            if (commandWords[0].equals("delete")){
                cvfs.getDir().delete(commandWords[1]);
                System.out.println("Delete successfully.");
            }

            if (commandWords[0].equals("changeDir")){
                if(cvfs.getDir().get(commandWords[1])!=null){
                    cvfs.setDir((Directory) cvfs.getDir().get(commandWords[1]));
                    stack.push(cvfs.getDir());
                }else if(commandWords[1].equals("..") && !cvfs.getDir().toString().equals("Disk")){//dirName是..并且workingDir不是根目录
                    stack.pop();
                    cvfs.setDir(stack.peek());
                }
            }
            if (commandWords[0].equals("newNegation")){
                String criName1 = commandWords[1];
                String criName2 = commandWords[2];
                Criteria criteria2 = Criteria.getCriteria(criName2);

                if (criteria2 != null) {
                    Criteria negatedCriteria = new Criteria(criName1, criteria2.getAttrName(), Criteria.notOp(criteria2.getOp()), criteria2.getVal());
                    System.out.println("Negated criterion created successfully.");
                } else {
                    System.out.println("Criterion " + criName2 + " does not exist.");
                }
            }
            if (commandWords[0].equals("newBinaryCri")){
                String criName1 = commandWords[1];
                String criName3 = commandWords[2];
                String logicOp = commandWords[3];
                String criName4 = commandWords[4];
                Criteria criteria3 = Criteria.getCriteria(criName3);
                Criteria criteria4 = Criteria.getCriteria(criName4);

                if (criteria3 != null && criteria4 != null) {
                    if (logicOp.equals("&&") || logicOp.equals("||")) {
                        Criteria binaryCriteria = new CompositeCriteria(criName1, criteria3, logicOp, criteria4);
                        System.out.println("Binary criterion created successfully.");
                    } else {
                        System.out.println("Invalid logical operator. Use '&&' or '||'.");
                    }
                } else {
                    System.out.println("One or both criteria do not exist.");
                }
            }
            if (commandWords[0].equals("printAllCriteria")){
                for(Criteria criteria : Criteria.getAllCriteria()){
                    boolean isDocument = criteria.IsDocument(criteria);
                    if (isDocument == false){
                        System.out.println("It is not a document");
                        continue;
                    }
                    if (criteria instanceof compositeCriteria){
                        System.out.println("attrName is " + criteria.getAttrName() +"\n" +
                                           "op is " + criteria.getOp()+"\n" + 
                                           "val is "+criteria.getVal()+"\n" +
                                           "logicOp is " + ((compositeCriteria) criteria).getLogicOp());}
                    else{System.out.println("attrName is " + criteria.getAttrName() +"\n" +
                                            "op is " + criteria.getOp()+"\n" +
                                            "val is "+criteria.getVal());}
                }
                }
            }
            if (commandWords[0].equals("search")){
                String criName = commandWords[1];
                Criteria criteria = Criteria.getCriteria(criName);

            }

            if (commandWords[0].equals("newSimpleCri")) {
                String criName = commandWords[1];
                String attrName = commandWords[2];
                String op = commandWords[3];
                String val = commandWords[4];

                if (criName.length() == 2 && criName.matches("[a-zA-Z]{2}")) {
                    boolean valid = false;
                    if (attrName.equals("name") && op.equals("contains") && val.startsWith("\"") && val.endsWith("\"")) {
                        valid = true;
                    } else if (attrName.equals("type") && op.equals("equals") && val.startsWith("\"") && val.endsWith("\"")) {
                        valid = true;
                    } else if (attrName.equals("size") && (op.equals(">") || op.equals("<") || op.equals(">=") || op.equals("<=") || op.equals("==") || op.equals("!=")) && val.matches("\\d+")) {
                        valid = true;
                    }

                    if (valid) {
                        Criteria criteria = new Criteria(criName, attrName, op, val);
                        System.out.println("Simple criterion created successfully.");
                    } else {
                        System.out.println("Invalid criteria parameters.");
                    }
                } else {
                    System.out.println("Invalid criteria name.");
                }
            }

            System.out.println(cvfs.getDir().toString()+">");
        }

        scanner.close();
    }
}
