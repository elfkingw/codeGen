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

import java.io.*;
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

    private static Log log = LogFacotry.getLogger(XmlParse.class);
    private Class<T> entity;

    public XmlParse(Class<T> entity) {
        this.entity = entity;
    }

    @SuppressWarnings("unchecked")
    public List<T> parseXmlFileToVo(String filePath) throws Exception {
//        File f = new File(filePath);
//        if (!f.exists()) {
//            return new ArrayList<T>();
//        }
        File file = new File(FileUtils.getConfigPath());

        //window和mac在jar包里的文件分隔符都是"/"
        InputStream inputStream = XmlParse.class.getResourceAsStream("/" + JarFileUtils.JAR_CONFIG_PATH + "/" + filePath);
        if (inputStream == null) {
            return new ArrayList<T>();
        }
        SAXReader sr = new SAXReader();
        Document doc = null;
        try {
            doc = sr.read(inputStream);
        } catch (DocumentException e) {
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
                            if (("Boolean").equals(claz.getSimpleName())) {
                                isBoolean = true;
                            }
                        }
                        if (isBoolean) {
                            if ("true".equals(e.getText())) {
                                method.invoke(t, Boolean.TRUE);
                            } else {
                                method.invoke(t, Boolean.FALSE);
                            }
                        } else {
                            Object[] params = new Object[]{e.getText()};
                            method.invoke(t, params);
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

    public String getVoToXmlFileContent(List<T> list, String fileName) throws Exception {
        Document document = getDocument(list);
        return getContentFromDocument(document, fileName);
    }

    @SuppressWarnings("unchecked")
    public void genVoToXmlFile(List<T> list, String outfile) throws Exception {
        Document document = getDocument(list);
        writeDocument(document, outfile);
    }

    private Document getDocument(List<T> list) throws Exception {
        if (list == null) {
            return null;
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
        return document;
    }

    private void setElements(T o, Class<T> clazz, Element parent) throws Exception {
        Method[] methods = clazz.getMethods();
        for (Method method : methods) {
            if (method.getName().startsWith("get") && !("getClass").equals(method.getName())) {
                Element fieldElement = parent.addElement(getFieldName(method.getName()));
                Object result = method.invoke(o, new Object[]{});
                fieldElement.addText(result != null ? result.toString() : "");
            }
        }
    }

    private String getFieldName(String getMethodName) {
        String method = getMethodName.substring(3);
        return method.substring(0, 1).toLowerCase() + method.substring(1);
    }

    private String getSetMonthName(String getFieldName) {
        return "set" + getFieldName.substring(0, 1).toUpperCase() + getFieldName.substring(1);
    }

    /**
     * 写入xml文件地址
     * 修改为写入jar文件中
     *
     * @param document 所属要写入的内容
     * @param outFile  文件存放的地址
     * @throws IOException 异常
     */
    private static void writeDocument(Document document, String outFile) throws IOException {
        XMLWriter xmlWriter = null;
        try {
            // 读取文件
            OutputStreamWriter out = new OutputStreamWriter(new FileOutputStream(outFile), "utf-8");
            // 设置文件编码
            OutputFormat xmlFormat = new OutputFormat();
            xmlFormat.setEncoding("UTF-8");
            // 创建写文件方法
            xmlWriter = new XMLWriter(out, xmlFormat);
            // 写入文件
            xmlWriter.write(document);
        } catch (IOException e) {
            log.error("写Document报错",e);
            throw e;
        } finally {
            // 关闭
            if (xmlWriter != null) {
                xmlWriter.close();
            }
        }
    }

    /**
     * 把都document转化成文本，通过临时文件的方式
     *
     * @param document xmlDocument
     * @param fileName 临时文件名
     * @return xml文本内容
     * @throws Exception 异常
     */
    private String getContentFromDocument(Document document, String fileName) throws Exception {
        XMLWriter xmlWriter = null;
        String content;
        try {
            String tempFileName = JarFileUtils.TEMP_FILE_PATH + File.separator + fileName;
            File tempFile = new File(tempFileName);
            // 读取文件
            OutputStreamWriter out = new OutputStreamWriter(new FileOutputStream(tempFile), "utf-8");
            // 设置文件编码
            OutputFormat xmlFormat = new OutputFormat();
            xmlFormat.setEncoding("UTF-8");
            // 创建写文件方法
            xmlWriter = new XMLWriter(out, xmlFormat);
            // 写入文件
            xmlWriter.write(document);
            xmlWriter.flush();
            content = readFile(tempFile);
            //删除临时文件
            if( tempFile.delete()) {
                log.info("根据Document获取内容，临时文件名：【" + fileName + "】,删除临时文件");
            }
        } catch (Exception e) {
            throw new RuntimeException("写入配置xml文件出错", e);
        } finally {
            // 关闭
            if (xmlWriter != null) {
                xmlWriter.close();
            }
        }
        return content;
    }

    /**
     * 读取文件内容
     *
     * @param file 文件
     * @return 文件内容
     * @throws IOException 异常
     */
    private String readFile(File file) throws IOException {
        StringBuilder sb = new StringBuilder();
        BufferedReader reader = null;
        try {
            reader = new BufferedReader(new InputStreamReader(new FileInputStream(file), "utf-8"));
            String lineStr = null;
            while ((lineStr = reader.readLine()) != null) {
                sb.append(lineStr).append("\n");
            }
        } finally {
            if (null != reader) {
                reader.close();
            }
        }
        return sb.toString();
    }

}
