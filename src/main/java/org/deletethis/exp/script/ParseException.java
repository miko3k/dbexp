package org.deletethis.exp.script;

/**
 *
 * @author miko
 */
public class ParseException extends Exception {
    private final String badLine;

    public ParseException(String badLine) {
        this.badLine = badLine;
    }

    public String getBadLine() {
        return badLine;
    }
    

    

}
