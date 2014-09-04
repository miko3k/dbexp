# dbexp

  This is a tool to export database as `insert` statements. Only Oracle database is supported so far. Unlike [SQLDeveloper](http://www.oracle.com/technetwork/developer-tools/sql-developer/overview/index.html) it is able to export more types including `clob` and `blob`. It can also export sequence values. 
  
Output should be executable through SQL*Plus and tries to stick to simple SQL but resorts to PL/SQL blocks when necessary.

Export is driven by script so you can repeat your export at any time.

Feedback is appreciated.

## Usage

`dbexp` reads a script, lines begining with `#` are ignored, lines begining with `export` do the magic. Everything else is simply copied to output, so you can include any other SQL or SQL*Plus statements in your script such as `commit`, `whenever sqlerror...` or DDL for your tables.

You can see the [source](https://github.com/miko3k/dbexp/blob/master/src/main/java/org/deletethis/exp/script/CommandInfo.java) for list of supported `export` commands.

Input script is always in UTF-8 encoding, output encoding is selectable.

## Compiling

You will need to drop an [Oracle JDBC Driver](http://www.oracle.com/technetwork/database/enterprise-edition/jdbc-112010-090769.html) to `bin` subdirectory, `cd` to `installoracle` and type `mvn install`. It will upload the driver to local maven repository. Then use `mvn` to compile the project as usual.
