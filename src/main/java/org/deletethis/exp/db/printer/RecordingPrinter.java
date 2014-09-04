package org.deletethis.exp.db.printer;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author miko
 */
public class RecordingPrinter implements Printer {

    private static interface Command {
        void run(Printer p);
    }
    
    private final List<Command> commands = new ArrayList<>();

    @Override
    public void printAtoms(final String... str) {
        commands.add(new Command() {
            @Override
            public void run(Printer p) {
                p.printAtoms(str);
            }
        });
    }

    @Override
    public void printAtomsLn(final String... str) {
        commands.add(new Command() {
            @Override
            public void run(Printer p) {
                p.printAtomsLn(str);
            }
        });
    }

    @Override
    public void printQuoted(final String str) {
        commands.add(new Command() {
            @Override
            public void run(Printer p) {
                p.printQuoted(str);
            }
        });
    }

    @Override
    public void newline() {
        commands.add(new Command() {
            @Override
            public void run(Printer p) {
                p.newline();
            }
        });
    }

    @Override
    public void indent() {
        commands.add(new Command() {
            @Override
            public void run(Printer p) {
                p.indent();
            }
        });
    }

    @Override
    public void unindent() {
        commands.add(new Command() {
            @Override
            public void run(Printer p) {
                p.unindent();
            }
        });
    }
    
    public void print(Printer p) {
        for(Command c: commands) {
            c.run(p);
        }
    }
    
}
