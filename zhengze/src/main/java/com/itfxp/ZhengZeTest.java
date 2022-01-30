package com.itfxp;

import org.junit.Test;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ZhengZeTest {
    public static final String EXPRESSION="";

    @Test
    public void test(){
        String regExp="^\\<img\\s*";
        String content="<img src=\"www.baidu.com\" data=\"wjkdbhakb,mzsnmk89qw3jnnwedklansdfl," +
                "amndflad;lma.;m,ds/a,/ds,a?Dljefl\" www />";
        Pattern pattern = Pattern.compile(regExp);
        Matcher matcher = pattern.matcher(content);
        while (matcher.find()){
            System.out.println(matcher.group(0));
        }

    }
}
