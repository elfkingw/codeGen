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
// Created on 2013-7-13

package org.richie.codeGen.ui.util;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.lang.reflect.Method;
import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.List;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.SAXReader;
import org.dom4j.io.XMLWriter;
import org.richie.codeGen.core.log.Log;
import org.richie.codeGen.core.log.LogFacotry;

/**
 * @author elfkingw
 */
public class XmlParse<T> {

    private Log log = LogFacotry.getLogger(XmlParse.class);
    private Class<T> entity;

    public XmlParse(Class<T> entity){
        this.entity = entity;
    }

    @SuppressWarnings("unchecked")
    public List<T> parseXmlFileToVo(String filePath) throws Exception {
        File f = new File(filePath);
        if (!f.exists()) {
            return new ArrayList<T>();
        }
        SAXReader sr = new SAXReader();
        Document doc = null;
        try {
            doc = sr.read(f);
        } catch (DocumentException e) {
            log.error("read xml", e);
        } catch (MalformedURLException e) {
            log.error("read xml", e);
        }
        List<T> resultList = new ArrayList<T>();
        List<Element> list = doc.getRootElement().elements();
        for (Element element : list) {
            T t = entity.newInstance();
            Class<T> clazz = (Class<T>) t.getClass();
            List<Element> childList = element.elements();
            for (Element e : childList) {
                String methodName = getSetMonthName(e.getName());
                Method[] methods = clazz.getMethods();
                for (Method method : methods) {
                    if (method.getName().equals(methodName)) {
                        Class<?>[] clas = method.getParameterTypes();
                        boolean isBoolean = false;
                        for (Class<?> claz : clas) {
                            if (claz.getSimpleName().equals("Boolean")) isBoolean = true;
                        }
                        if (isBoolean) {
                            if ("true".equals(e.getText())) {
                                method.invoke(t, Boolean.TRUE);
                            } else {
                                method.invoke(t, Boolean.FALSE);
                            }
                        } else {
                            method.invoke(t, new Object[]{e.getText()});
                        }
                    }
                }
            }
            resultList.add(t);
        }
        return resultList;
    }

    public void genVoToXmlFile(T t, String outfile) throws Exception {
        List<T> list = new ArrayList<T>();
        list.add(t);
        genVoToXmlFile(list, outfile);
    }

    @SuppressWarnings("unchecked")
    public void genVoToXmlFile(List<T> list, String outfile) throws Exception {
        if (list == null ) {
        }
        Class<T> clazz = null;
        Document document = DocumentHelper.createDocument();
        String className = null;
       
        clazz = (Class<T>) entity.newInstance().getClass();
        className = clazz.getSimpleName();
        Element root = document.addElement(className + "s");
        for (T t : list) {
            Element category = root.addElement(className);
            setElements(t, clazz, category);
        }
        writeDocument(document, outfile);
    }

    private void setElements(T o, Class<T> clazz, Element parent) throws Exception {
        Method[] methods = clazz.getMethods();
        for (Method method : methods) {
            if (method.getName().startsWith("get") && !method.getName().equals("getClass")) {
                Element fieldElement = parent.addElement(getFieldName(method.getName()));
                Object result = method.invoke(o, new Object[] {});
                fieldElement.addText(result != null ? result.toString() : "");
            }
        }
    }

    private String getFieldName(String getMonthedName) {
        String method = getMonthedName.substring(3);
        return method.substring(0, 1).toLowerCase() + method.substring(1);
    }

    private String getSetMonthName(String getFieldName) {
        return "set" + getFieldName.substring(0, 1).toUpperCase() + getFieldName.substring(1);
    }

    /**
     * 写入xml文件地址
     * 
     * @param document 所属要写入的内容
     * @param outFile 文件存放的地址
     * @throws IOException
     */
    public static void writeDocument(Document document, String outFile) throws IOException {
        XMLWriter xmlWriter = null;
        try {
            // 读取文件
            OutputStreamWriter out = new OutputStreamWriter(new FileOutputStream(outFile),"utf-8");  
            // 设置文件编码
            OutputFormat xmlFormat = new OutputFormat();
            xmlFormat.setEncoding("UTF-8");
            // 创建写文件方法
            xmlWriter = new XMLWriter(out, xmlFormat);
            // 写入文件
            xmlWriter.write(document);
        } catch (IOException e) {
            throw e;
        } finally {
            // 关闭
            if(xmlWriter != null)
                xmlWriter.close();
        }
    }

}
