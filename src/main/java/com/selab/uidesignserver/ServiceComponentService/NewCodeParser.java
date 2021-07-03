
package com.selab.uidesignserver.ServiceComponentService;

import java.io.IOError;
import java.io.IOException;
import java.nio.charset.Charset;

import com.sun.source.tree.*;
import com.sun.tools.javac.api.JavacTool;
import com.sun.tools.javac.file.JavacFileManager;
import com.sun.tools.javac.util.Context;
import com.selab.uidesignserver.ServiceComponentService.visitors.*;
import com.sun.source.util.JavacTask;
import javax.tools.JavaFileObject;
import javax.tools.JavaCompiler;

import java.util.*;

public class NewCodeParser {
    private JavacTool javacTool;
    private JavacFileManager fileManager;

    public NewCodeParser() {
        Context context = new Context();
        this.fileManager = new JavacFileManager(context, true, Charset.defaultCharset());
        this.javacTool = JavacTool.create();
    }

    public MethodTree parseServiceComponent(String path) {
        MethodTree mt = null;
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

    public CompilationUnitTree parseJavaFile(String path) throws IOException {
        Iterable<? extends JavaFileObject> files = fileManager.getJavaFileObjects(path);
        JavaCompiler.CompilationTask compilationTask = this.javacTool.getTask(null, fileManager, null, null, null,
                files);
        JavacTask javacTask = (JavacTask) compilationTask;
        List<MethodTree> methodTrees = new ArrayList<>();
        try {
            Iterable<? extends CompilationUnitTree> result = javacTask.parse();
            for (CompilationUnitTree tree : result) {
                return tree;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public ClassTree parseClass(String path) throws IOException {
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
                    return classTree;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public boolean checkIsFileExisted(String path) {
        return true;
    }

    public boolean isSignatureTheSame(MethodTree method1, MethodTree method2) {
        if (method1.getName().toString().equals(method2.getName().toString())) {
            List<? extends VariableTree> variableTrees = method1.getParameters();
            List<? extends VariableTree> targetVariableTrees = method2.getParameters();
            if (variableTrees.size() == targetVariableTrees.size()) {
                boolean identityFlag = true;
                for (int variableIndex = 0; variableIndex < variableTrees.size(); variableIndex++) {
                    String variableType = variableTrees.get(variableIndex).getType().toString();
                    String targetVariableType = targetVariableTrees.get(variableIndex).getType().toString();
                    if (!variableType.equals(targetVariableType)) {
                        identityFlag = false;
                        break;
                    }
                }
                if (identityFlag) {
                    return true;
                }
            }
        }
        return false;
    }

    public boolean identifySignatureUnique(ClassTree classTree, MethodTree methodTree) {
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
                            // System.out.println(methodTree.getName());
                            // System.out.println(methodTrees.get(index).getName());
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

}
