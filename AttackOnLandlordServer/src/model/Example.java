package model;

import java.util.List;

import com.alibaba.fastjson.JSON;
 
import jdk.jshell.JShell;
import jdk.jshell.SnippetEvent;
 
public class Example {
 
    private static JShell shell;
 
    public static synchronized void eval(String command) {
        shell = JShell.builder().build();
        List<SnippetEvent> events = shell.eval(command);
        System.out.println(events);
        System.out.println(JSON.toJSONString(events));
 
    }
 
    public static void main(String[] args) {
        eval("System.out.println(\"Hello,World!\")");
    }
 
}