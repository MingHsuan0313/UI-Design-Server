
package com.selab.uidesignserver.ServiceComponentService;
import java.nio.charset.Charset;

import com.sun.source.tree.*;
import com.sun.tools.javac.api.JavacTool;
import com.sun.tools.javac.file.JavacFileManager;
import com.sun.tools.javac.util.Context;
import com.selab.uidesignserver.ServiceComponentService.visitors.*;

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
		return mt;
	}

	public ClassTree parseJavaFile(String path) {
		ClassTree ct = null;
		return ct;
	}

	public boolean checkIsFileExisted(String path) {
		return true;
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

}
