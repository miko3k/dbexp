package org.deletethis.exp;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.Arrays;
import java.util.List;
import org.deletethis.exp.db.oracle.OracleDestination;
import org.deletethis.exp.script.Command;
import org.deletethis.exp.script.CommandException;
import org.deletethis.exp.script.CommandInfo;
import org.deletethis.exp.script.ExecutionState;
import org.deletethis.exp.script.ParseException;
import org.deletethis.exp.script.Parser;

/**
 *
 * @author miko
 */
public class Main {
    private static final List<String> HELPS = Arrays.asList("-h", "--help", "-help", "-?");
    
    public static final void main(String [] args) 
            throws Exception
    {
        if(args.length != 1) {
            System.err.println("I want script name as argument. You can also try --help.");
            return;
        }
        String arg = args[0];
        if(HELPS.contains(arg)) {
            System.err.println("available commands:");
            for(CommandInfo ci: CommandInfo.values()) {
                System.err.println("  " + ci.getTemplate().getText());
            }
            System.err.println("  # comment");
            System.err.println("  anything else will be copied to output");
            return;
        }
        
        FileReader reader = new FileReader(arg);
        BufferedReader br = new BufferedReader(reader);
        
        ExecutionState state = new ExecutionState(System.out);
        state.setDestinationSql(new OracleDestination());
        
        Parser p = new Parser();
        String line;
        int lineNo = 1;
        while((line = br.readLine()) != null) {
            Command c;
            try {
                c = p.parseLine(line);
                c.execute(state);
            } catch(ParseException | CommandException e) {
                throw new Exception("executing script, line " + lineNo, e);
            }
            lineNo++;
        }
    }
}
