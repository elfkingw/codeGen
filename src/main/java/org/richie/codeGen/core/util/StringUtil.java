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

import org.apache.commons.lang.StringUtils;

/**
 * @author elfkingw 2013-6-29
 */
public class StringUtil extends StringUtils {

    /**
     * change the table to javabean class name
     * 
     * @param name
     * @return
     */
    public String getJavaName(String name) {
        if (name == null) return null;
        StringBuilder sb = new StringBuilder("");
        if (name.indexOf("_") > 0) {
            String[] strs = name.split("_");
            for (String str : strs) {
                sb.append(getCapitalName(str));
            }
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
     * 转化成首字母小写java名
     * @param name
     * @return
     */
    public String getUncapName(String name) {
        if (name == null) return null;
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
        }
        return sb.toString();
    }
}
