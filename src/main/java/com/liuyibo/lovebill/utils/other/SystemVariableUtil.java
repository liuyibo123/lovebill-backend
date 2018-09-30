package com.liuyibo.lovebill.utils.other;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 匹配${AAA_BBB}或者${AAA_BBB:xxx}形式字符串,用到环境变量中找AAA_BBB,将字符串转化为对应的值,默认为xxx
 *
 * @author sun_zhen
 */
public class SystemVariableUtil {

    /**
     * 匹配${AAA_BBB}或者${AAA_BBB:xxx}形式字符串
     */
    private static final Pattern SYSTEM_VARIABLE_PATTERN = Pattern.compile("\\$\\{[A-Z]+(_[A-Z]+)+(:.*)+}");

    public static String convertSystemVariable(String variableStr) {
        //解析含有${}格式的propertyValue值,将其以环境变量替代
        Matcher matcher = SYSTEM_VARIABLE_PATTERN.matcher(variableStr);
        Map<String,String> temp = new HashMap<>();
        while (matcher.find()) {
            int start = matcher.start(), end = matcher.end();
            String matchStr = variableStr.substring(start, end);
            String systemVariableKey = "", defaultValue = "";
            if (matchStr.indexOf(":") > -1) {
                systemVariableKey = matchStr.split(":")[0].replace("${", "");
                defaultValue = matchStr.replace("${"+systemVariableKey+":","").replace("}","");
            }
            String systemVariableValue = System.getenv().get(systemVariableKey);
            if (null != systemVariableValue) {
                temp.put(matchStr,systemVariableValue);
            } else {
                temp.put(matchStr,defaultValue);
            }
        }
        for(String key : temp.keySet()){
            variableStr = variableStr.replace(key,temp.get(key));
        }
        return variableStr;
    }

    //测试方法
    public static void main(String[] args){
        convertSystemVariable("jdbc:mysql://${DATASOURCE_MASTER_URL:10.10.250.149:3306/lambo}?useUnicode=true&characterEncoding=utf-8&autoReconnect=true");
        System.out.println(System.getenv());
        System.out.println(System.getProperties());
    }
}
