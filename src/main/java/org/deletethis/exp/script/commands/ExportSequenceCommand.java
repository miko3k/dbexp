package org.deletethis.exp.script.commands;

import java.math.BigInteger;
import org.deletethis.exp.script.Command;
import org.deletethis.exp.script.CommandException;
import org.deletethis.exp.script.ExecutionState;

/**
 *
 * @author miko
 */
public class ExportSequenceCommand implements Command {
    final private String seqName;

    public ExportSequenceCommand(String seqName) {
        this.seqName = seqName;
    }

    @Override
    public void execute(ExecutionState state) throws CommandException {
        BigInteger sequenceValue = state.getSourceSql().getSequenceValue(seqName);
        state.getDestinationSql().setSequenceValue(state, seqName, sequenceValue);
    }
    
}
