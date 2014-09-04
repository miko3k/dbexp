package org.deletethis.exp.db.oracle;


import org.deletethis.exp.db.printer.LineWrapper;
import org.deletethis.exp.db.printer.PoliteAppendableImpl;
import org.junit.Assert;
import org.junit.Test;

/**
 *
 * @author miko
 */

public class LineWrapperTest {
    @Test
    public void test1() {
        StringBuilder sb = new StringBuilder();
        LineWrapper lw = new LineWrapper(new PoliteAppendableImpl(sb), 100, 4);
        lw.chunk("a");
        Assert.assertEquals("a", sb.toString());
    }
    @Test
    public void test2() {
        StringBuilder sb = new StringBuilder();
        LineWrapper lw = new LineWrapper(new PoliteAppendableImpl(sb), 6, 2);
        lw.chunk("a");
        lw.chunk("b");
        lw.chunk("c");
        lw.chunk("d");
        lw.chunk("e");
        Assert.assertEquals("abcde", sb.toString());
    }    
    @Test
    public void test3() {
        StringBuilder sb = new StringBuilder();
        LineWrapper lw = new LineWrapper(new PoliteAppendableImpl(sb), 2, 2);
        lw.chunk("a");
        lw.chunk("b");
        lw.chunk("c");
        lw.chunk("d");
        lw.chunk("e");
        Assert.assertEquals("ab\ncd\ne", sb.toString());
    }      
    
    @Test
    public void test4() {
        StringBuilder sb = new StringBuilder();
        LineWrapper lw = new LineWrapper(new PoliteAppendableImpl(sb), 4, 2);
        lw.chunk("aa");
        lw.chunk("bb");
        lw.indent();
        lw.chunk("cc");
        lw.unindent();
        lw.chunk("dd");
        lw.newline();
        lw.chunk("ee");
        Assert.assertEquals("aabb\n  cc\ndd\nee", sb.toString());
    }   
    
    @Test
    public void test5() {
        StringBuilder sb = new StringBuilder();
        LineWrapper lw = new LineWrapper(new PoliteAppendableImpl(sb), 6, 2);
        lw.chunk("aa");
        lw.space();
        lw.chunk("bb");
        lw.indent();
        lw.space();
        lw.space();
        lw.chunk("cc");
        lw.newline();
        lw.unindent();
        lw.chunk("dd");
        lw.space();
        lw.newline();
        lw.space();
        lw.chunk("ee");
        lw.space();
        lw.space();
        lw.chunk("f");
        Assert.assertEquals("aa bb\n  cc\ndd\nee f", sb.toString());
    }     
}
