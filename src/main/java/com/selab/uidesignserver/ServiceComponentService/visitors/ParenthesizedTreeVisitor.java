package com.selab.uidesignserver.ServiceComponentService.visitors;

import com.sun.source.tree.MethodTree;
import com.sun.source.tree.*;
import com.sun.source.util.TreeScanner;

import java.util.List;

/*
 *  This class is aimed to get all methods of a class but not in its inner class.
 */

public class ParenthesizedTreeVisitor extends TreeScanner<Void, List<ParenthesizedTree>> {

    // Prevent to get into inner classes.
    @Override
    public Void scan(Tree node, List<ParenthesizedTree> objects){
        if (node instanceof ClassTree){
            return null;
        }
        return super.scan(node, objects);
    }

    @Override
    public Void visitParenthesized(ParenthesizedTree node, List<ParenthesizedTree> objects) {
        objects.add(node);
        return super.visitParenthesized(node, objects);
    }
}