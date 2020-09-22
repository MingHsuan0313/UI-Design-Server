package com.selab.uidesignserver.ServiceComponentService.visitors;

import com.sun.source.tree.ClassTree;
import com.sun.source.tree.Tree;
import com.sun.source.util.TreeScanner;

import java.util.List;

/*
 *  This class is aimed to get all classes of a tree but not including their inner classes.
 */
public class ClassVisitor extends TreeScanner<Void, List<ClassTree>> {

    @Override
    public Void scan(Tree node, List<ClassTree> objects){
        if (node instanceof ClassTree){
            objects.add((ClassTree) node);
            return null;
        }
        return super.scan(node, objects);
    }
    
    @Override
    public Void visitClass(ClassTree node,List<ClassTree> objects) {
        objects.add(node);
        return super.visitClass(node,objects);
    }
    
}