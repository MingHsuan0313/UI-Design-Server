package com.selab.uidesignserver.ServiceComponentService.visitors;

import com.sun.source.tree.*;
import com.sun.source.util.TreeScanner;

import java.util.List;

/*
 *  This class is aimed to get all methods of a class but not in its inner class.
 */

public class ImportVisitor extends TreeScanner<Void, List<ImportTree>> {

    @Override
    public Void visitImport(ImportTree node,List<ImportTree> Objects) {
        if(node != null) {
            Objects.add(node);
        }
        return super.visitImport(node, Objects);
    }
}