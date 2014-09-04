package org.deletethis.exp.script.commands;

import org.deletethis.exp.script.Command;
import org.deletethis.exp.script.ExecutionState;

/**
 *
 * @author miko
 */
public class CharsetCommand implements Command {
    private final String charset;
    
    public CharsetCommand(String charset) {
        this.charset = charset;
    }

    public String getCharset() {
        return charset;
    }

    @Override
    public void execute(ExecutionState state) {
        state.setCharset(charset);
    }
}
