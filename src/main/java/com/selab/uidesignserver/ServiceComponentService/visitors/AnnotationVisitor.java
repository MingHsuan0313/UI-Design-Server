package com.selab.uidesignserver.ServiceComponentService.visitors;

import java.util.*;
import com.sun.source.util.TreeScanner;
import com.sun.source.tree.*;

public class AnnotationVisitor extends TreeScanner<Void, List<AnnotationTree>> {
	@Override
	public Void scan(Tree node, List<AnnotationTree> objects) {
		if (node instanceof MethodTree || node instanceof VariableTree) {
			return null;
		}
		return super.scan(node, objects);
	}

	@Override
	public Void visitAnnotation(AnnotationTree node, List<AnnotationTree> objects) {
		objects.add(node);
		return super.visitAnnotation(node, objects);
	}

}
