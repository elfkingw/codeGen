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
// Created on 2013-7-6
package org.richie.codeGen.core.velocity;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.StringWriter;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Properties;

import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.Velocity;
import org.apache.velocity.tools.generic.AlternatorTool;
import org.apache.velocity.tools.generic.EscapeTool;
import org.apache.velocity.tools.generic.NumberTool;
import org.apache.velocity.tools.generic.RenderTool;
import org.apache.velocity.tools.generic.SortTool;
import org.apache.velocity.tools.generic.ValueParser;
import org.richie.codeGen.core.exception.CGException;
import org.richie.codeGen.core.log.Log;
import org.richie.codeGen.core.log.LogFacotry;
import org.richie.codeGen.core.model.Table;
import org.richie.codeGen.core.util.DateUtil;
import org.richie.codeGen.core.util.StringUtil;
import org.richie.codeGen.ui.util.FileUtils;

public class CodeGen {

    private static Log                 log = LogFacotry.getLogger(CodeGen.class);
    private static Map<String, Object> map = new HashMap<String, Object>();

    static {
        initVelocity();
        putDefaultToolsToVelocityContext();
        initCustomerVelocityContext();
    }

    /**
     * generate code File from template file with velocity tool
     * 
     * @param templateName
     * @param templatesFolder
     * @param outFileFolder
     * @param outFileName
     * @throws CGException
     * @throws Exception
     */
    public static void genCode(String templateName, String templatesFolder, String outFileFolder, String outFileName)
                                                                                                                     throws CGException,
                                                                                                                     Exception {
        String fileContent = genCode(templateName, templatesFolder);
        File folder = new File(outFileFolder);
        if (!folder.exists()) {
            folder.mkdirs();
        }
        FileUtils.witerFile(outFileFolder, outFileName, fileContent);
    }

    public static String genCode(String templateName, String templatesFolder) throws CGException, Exception {
        File f = new File(templatesFolder, templateName);
        if (!f.exists()) throw new CGException("Template " + templateName + " not found!");
        InputStreamReader reader = null;
        StringWriter writer = null;
        try {
            VelocityContext context = new VelocityContext();
            insertInVelocityContext(map, context);
            context.put("map", map);
            reader =new InputStreamReader(new FileInputStream(f),FileUtils.ENCODING);
            writer = new StringWriter();
            Velocity.evaluate(context, writer, "", reader);
        } catch (Exception e) {
            String msg = "Error at Velocity.evaluate (please, make sure the Velocity template is Ok. ex.getMessage() >> ["
                         + e.getMessage() + "]";
            log.error(msg, e);
            throw new Exception(msg, e);
        } finally {
            try {
                reader.close();
                writer.flush();
                writer.close();
            } catch (IOException e) {
                log.error("", e);
            }
        }
        return writer != null ? writer.toString() : null;
    }

    private static void insertInVelocityContext(Map<String, Object> variablesMap, VelocityContext context) {
        if (variablesMap != null) {
            Iterator<String> iter = variablesMap.keySet().iterator();
            while (iter.hasNext()) {
                String key = (String) iter.next();
                context.put(key, variablesMap.get(key));
            }
        }
    }

    /**
     * 设置用户自定义velocity Context
     * 
     * @param map
     */
    public static void initCustomerVelocityContext() {
        Map<String, Object> customerMap = CustomerVelocityContext.getCustomerVelociTyContext();
        if (customerMap != null) map.putAll(customerMap);
    }

    public static void initCustomerVelocityContext(Map<String, Object> customerMap) {
        if (customerMap != null) map.putAll(customerMap);
    }

    /**
     * 将数据库表模型加入到velocityContext
     * 
     * @param table
     */
    public static void initTableVelocityContext(Table table) {
        map.put("table", table);
    }

    /**
     * - $stringUtils - $dateUtils 
     */
    private static void putDefaultToolsToVelocityContext() {
        map.put("stringUtils", new StringUtil());
        map.put("dateUtils", new DateUtil());
        
        map.put("numberTool", new NumberTool());
        map.put("renderTool", new RenderTool());
        map.put("escapeTool", new EscapeTool());
        map.put("alternatorTool", new AlternatorTool());
        map.put("valueParserTool", new ValueParser());
        map.put("sortTool", new SortTool());

        // Velocity Tags.
        map.put("if", "#if");
        map.put("for", "#foreach");
        map.put("foreach", "#foreach");
        map.put("end", "#end");
        map.put("velocity", "$");
        map.put("open", "{");
        map.put("close", "}");
    }

    public static void putToolsToVelocityContext(String toolKey, Object o) {
        map.put(toolKey, o);
    }

    public static void initVelocity() {
        // 配置
        Properties p = new Properties();
        // 设置输入输出编码类型。
        p.setProperty(Velocity.INPUT_ENCODING, "UTF-8");
        p.setProperty(Velocity.OUTPUT_ENCODING, "UTF-8");
        // 初始化(单实例)
        Velocity.init(p);
    }
}
