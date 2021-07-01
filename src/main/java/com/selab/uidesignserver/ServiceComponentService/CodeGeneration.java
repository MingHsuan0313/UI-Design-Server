package com.selab.uidesignserver.ServiceComponentService;

public class CodeGeneration {
	public String projectName;
	public String className;
	public Mode mode;

	public enum Mode {
		EDIT_SERVICE {
		    public String toString() {
     			return "Edit Service";
		    }
		},

		ADD_SERVICE {
	        public String toString() {
        	    return "Add Service";
      		}
		}
	}

	public boolean genJavaFile(String code, String path) {
		return true;
	}

	public boolean doGitVersionControl(String projectName) {
		return true;
	}
}