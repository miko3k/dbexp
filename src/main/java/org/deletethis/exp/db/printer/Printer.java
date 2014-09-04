package org.deletethis.exp.db.printer;

/**
 *
 * @author miko
 */
public interface Printer {
    public void printAtoms(String ... str);
    public void printAtomsLn(String ... str);
    public void printQuoted(String str);
    public void newline();
    public void indent();
    public void unindent();
}
