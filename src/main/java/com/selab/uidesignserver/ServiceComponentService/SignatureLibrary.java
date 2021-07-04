package com.selab.uidesignserver.ServiceComponentService;

import java.util.*;

public class SignatureLibrary {

	public static Map<String, String> getSignatures() {
		Map<String, String> signatures = new HashMap();
		signatures.put("Integer", "java.lang.Integer");
		signatures.put("int", "int");
		signatures.put("Object", "java.lang.Object");
		signatures.put("String", "java.lang.String");
		return signatures;
	}
}
