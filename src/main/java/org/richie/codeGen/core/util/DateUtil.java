/*
 * Copyright 2013 elfkingw
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
// Created on 2013-8-1

package org.richie.codeGen.core.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author elfkingw
 */
public class DateUtil {

    public static final String DATESTYLE = "yyyy-MM-dd HH:mm:ss";

    public static String getNow() {
        return formatDate(new Date(), DATESTYLE);
    }

    public static String formatDate(Date date) {
        return formatDate(date, DATESTYLE);
    }

    public static Date parseDate(String date) throws ParseException {
        if (date == null) return null;
        return parseDate(date,DATESTYLE);
    }

    public static Date parseDate(String date, String sf) throws ParseException {
        if (date == null) return null;
        SimpleDateFormat dateformat = new SimpleDateFormat(sf);
        return dateformat.parse(date);
    }

    /**
     * 计算两个日期差（毫秒）
     * 
     * @param date1 时间1
     * @param date2 时间2
     * @return 相差毫秒数
     */
    public static long diffTwoDate(Date date1, Date date2) {
        long l1 = date1.getTime();
        long l2 = date2.getTime();
        return (l1 - l2);
    }

    /**
     * 对日期进行格式化
     * 
     * @param date 日期
     * @param sf 日期格式
     * @return 字符串
     */
    public static String formatDate(Date date, String sf) {
        if (date == null) return "";
        SimpleDateFormat dateformat = new SimpleDateFormat(sf);
        return dateformat.format(date);
    }

    public static void main(String[] args) {
        System.out.println(getNow());
    }
}
