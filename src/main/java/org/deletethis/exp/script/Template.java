package org.deletethis.exp.script;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author miko
 */
public class Template {
    final private Pattern pattern;
    final private String text;
    final private List<String> keys = new ArrayList<>();
    
    public Template(String tpl) {
        this.text = tpl;
        StringBuilder patternString = new StringBuilder();

        int i = 0;
        while(i < tpl.length()) {
            char c = tpl.charAt(i);

            if (c == '<') {
                StringBuilder word = new StringBuilder();
                ++i;
                while(true) {
                    if(i >= tpl.length())
                        throw new IllegalArgumentException("unterminated <");
                    
                    c = tpl.charAt(i);
                    if(c == '>') {
                        break;
                    } else {
                        word.append(c);
                        ++i;
                    }
                }
                ++i;
                keys.add(word.toString());
                patternString.append("(.+?)");
            } else if (Character.isUpperCase(c)) {
                StringBuilder word = new StringBuilder();
                
                while(i < tpl.length()) {
                    c = tpl.charAt(i);
                    if(!Character.isUpperCase(c))
                        break;
                    word.append(c);
                    ++i;
                }
                //System.out.println("XX: " + word);
                patternString.append("([a-zA-Z0-9_-]+?)");
                keys.add(word.toString());
            } else {
                if (c == ' ') {
                    patternString.append("\\s+");
                } else if ((c >= 'a' && c <= 'z') || c == '#') {
                    patternString.append(c);
                } else if (c == ':' || c == '/' || c == '@') {
                    patternString.append("\\s*");
                    if(c == '/') {
                        patternString.append("\\/");
                    } else {
                        patternString.append(c);
                    }
                    
                    patternString.append("\\s*");
                } else {
                    throw new IllegalArgumentException("bad template: " + tpl + ", character: " + c);
                }

                ++i;
            }
        }
        pattern = Pattern.compile(patternString.toString(), Pattern.CASE_INSENSITIVE);
    }
    
    public Map<String,String> match(String input) {
        Matcher matcher = pattern.matcher(input.trim());
        if(!matcher.matches())
            return null;
        
        int cnt = matcher.groupCount();
        Map<String,String> result = new HashMap<>();
        for(int i=0;i<cnt;++i) {
            result.put(keys.get(i), matcher.group(i+1));
        }
        return result;
    }

    public String getText() {
        return text;
    }
}
