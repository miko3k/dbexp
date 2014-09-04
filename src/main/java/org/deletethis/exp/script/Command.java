package org.deletethis.exp.script;

/**
 *
 * @author miko
 */
public interface Command {
    public void execute(ExecutionState state) throws CommandException;
}
