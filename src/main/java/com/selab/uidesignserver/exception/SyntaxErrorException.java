package com.selab.uidesignserver.exception;

public class SyntaxErrorException extends Exception {
    
    String exceptionContent;
    
    public SyntaxErrorException(String msg) {
        this.exceptionContent = msg;
    }
    
    public String toString() {
        return("Syntax Error Exception: " + this.exceptionContent);
    }
    
}
