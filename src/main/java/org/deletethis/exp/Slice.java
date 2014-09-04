package org.deletethis.exp;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author miko
 */
public class Slice {

    public Slice(int start, int length) {
        this.start = start;
        this.length = length;
    }

    final public int start;
    final public int length;

    public static Iterable<Slice> create(int totalLength, int sliceLength) {
        List<Slice> result = new ArrayList<>();

        for (int start = 0; start < totalLength; start += sliceLength) {
            int end = start + sliceLength;
            if (end > totalLength) {
                end = totalLength;
            }
            result.add(new Slice(start, end - start));
        }
        return result;
    }
}
