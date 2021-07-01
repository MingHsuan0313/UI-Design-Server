
package com.selab.uidesignserver.ServiceComponentService;
import com.sun.source.tree.*;
import com.sun.tools.javac.api.JavacTool;
public class newCodeParser {
	private JavacTool javacTool;

	public boolean checkSignatureUnique(String newService, String originProjectName, String originClassName) {
		return true;
	}

	public MethodTree parseServiceComponent(String service) {
		MethodTree mt = null;
		return mt;
	}

	public CompilationUnitTree parseJavaFile(String path) {
		CompilationUnitTree ct = null;
		return ct;
	}

	public boolean checkIsFileExisted(String path) {
		return true;
	}
}
