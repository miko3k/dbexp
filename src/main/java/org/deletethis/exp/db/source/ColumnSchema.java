package org.deletethis.exp.db.source;

/**
 *
 * @author miko
 */
public class ColumnSchema {
    private final String name;
    private final ColumnType type;

    public ColumnSchema(String name, ColumnType type) {
        this.name = name;
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public ColumnType getType() {
        return type;
    }
    
    @Override
    public String toString()
    {
        return name + " " + type;
    }
    
}
