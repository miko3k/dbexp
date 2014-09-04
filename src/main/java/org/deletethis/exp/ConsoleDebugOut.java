package org.deletethis.exp;

/**
 *
 * @author miko
 */
public class ConsoleDebugOut implements DebugOut {

    @Override
    public void println(String message) {
        System.err.println(message);
    }
}
