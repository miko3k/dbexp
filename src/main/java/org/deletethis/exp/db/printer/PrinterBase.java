package org.deletethis.exp.db.printer;

/**
 *
 * @author miko
 */
abstract public class PrinterBase implements Printer {
    abstract protected void printAtom(String str);
    
    private void validateAtom(String str) {
        if(str == null)
            throw new IllegalArgumentException("atom cannot be null");

        if(str.isEmpty())
            throw new IllegalArgumentException("atom cannot be empty string");
        
        for(int i=0;i<str.length();++i) {
            char c = str.charAt(i);
            if(Character.isWhitespace(c)) {
                throw new IllegalArgumentException("atom cannot contain a whitespace: " + str);
            }
        }
    }
    
    @Override
    final public void printAtoms(String ... str) {
        for(String s: str) {
            validateAtom(s);
            printAtom(s);
        }
    }
    
    @Override
    final public void printAtomsLn(String ... str) {
        for(String s: str) {
            validateAtom(s);
            printAtom(s);
        }
        newline();
    }
}
