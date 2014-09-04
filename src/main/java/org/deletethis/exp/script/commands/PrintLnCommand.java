package org.deletethis.exp.script.commands;

import org.deletethis.exp.script.Command;
import org.deletethis.exp.script.ExecutionState;

/**
 *
 * @author miko
 */
public class PrintLnCommand implements Command {
    private final String data;

    public PrintLnCommand(String data) {
        this.data = data;
    }
    
    @Override
    public void execute(ExecutionState state) {
        state.appendln(data);
    }
    
}
