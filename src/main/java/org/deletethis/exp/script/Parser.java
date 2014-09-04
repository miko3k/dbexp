package org.deletethis.exp.script;

import org.deletethis.exp.script.commands.PrintLnCommand;
import org.deletethis.exp.script.commands.NopCommand;
import java.util.Map;
import java.util.regex.Pattern;

/**
 *
 * @author miko
 */
public class Parser {

    Pattern comment = Pattern.compile("\\s*#.*");
    Pattern anyCommand = Pattern.compile("\\s*export\\b.*", Pattern.CASE_INSENSITIVE);
    
    public Command parseLine(String input) throws ParseException {
        if(comment.matcher(input).matches()) {
            return new NopCommand();
        }
        if(!anyCommand.matcher(input).matches()) {
            return new PrintLnCommand(input);
        }
        Map<String,String> map;
        
        for(CommandInfo ci: CommandInfo.values()) {
            Map<String,String> res = ci.getTemplate().match(input);
            if(res != null) {
                return ci.createCommand(res);
            }
        }
        throw new ParseException(input);
    }
}
