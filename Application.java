package hk.edu.polyu.comp.comp2021.cvfs;

import hk.edu.polyu.comp.comp2021.cvfs.model.*;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.Scanner;
import java.util.Stack;

public class Application {

    public static void main(String[] args) {
        CVFS cvfs = new CVFS();
        // Initialize and utilize the system
        Stack<Directory> stack = new Stack<>();//存储所有路径
        Scanner scanner = new Scanner(System.in);
        String command;
        String[] commandWords;
        Criteria isDoc=new Criteria("IsDocument","size",">","20");

        System.out.println("Any command or 'quit' to quit.");

        while (true) {
            System.out.print("Command: ");
            command = scanner.nextLine();
            commandWords = command.split("\\s+");

            if (command.equalsIgnoreCase("quit")) {
                System.out.println("Terminate the execution of the system.");
                break;
            }

            else if (commandWords[0].equals("newDisk")) {
                // 如果有就先关掉
                if (cvfs.getDisk() != null) {
                    System.out.println("Closing the existing disk before creating a new one.");
                    cvfs.close();
                }

                // 检测是否是数字
                if (commandWords.length < 2 || !commandWords[1].matches("\\d+")) {
                    System.out.println("Invalid input. Please provide a valid number for the disk size.");
                    continue; 
                }
                int size = Integer.parseInt(commandWords[1]);
                Disk disk = new Disk(size);
                cvfs.setDisk(disk);
                cvfs.setDir(cvfs.getDisk().getDir());
                stack.push(cvfs.getDir());
                System.out.println("New disk created successfully.");
            }

            else if (commandWords[0].equals("newDoc")) {
                // 检测是否有工作目录
                if (cvfs.getDir() == null) {
                    System.out.println("No current working directory set. Please set a directory before creating a new document.");
                    continue;
                } else if (cvfs.getDir().get(commandWords[1]) instanceof Document) {
                    // 检测是否有相同名称文件
                    System.out.println("A document with the name '" + commandWords[1] + "' already exists. Cannot create a document with same name.");
                } else {
                    Document doc = new Document(commandWords[1], commandWords[2], commandWords[3]);
                    cvfs.getDir().put(commandWords[1], doc);
                    System.out.println("New document created successfully.");
                }
            }

            else if (commandWords[0].equals("newDir")) {
                // 检测同名
                if (cvfs.getDir().get(commandWords[1]) != null) {
                    System.out.println("A directory with the name '" + commandWords[1] + "' already exists. Cannot create a directory with same name.");
                } else {
                    Directory dir = new Directory(commandWords[1]);
                    cvfs.getDir().put(commandWords[1], dir);
                    System.out.println("New directory created successfully.");
                }
            }

            else if (commandWords[0].equals("delete")) {
                // 检测工作目录，是否有该名称文件
                if (cvfs.getDisk() == null) {
                    System.out.println("No disk is set. Please create a disk before attempting to delete a document.");
                    continue;
                } else if (cvfs.getDir() == null) {
                    System.out.println("No current working directory set. Please set a directory before attempting to delete a document.");
                    continue;
                } else if (cvfs.getDir().get(commandWords[1]) == null) {
                    System.out.println("Document '" + commandWords[1] + "' does not exist. Cannot delete.");
                } else {
                    cvfs.getDir().delete(commandWords[1]);
                    System.out.println("Delete successfully.");
                }
            }

            else if (commandWords[0].equals("changeDir")) {
                // 处理".."的情况，else是在根目录
                if (commandWords[1].equals("..")) {
                    if (!cvfs.getDir().toString().equals("Disk")) {
                        stack.pop();
                        cvfs.setDir(stack.peek());
                    } else {
                        System.out.println("Already at the root directory. Cannot go up.");
                        
                    }
                //处理空目录
                } else if (cvfs.getDir().get(commandWords[1]) != null) {
                    cvfs.setDir((Directory) cvfs.getDir().get(commandWords[1]));
                    stack.push(cvfs.getDir());
                } else {
                    System.out.println("Directory '" + commandWords[1] + "' does not exist. Cannot change directory.");
                    
                }
            }

            else if (commandWords[0].equals("list")) {
                cvfs.getDir().list();
            }

            else if (commandWords[0].equals("rList")) {
                cvfs.getDir().rList(0);
            }

            else if (commandWords[0].equals("newNegation")) {
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
            else if (commandWords[0].equals("newBinaryCri")) {
                String criName1 = commandWords[1];
                String criName3 = commandWords[2];
                String logicOp = commandWords[3];
                String criName4 = commandWords[4];
                Criteria criteria3 = Criteria.getCriteria(criName3);
                Criteria criteria4 = Criteria.getCriteria(criName4);

                if (criteria3 != null && criteria4 != null) {
                    if (logicOp.equals("&&") || logicOp.equals("||")) {
                        Criteria binaryCriteria = new compositeCriteria(criName1, criteria3, logicOp, criteria4);
                        System.out.println("Binary criterion created successfully.");
                    } else {
                        System.out.println("Invalid logical operator. Use '&&' or '||'.");
                    }
                } else {
                    System.out.println("One or both criteria do not exist.");
                }
            }
            else if (commandWords[0].equals("printAllCriteria")) {
                for (Criteria criteria : Criteria.getAllCriteria()) {
                    if (criteria instanceof compositeCriteria) {
                        criteria.print();
                    } else if(criteria.getCriName().equals("IsDocument")){
                        System.out.println("IsDocument");
                    }else {
                        System.out.println(criteria.getAttrName() +" "+ criteria.getOp() +" " + criteria.getVal());
                    }
                }
            }

            else if (commandWords[0].equals("search")) {
                String criName = commandWords[1];
                Criteria criteria = Criteria.getCriteria(criName);

                if (criteria != null) {
                    cvfs.getDisk().search(criteria);
                } else {
                    System.out.println("Criterion " + criName + " does not exist.");
                }
            }

            else if (commandWords[0].equals("rSearch")) {
                String criName = commandWords[1];
                Criteria criteria = Criteria.getCriteria(criName);

                if (criteria != null) {
                    cvfs.getDisk().rSearch(criteria);
                } else {
                    System.out.println("Criterion " + criName + " does not exist.");
                }
            }


            else if (commandWords[0].equals("newSimpleCri")) {
                String criName = commandWords[1];
                String attrName = commandWords[2];
                String op = commandWords[3];
                String val = commandWords[4];
                Criteria criteria = new Criteria(criName,attrName,op,val);

            }


            else if (commandWords[0].equals("save")) {
                //如果超过大小，则超出部分会保存在虚拟磁盘上，但无法执行save命令
                if (cvfs.getDir().getAllSize()>cvfs.getDisk().getSize()){
                    System.out.println("This disk is full, please delete some files and then run the 'save' command");
                    continue;
                }
                stack.get(0).save(commandWords[1]);//对于根目录进行操作，stack第一个就是Disk的根目录。save方法写在Directory类里。
                try (FileWriter writer = new FileWriter(commandWords[1], true)) { // 以追加模式打开文件
                    writer.write(cvfs.getDisk().getSize() + System.lineSeparator()); // 写入内容并换行
                } catch (IOException e) {
                    System.err.println(e.getMessage());
                }
            }

            else if (commandWords[0].equals("load")){
                try {
                    List<String> lines = Files.readAllLines(Paths.get(commandWords[1]));

                    //创建Disk
                    int size=Integer.parseInt(lines.get(lines.size()-1));
                    Disk disk=new Disk(size);
                    cvfs.setDisk(disk);
                    cvfs.setDir(cvfs.getDisk().getDir());
                    stack.push(cvfs.getDir());
                    System.out.println("Disk loaded successfully.");
                    lines.remove(lines.size()-1);//最后一行 Disk大小 用完了删掉

                    lines.remove(lines.size()-1);//最后一行Total number没用，删掉
                    int indents=0;
                    for (String line : lines){
                        //如果缩进等于indents，不用任何改变
                        //缩进不可能大于indents，因为每一个可能的缩进增加，indents都增加了。缩进不等于indents，一定是缩进小于indents。回到上一个目录并再次检查。
                        String expectedSpaces = new String(new char[indents]).replace('\0', ' ');
                        while(line.substring(0,indents).equals(expectedSpaces)==false && indents>=2){
                            stack.pop();
                            cvfs.setDir(stack.peek());
                            indents-=2;//回到上一个目录
                        }
                        int colonCount = line.split(":").length - 1;//冒号的数量
                        if(colonCount==3){//一定是newDoc命令
                            String[] words=line.trim().split(" ");
                            Document doc=new Document(words[1],words[3],words[6]);
                            cvfs.getDir().put(words[1],doc);
                            System.out.println("New document created successfully.");
                        }else if(colonCount==2){//newDir命令或者Total行
                            String[] words=line.trim().split(" ");
                            if(words[0].equals("Total"))continue;//遇到Total行就跳过，进行下一行。
                            Directory dir=new Directory(words[1]);
                            cvfs.getDir().put(words[1],dir);
                            System.out.println("New directory created successfully.");

                            cvfs.setDir((Directory) cvfs.getDir().get(words[1]));
                            stack.push(cvfs.getDir());
                            indents+=2;//创建一个Dir之后自动changeDir
                        }
                    }
                } catch (IOException e) {
                    System.err.println("load error"+e.getMessage());
                }
            }
            // 一般情况
            else{
                System.out.println("Invalid input, please enter again:");
                continue;
            }
            // 大小检测，出问题会有提示
            if (cvfs.getDir().getAllSize()>cvfs.getDisk().getSize()){
                System.out.println("This disk is full, please note");
            }

            System.out.println(cvfs.getDir().toString() + ">");
        }
        scanner.close();
    }
}
