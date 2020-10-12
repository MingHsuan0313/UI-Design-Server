package com.selab.uidesignserver.ServiceComponentService;

import com.sun.tools.javac.file.JavacFileManager;

import java.util.ArrayList;
import java.util.List;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.Charset;

import com.sun.source.tree.*;
import com.sun.source.util.JavacTask;
import com.sun.tools.javac.api.JavacTool;
import com.sun.tools.javac.util.Context;
import javax.tools.JavaFileObject;
import javax.tools.JavaCompiler;
import com.selab.uidesignserver.ServiceComponentService.visitors.*;

public class CodeParser {
    private JavacFileManager fileManager;
    private JavacTool javacTool;
    public String resultString;
    public String originalCode;
    public String sourceFilePath;

    public CodeParser(String filePath) {
        this.resultString = "";
        this.sourceFilePath = filePath;
        this.originalCode = "";
        Context context = new Context();
        this.fileManager = new JavacFileManager(context, true, Charset.defaultCharset());
        this.javacTool = JavacTool.create();
    }

    public String getResult() {
        return this.resultString;
    }

    public void recover() {
        System.out.println("starting recover");
        System.out.println(this.sourceFilePath);
        System.out.println(this.originalCode);
        this.writeFile(this.sourceFilePath, this.originalCode);
    }

    public String addEditedServiceComponent(String editedServicePath, String originalServicePath) {
        MethodTree methodTree = this.parseServiceComponent(editedServicePath);
        ClassTree classTree = this.parseJavaFile(originalServicePath);
        this.originalCode = this.resultString;
        // identify if service signature is unique
        if (this.identifySignatureUnique(methodTree, classTree)) {
            // System.out.println("Update Service Component....");
            String code = this.resultString.substring(0, resultString.toString().length() - 1) + methodTree.toString()
                    + "\n}";
            // System.out.println(code);
            // System.out.println("HelloWorld111");
            return code;
        } else {
            System.out.println("Because Signature is the same , you must modify your function signature");
            return "";
        }
    }

    public String checkingSignatureUnique(String editedServicePath, String originalServicePath) {
        MethodTree methodTree = this.parseServiceComponent(editedServicePath);
        ClassTree classTree = this.parseJavaFile(originalServicePath);
        this.originalCode = this.resultString;
        // identify if service signature is unique
        if (this.identifySignatureUnique(methodTree, classTree)) {
            System.out.println("Update Service Component....");
            String code = this.resultString.substring(0, resultString.toString().length() - 1) + methodTree.toString()
                    + "\n}";
            // System.out.println(code);
            return code;
        } else {
            System.out.println("Because Signature is the same , you must modify your function signature");
            return "";
        }
    }

    public boolean identifySignatureUnique(MethodTree methodTree, ClassTree classTree) {
        List<MethodTree> methodTrees = new ArrayList<>();
        classTree.accept(new MethodVisitor(), methodTrees);
        for (int index = 0; index < methodTrees.size(); index++) {
            if (methodTrees.get(index).getName().toString().equals(methodTree.getName().toString())) {
                List<? extends VariableTree> variableTrees = methodTree.getParameters();
                List<? extends VariableTree> targetVariableTrees = methodTrees.get(index).getParameters();
                if (variableTrees.size() == targetVariableTrees.size()) {
                    boolean identityFlag = true;
                    for (int variableIndex = 0; variableIndex < variableTrees.size(); variableIndex++) {
                        String variableType = variableTrees.get(variableIndex).getType().toString();
                        String targetVariableType = targetVariableTrees.get(variableIndex).getType().toString();
                        if (!variableType.equals(targetVariableType)) {
                            System.out.println(methodTree.getName());
                            System.out.println(methodTrees.get(index).getName());
                            identityFlag = false;
                            break;
                        }
                    }
                    if (identityFlag) {
                        return false;
                    }
                }
            }
        }
        return true;
    }

    // java file which only has function
    public MethodTree parseServiceComponent(String path) {
        System.out.println("parser service component : " + path);
        Iterable<? extends JavaFileObject> files = fileManager.getJavaFileObjects(path);
        JavaCompiler.CompilationTask compilationTask = this.javacTool.getTask(null, fileManager, null, null, null,
                files);
        JavacTask javacTask = (JavacTask) compilationTask;
        List<MethodTree> methodTrees = new ArrayList<>();
        try {
            Iterable<? extends CompilationUnitTree> result = javacTask.parse();
            for (CompilationUnitTree tree : result) {
                List<ClassTree> classTreeContainer = new ArrayList<>();
                tree.accept(new ClassVisitor(), classTreeContainer);
                for (ClassTree classTree : classTreeContainer) {
                    classTree.accept(new MethodVisitor(), methodTrees);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return methodTrees.get(0);
    }

    public ClassTree parseJavaFile(String path) {
        System.out.println("parser file...");
        Iterable<? extends JavaFileObject> files = fileManager.getJavaFileObjects(path);
        JavaCompiler.CompilationTask compilationTask = javacTool.getTask(null, fileManager, null, null, null, files);
        JavacTask javacTask = (JavacTask) compilationTask;

        List<ClassTree> classTreeContainer = new ArrayList<>();
        try {
            Iterable<? extends CompilationUnitTree> result = javacTask.parse();

            for (CompilationUnitTree tree : result) {
                System.out.println(tree.getPackageName());

                resultString += "package " + tree.getPackageName() + ";\n";

                List<ImportTree> importTreeContainer = new ArrayList<>();
                tree.accept(new ImportVisitor(), importTreeContainer);
                for (int importTreeIndex = 0; importTreeIndex < importTreeContainer.size(); importTreeIndex++) {
                    resultString += importTreeContainer.get(importTreeIndex).toString();
                }

                tree.accept(new ClassVisitor(), classTreeContainer);
                for (Object classTreeObj : classTreeContainer) {
                    ClassTree classTree = (ClassTree) classTreeObj;
                    List<MethodTree> methodTrees = new ArrayList<>();
                    classTree.accept(new MethodVisitor(), methodTrees);
                    for (int index = 0; index < methodTrees.size(); index++) {
                        MethodTree methodTree = methodTrees.get(index);
                        List<? extends VariableTree> variableTrees = methodTree.getParameters();
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        resultString = resultString + classTreeContainer.get(0).toString();
        return classTreeContainer.get(0);
    }

    public void writeFile(String path, String text) {
        System.out.println("path here : " + path);
        File fileObj = new File(path);
        if (fileObj.exists()) {
            try {
                FileWriter myWriter = new FileWriter(path);
                myWriter.write(text);
                myWriter.close();
            } catch (IOException e) {
                System.out.println("An error occurred.");
                e.printStackTrace();
            }
        } else {
            try {
                fileObj.createNewFile();
                FileWriter myFileWriter = new FileWriter(path);
                myFileWriter.write(text);
                myFileWriter.close();
                System.out.println("create file successfully");
            } catch (IOException e) {
                e.printStackTrace();
                System.out.println("create not success");
            }
        }
    }
}
