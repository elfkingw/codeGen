/*
 * Copyright 2013  elfkingw
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
// Created on 2013-6-29
package org.richie.codeGen.core.util;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.richie.codeGen.core.model.Column;

/**
 * 字符串处理工具类 继承 commons-lang-2.4.jar中的StringUtils<br>
 * 该工具已 stringUtils 对应该工具类<br>
 * 模板中调用方法例如： $stringUtils.getClassName(**)
 * 
 * @author elfkingw 2013-6-29
 */
public class StringUtil extends StringUtils {

    /**
     * change the String to javabean class name<br>
     * eg: CAR_ORDER_LIST return carOrderList
     * 
     * @param name
     * @return
     */
    public String getClassName(String name) {
        if (name == null) return null;
        StringBuilder sb = new StringBuilder("");
        if (name.indexOf("_") > 0) {
            String[] strs = name.split("_");
            for (String str : strs) {
                sb.append(getCapitalName(str));
            }
        } else {
            sb.append(getCapitalName(name));
        }
        return sb.toString();
    }

    /**
     * Capitalize first letter
     * 
     * @param name
     * @return
     */
    private String getCapitalName(String name) {
        if (name == null) return null;
        StringBuilder sb = new StringBuilder("");
        if (name.length() > 0) {
            String firstLetter = name.substring(0, 1);
            sb.append(firstLetter.toUpperCase());
            sb.append(name.substring(1).toLowerCase());
        }
        return sb.toString();
    }

    /**
     * 转化成首字母小写驼峰式字符串<br>
     * eg: SYSTEM_CONFIG 返回 systemConfig
     * 
     * @param name
     * @return
     */
    public String getUncapName(String name) {
        if (name == null || name.length() == 0) return null;
        StringBuilder sb = new StringBuilder("");
        if (name.indexOf("_") > 0) {
            String[] strs = name.split("_");
            for (int i = 0; i < strs.length; i++) {
                if (i == 0) {
                    sb.append(strs[i].toLowerCase());
                } else {
                    sb.append(getCapitalName(strs[i]));
                }
            }
        } else {
            String firstLetter = name.substring(0, 1);
            sb.append(firstLetter.toLowerCase());
            sb.append(name.substring(1).toLowerCase());
        }
        return sb.toString();
    }

    /**
     * 如果字段对应的代码类不在java.lang包下则 生成import该资源 eg：返回如下字符串 import java.math.BigDecimal; import java.sql.Date;
     * 
     * @param columns 表字段
     * @return
     */
    public String getImportClass(List<Column> columns) {
        StringBuilder sb = new StringBuilder();
        for (Column column : columns) {
            String codeType = column.getCodeType();
            if (codeType.indexOf(".") > 0 && !codeType.contains("java.lang")) {
                sb.append("import " + codeType + ";\n");
            }
        }
        return sb.toString();
    }

    /**
     * 第一个参数是否包含第二参数 eg ： arg1： elfkingw arg2 ing 返回 true
     * 
     * @param str
     * @param contantStr
     * @return
     */
    public boolean isContants(String str, String contantStr) {
        if (str == null || contantStr == null) {
            return false;
        }
        return str.contains(contantStr);
    }

    /**
     * 拼接两个字符传 eg ：arg1：elf erg2 kingw 返回 elfkingw
     * 
     * @param str
     * @param appendStr
     * @return
     */
    public String append(String str, String appendStr) {
        if (appendStr == null) {
            return str;
        }
        return str + appendStr;
    }

    /**
     * 截取字符窜尾部的指定长度的字符串 eg： arg1：elfkingw1 arg2 1 return elfkingw
     * 
     * @param str 被截取字符村
     * @param len 要截取尾部字符串的长度
     * @return
     */
    public String subStrigTail(String str, int len) {
        if (str == null || str.length() < len) {
            return str;
        }
        return str.substring(0, str.length() - len);
    }

    /**
     * 将字符串转换为小写
     * 
     * @param str
     * @return
     */
    public String toLowerCase(String str) {
        if (str == null) {
            return null;
        }
        return str.toLowerCase();
    }

    /**
     * 将字符串转换为大写
     * 
     * @param str
     * @return
     */
    public String toUpperCase(String str) {
        if (str == null) {
            return null;
        }
        return str.toUpperCase();
    }

    /**
     * 将字符转化为16进制字符
     * 
     * @param s
     * @return
     */
    public String toHex(String s) {
        String str = "";
        for (int i = 0; i < s.length(); i++) {
            int ch = (int) s.charAt(i);
            String s4 = Integer.toHexString(ch);
            str = str + s4;
        }
        return "\\u00" + str;
    }

    public static void main(String[] args) {
        StringUtil util = new StringUtil();
        System.out.println(util.toHex("\n"));
    }
}
