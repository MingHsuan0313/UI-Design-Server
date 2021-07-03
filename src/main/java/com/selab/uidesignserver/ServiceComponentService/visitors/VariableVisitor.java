package com.selab.uidesignserver.ServiceComponentService.visitors;

import com.sun.source.tree.ClassTree;
import com.sun.source.tree.MethodTree;
import com.sun.source.tree.Tree;
import com.sun.source.tree.VariableTree;
import com.sun.source.util.TreeScanner;

import java.util.List;

/*
 *  This class is aimed to get all variables in a class but not in its inner class.
 */

public class VariableVisitor extends TreeScanner<Void, List<VariableTree>> {

    // Prevent to get into inner classes and methods.
    @Override
    public Void scan(Tree node, List<VariableTree> objects){
        if (node instanceof ClassTree || node instanceof MethodTree){
            return null;
        }
        return super.scan(node, objects);
    }

    @Override
    public Void visitVariable(VariableTree node, List<VariableTree> objects) {
        objects.add(node);
        return super.visitVariable(node, objects);
    }
}
