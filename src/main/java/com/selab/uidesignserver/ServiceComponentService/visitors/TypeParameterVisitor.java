package com.selab.uidesignserver.ServiceComponentService.visitors;

import com.sun.source.tree.MethodTree;
import com.sun.source.tree.*;
import com.sun.source.util.TreeScanner;

import java.util.List;

/*
 *  This class is aimed to get all methods of a class but not in its inner class.
 */

public class TypeParameterVisitor extends TreeScanner<Void, List<TypeParameterTree>> {

    // Prevent to get into inner classes.
    @Override
    public Void scan(Tree node, List<TypeParameterTree> objects){
        if (node instanceof ClassTree){
            return null;
        }
        return super.scan(node, objects);
    }

    @Override
    public Void visitTypeParameter(TypeParameterTree node, List<TypeParameterTree> objects) {
        objects.add(node);
        return super.visitTypeParameter(node, objects);
    }
}