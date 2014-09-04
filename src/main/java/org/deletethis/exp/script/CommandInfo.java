package org.deletethis.exp.script;

import org.deletethis.exp.script.commands.CharsetCommand;
import org.deletethis.exp.script.commands.OracleConnectionCommand;
import java.util.Map;
import org.deletethis.exp.script.commands.ExportCommand;
import org.deletethis.exp.script.commands.ExportSequenceCommand;

public enum CommandInfo {
    CONNECT("export from oracle database <URL> as USR using password PWD", "connect to database") {
        @Override
        public Command createCommand(Map<String,String> args) {
            return new OracleConnectionCommand(args.get("USR"), args.get("PWD"), args.get("URL"));
        }
    },
    ENCODING("export encoding is CHARSET", "select output charset") {
        @Override
        public Command createCommand(Map<String, String> args) {
            return new CharsetCommand(args.get("CHARSET"));
        }
    },
    EXPORT_DATA1("export data from TABLE", "export all data from table") {

        @Override
        public Command createCommand(Map<String, String> args) {
            return new ExportCommand(args.get("TABLE"), null);
        }
        
    },
    EXPORT_DATA2("export data from TABLE <SQL>", "export ordered or filtered data") {

        @Override
        public Command createCommand(Map<String, String> args) {
            return new ExportCommand(args.get("TABLE"), args.get("SQL"));
        }
    },
    EXPORT_SEQ_VALUE("export value of sequence SEQUENCE", "export current sequence value") {

        @Override
        public Command createCommand(Map<String, String> args) {
            return new ExportSequenceCommand(args.get("SEQUENCE"));
        }
        
    },    
    ;

    private final String desc;
    private final Template template;
    public abstract Command createCommand(Map<String,String> args);
    
    private CommandInfo(String template, String desc) {
        this.desc = desc;
        this.template = new Template(template);
    }

    public String getDescription() {
        return desc;
    }

    public Template getTemplate() {
        return template;
    }
}