package com.selab.uidesignserver.ServiceComponentService;

import com.sun.tools.javac.file.JavacFileManager;

import java.util.ArrayList;
import java.util.List;
import java.io.IOException;
import java.nio.charset.Charset;

import com.sun.source.tree.*;
import com.sun.source.util.JavacTask;
import com.sun.tools.javac.api.JavacTool;
import com.sun.tools.javac.util.Context;
import javax.tools.JavaFileObject;
import javax.tools.JavaCompiler;

public class CodeParser {
    private JavacFileManager fileManager;
    private JavacTool javacTool;

    public CodeParser () {
        System.out.println("constructor");
        Context context = new Context();
        fileManager = new JavacFileManager(context, true, Charset.defaultCharset());
        javacTool = JavacTool.create();
    }

    public void addEditedServiceComponent(String editedServicePath, String originalServicePath) {
        MethodTree methodTree = this.parseServiceComponent(editedServicePath);
        ClassTree classTree = this.parseJavaFile(originalServicePath);
        // identify if service signature is unique
        this.identifySignatureUnique(methodTree, classTree);
        String code = classTree.toString().substring(0, classTree.toString().length() - 1) + methodTree.toString()
                + "\n}";
        // System.out.println(code);
    }

    public boolean identifySignatureUnique(MethodTree methodTree, ClassTree classTree) {
        List<MethodTree> methodTrees = new ArrayList<>();
        classTree.accept(new MethodVisitor(), methodTrees);
        for (int index = 0; index < methodTrees.size(); index++) {
            if (methodTrees.get(index).getName().toString().equals(methodTree.getName().toString())) {
                List<? extends VariableTree> variableTrees = methodTree.getParameters();
                List<? extends VariableTree> targetVariableTrees = methodTrees.get(index).getParameters();
                if(variableTrees.size() == targetVariableTrees.size()) {
                    boolean identityFlag = true;
                    for(int variableIndex = 0;variableIndex < variableTrees.size();variableIndex++) {
                        String variableType = variableTrees.get(variableIndex).getType().toString();
                        String targetVariableType = targetVariableTrees.get(variableIndex).getType().toString();
                        if(!variableType.equals(targetVariableType)) {
                           identityFlag = false; 
                           break;
                        }
                    }
                    if(identityFlag) {
                        System.out.println("Signature is the same");
                    }
                    else {
                        System.out.println("Argument type isn't the same");
                        System.out.println("Signature isn't the same");
                    }
                }
                else {
                    System.out.println("Arguments isn't the same");
                    System.out.println("Signature isn't the same");
                }
                for (int variableIndex = 0; variableIndex < variableTrees.size(); variableIndex++) {
                    VariableTree variableTree = variableTrees.get(variableIndex);
                    System.out.println("Parameter : " + variableTree.getType());
                }

            } else {
                System.out.println("Name isn't the same");
                System.out.println("Signature isn't the same");
            }
        }
        return false;
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
        // System.out.println(files.toString());
        JavaCompiler.CompilationTask compilationTask = javacTool.getTask(null, fileManager, null, null, null, files);
        JavacTask javacTask = (JavacTask) compilationTask;

        List<ClassTree> classTreeContainer = new ArrayList<>();
        try {
            Iterable<? extends CompilationUnitTree> result = javacTask.parse();

            for (CompilationUnitTree tree : result) {
                // tree.getPackage();
                System.out.println(tree.getPackageName());

                List<ImportTree> importTreeContainer = new ArrayList<>();
                tree.accept(new ImportVisitor(), importTreeContainer);
                ImportTree importTree = importTreeContainer.get(0);
                System.out.println(importTree.toString());

                tree.accept(new ClassVisitor(), classTreeContainer);
                for (Object classTreeObj : classTreeContainer) {
                    ClassTree classTree = (ClassTree) classTreeObj;
                    List<MethodTree> methodTrees = new ArrayList<>();
                    classTree.accept(new MethodVisitor(), methodTrees);
                    for (int index = 0; index < methodTrees.size(); index++) {
                        MethodTree methodTree = methodTrees.get(index);
                        List<? extends VariableTree> variableTrees = methodTree.getParameters();
                        System.out.println("Method : " + methodTree.getName());
                        for (int variableIndex = 0; variableIndex < variableTrees.size(); variableIndex++) {
                            VariableTree variableTree = variableTrees.get(variableIndex);
                            System.out.println("Parameter : " + variableTree.getType());
                        }
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return classTreeContainer.get(0);
    }
}
