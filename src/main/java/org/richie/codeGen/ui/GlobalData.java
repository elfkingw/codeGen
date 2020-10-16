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

package org.richie.codeGen.ui;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.richie.codeGen.core.log.Log;
import org.richie.codeGen.core.log.LogFacotry;
import org.richie.codeGen.core.model.DataType;
import org.richie.codeGen.core.model.UIComponent;
import org.richie.codeGen.core.util.StringUtil;
import org.richie.codeGen.ui.model.*;
import org.richie.codeGen.ui.util.FileUtils;
import org.richie.codeGen.ui.util.JarFileUtils;
import org.richie.codeGen.ui.util.XmlParse;

/**
 * @author elfkingw
 */
public class GlobalData {

    private static Log log = LogFacotry.getLogger(GlobalData.class);
    /**
     * 模板
     */
    private static List<CodeTemplateVo> templateList;
    /**
     * 代码生成界面的模板
     */
    private static List<CodeTemplateVo> lastTemplateList;
    /**
     * 常量
     */
    private static List<ConstantConfigVo> constList;
    /**
     * 输出路径根目录
     */
    private static OutFileRootPathVo outFileRootPathVo;
    /**
     * 用户最后一次操
     */
    private static LastOperateVo lastOperateVo;
    /**
     * 数据类型
     */
    private static List<DataType> dataTypeList;
    /**
     * 界面控件
     */
    private static List<UIComponent> uiTypeList;

    /**
     * 要删除的模板文件面
     */
    private static List<String> deleteTemplateNames;

    public static String[] costantType = new String[]{"常量", "类"};
    public static String[] uiType = new String[]{"TextField", "DateField", "ComboBox",
            "ComboBoxTree", "ComboBoxGrid", "Radio", "Textarea", "Tree", "CheckBox", "Other"};


    public static List<CodeTemplateVo> getTemplateList() throws Exception {
        if (templateList == null || templateList.size() == 0) {
            XmlParse<CodeTemplateVo> xmlParse = new XmlParse<CodeTemplateVo>(CodeTemplateVo.class);
            templateList = xmlParse.parseXmlFileToVo(FileUtils.getConfigTemplatePath());
            addTemplateFile(templateList);
        }
        return templateList;
    }

    /**
     * 把在jar包下的模板文件不存在的加入到模板中
     *
     * @param templateList 模板list
     * @throws Exception 异常
     */
    private static void addTemplateFile(List<CodeTemplateVo> templateList) throws Exception {
        List<String> templateFileNames = JarFileUtils.getTemplateFiles();
        if (templateFileNames == null) {
            return;
        }
        Map<String, String> templateMap = new HashMap<String, String>(10);
        for (CodeTemplateVo templateVo : templateList) {
            templateMap.put(templateVo.getFileName(), null);
        }

        List<CodeTemplateVo> notExistList = new ArrayList<CodeTemplateVo>();
        for (String templateFileName : templateFileNames) {
            if (!templateMap.containsKey(templateFileName)) {
                CodeTemplateVo newTemplateVO = new CodeTemplateVo();
                newTemplateVO.setFileName(templateFileName);
                newTemplateVO.setTemplateName(templateFileName);
                notExistList.add(newTemplateVO);
            }
        }
        templateList.addAll(notExistList);
    }

    public static void setTemplateList(List<CodeTemplateVo> templateList) {
        GlobalData.templateList = templateList;
    }

    public static CodeTemplateVo getTemplateByName(String templateName) throws Exception {
        CodeTemplateVo vo = null;
        templateList = getTemplateList();
        if (templateList == null) {
            return null;
        }
        for (CodeTemplateVo codeTemplateVo : templateList) {
            if (codeTemplateVo.getTemplateName() != null && codeTemplateVo.getTemplateName().equals(templateName)) {
                vo = codeTemplateVo;
            }
        }
        return vo;
    }

    public static String[] getTemplateNames() throws Exception {
        String[] names = new String[]{};
        templateList = getTemplateList();
        if (templateList != null && templateList.size() > 0) {
            names = new String[templateList.size()];
            for (int i = 0; i < templateList.size(); i++) {
                names[i] = templateList.get(i).getTemplateName();
            }
        }
        return names;
    }


    public static List<CodeTemplateVo> getLastTemplateList() throws Exception {
        if (lastTemplateList == null || lastTemplateList.size() == 0) {
            XmlParse<CodeTemplateVo> xmlParse = new XmlParse<CodeTemplateVo>(CodeTemplateVo.class);
            lastTemplateList = xmlParse.parseXmlFileToVo(FileUtils.getLastTemplatePath());
        }
        return lastTemplateList;
    }

    public static void setLastTemplateList(List<CodeTemplateVo> lastTemplateList) {
        GlobalData.lastTemplateList = lastTemplateList;
    }

    public static List<ConstantConfigVo> getConstantList() throws Exception {
        if (constList == null) {
            XmlParse<ConstantConfigVo> xmlParse = new XmlParse<ConstantConfigVo>(ConstantConfigVo.class);
            constList = xmlParse.parseXmlFileToVo(FileUtils.getConstantConfigPath());
        }
        return constList;
    }

    public static void setConstantList(List<ConstantConfigVo> constantList) {
        constList = constantList;
    }

    public static OutFileRootPathVo getOutFileRootPathVo() throws Exception {
        if (outFileRootPathVo == null) {
            XmlParse<OutFileRootPathVo> xmlParse = new XmlParse<OutFileRootPathVo>(OutFileRootPathVo.class);
            List<OutFileRootPathVo> list = xmlParse.parseXmlFileToVo(FileUtils.getOutFileRootPath());
            if (list != null && list.size() != 0) {
                outFileRootPathVo = list.get(0);
            }
        }
        if (outFileRootPathVo == null) {
            outFileRootPathVo = new OutFileRootPathVo();
        }
        outFileRootPathVo.setName1("默认");
        outFileRootPathVo.setPath1(FileUtils.getDefaultOutPath());
        return outFileRootPathVo;
    }

    public static void setOutFileRootPath(OutFileRootPathVo outFileRootPathVo) {
        GlobalData.outFileRootPathVo = outFileRootPathVo;
    }

    public static String[] getOutFileRootNames() throws Exception {
        String[] names = new String[]{};
        OutFileRootPathVo vo = getOutFileRootPathVo();
        List<String> list = new ArrayList<String>();
        if (vo != null) {
            if (!StringUtil.isEmpty(vo.getName1())) {
                list.add(vo.getName1());
            }
            if (!StringUtil.isEmpty(vo.getName2())) {
                list.add(vo.getName2());
            }
            if (!StringUtil.isEmpty(vo.getName3())) {
                list.add(vo.getName3());
            }
            if (!StringUtil.isEmpty(vo.getName4())) {
                list.add(vo.getName4());
            }
        }
        if (list.size() > 0) {
            names = new String[list.size()];
            list.toArray(names);
        }
        return names;
    }

    public static String getOutFilePathByName(String rootName) throws Exception {
        if (StringUtil.isEmpty(rootName)) {
            return null;
        }
        String path = null;
        OutFileRootPathVo vo = getOutFileRootPathVo();
        if (vo != null) {
            if (rootName.equals(vo.getName1())) {
                path = vo.getPath1();
            } else if (rootName.equals(vo.getName2())) {
                path = vo.getPath2();
            } else if (rootName.equals(vo.getName3())) {
                path = vo.getPath3();
            } else if (rootName.equals(vo.getName4())) {
                path = vo.getPath4();
            }
        }
        log.info("生成代码路径为：" + path);
        return path;
    }

    public static LastOperateVo getLastOperateVo() throws Exception {
        if (lastOperateVo == null) {
            XmlParse<LastOperateVo> xmlParse = new XmlParse<LastOperateVo>(LastOperateVo.class);
            List<LastOperateVo> list = xmlParse.parseXmlFileToVo(FileUtils.getLastOperatePath());
            if (list != null && list.size() > 0) {
                lastOperateVo = list.get(0);
            } else {
                lastOperateVo = new LastOperateVo();
            }
        }
        return lastOperateVo;
    }

    public static void setLastOperateVo(LastOperateVo templastOperateVo) throws Exception {
        lastOperateVo = templastOperateVo;
    }

    public static List<DataType> getDataType() throws Exception {
        if (dataTypeList == null) {
            XmlParse<DataType> xmlParse = new XmlParse<DataType>(DataType.class);
            dataTypeList = xmlParse.parseXmlFileToVo(FileUtils.getDataTypePath());
        }
        return dataTypeList;
    }

    public static void setDataType(List<DataType> tempDataTypeList) {
        dataTypeList = tempDataTypeList;
    }

    public static List<UIComponent> getUIType() throws Exception {
        if (uiTypeList == null) {
            XmlParse<UIComponent> xmlParse = new XmlParse<UIComponent>(UIComponent.class);
            uiTypeList = xmlParse.parseXmlFileToVo(FileUtils.getUITypePath());
        }
        return uiTypeList;
    }

    public static String[] getUITypeStrs() throws Exception {
        String[] codes = new String[]{"TextField", "textarea", "checkbox", "radio"};
        uiTypeList = getUIType();
        if (uiTypeList != null && uiTypeList.size() > 0) {
            codes = new String[uiTypeList.size()];
            for (int i = 0; i < uiTypeList.size(); i++) {
                codes[i] = uiTypeList.get(i).getCode();
            }
        }
        return codes;
    }

    public static Map<String, String> getDataTypeMap() throws Exception {
        Map<String, String> dataTypeMap = new HashMap<String, String>();
        List<DataType> dataTypeList = getDataType();
        for (DataType dataType : dataTypeList) {
            String dataTypeStr = dataType.getDataType() != null ? dataType.getDataType().toLowerCase() : null;
            dataTypeMap.put(dataTypeStr, dataType.getJavaType());
        }
        return dataTypeMap;
    }

    public static void setUiTypeList(List<UIComponent> uiComponentList) {
        uiTypeList = uiComponentList;
    }

    public static Map<String, Object> getConstentMap() throws Exception {
        Map<String, Object> map = new HashMap<String, Object>();
        XmlParse<ConstantConfigVo> xmlParse = new XmlParse<ConstantConfigVo>(ConstantConfigVo.class);
        List<ConstantConfigVo> list = xmlParse.parseXmlFileToVo(FileUtils.getConstantConfigPath());
        if (list != null) {
            for (ConstantConfigVo constantConfigVo : list) {
                if (isClassType(constantConfigVo.getType())) {
                    String className = constantConfigVo.getValue();
                    try {
                        Object obj = Class.forName(className).newInstance();
                        map.put(constantConfigVo.getKey(), obj);
                        log.info("成功载入自定义类：" + className);
                    } catch (Exception e) {
                        log.error("载入自定义类失败：" + e.getMessage());
                    }
                } else {
                    map.put(constantConfigVo.getKey(), constantConfigVo.getValue());
                    log.info("成功载入自定义常量：" + constantConfigVo.getKey() + ":" + constantConfigVo.getValue());
                }
            }
        }
        return map;
    }

    private static boolean isClassType(String type) {
        if (costantType[1].equals(type)) {
            return true;
        }
        return false;
    }


    /**
     * 删除模板
     *
     * @param fileName 模板文件名
     */
    public static void deleteTemplateFile(String fileName) throws Exception {
        List<CodeTemplateVo> deleteList = new ArrayList<CodeTemplateVo>();
        for (CodeTemplateVo codeTemplateVo : getTemplateList()) {
            if (codeTemplateVo.getFileName().equals(fileName)) {
                deleteList.add(codeTemplateVo);
                if (deleteTemplateNames == null) {
                    deleteTemplateNames = new ArrayList<String>();
                }
                deleteTemplateNames.add(codeTemplateVo.getFileName());
            }
        }
        templateList.removeAll(deleteList);
    }


    /**
     * 保存所有的参数配置：
     * 1.保存修改的模板信息
     * 2.保存最后的操作信息
     * 3.保存页面控件设置信息
     * 4.保存数据类型信息
     * 5.保存生成代码最后的模板设置信息
     * 6.删除模板信息
     * 7.保存界面设置的常量设置信息
     * 8.保存界面设置的文件路劲设置信息
     */
    public static void saveLastConfig() throws Exception {
        List<JarFileVo> addFileList = new ArrayList<JarFileVo>();
        //界面模板信息
        addTemplateJarFile(templateList, FileUtils.getConfigTemplatePath(), addFileList);
        //最后操作信息
        addLastOperate(addFileList);
        //界面控件信息
        addUIComponent(addFileList);
        //数据类型信息
        addDataTypeJarFile(addFileList);
        //代码生成界面最后模板信息
        log.info("保存生成界面模板信息");
        addTemplateJarFile(lastTemplateList, FileUtils.getLastTemplatePath(), addFileList);
        //临时模板写入到jar包
        log.info("临时目录下的模板同步到jar中");
        addTempTemplate(addFileList);
        //保存界面设置的常量设置信息
        addConstantConfigJarFile(addFileList);
        //保存界面输出文件跟目录信息
        addOutFileRootPathVo(addFileList);
        //界面上操作的删除模板信息，最后删除掉
        logLastSaveInfo(addFileList);
        JarFileUtils.writeJarTemplateFile(addFileList, deleteTemplateNames);
        log.info("成功保存最后设置");
    }

    private static void logLastSaveInfo(List<JarFileVo> addFileList) {
        StringBuilder sb = new StringBuilder();
        sb.append("写入jar信息的文件如下：\n");
        for (JarFileVo jarFilVo : addFileList) {
            sb.append(jarFilVo.toString()).append("\n");
        }
        log.info(sb.toString());
    }

    private static void addTemplateJarFile(List<CodeTemplateVo> list, String path, List<JarFileVo> addFileList) throws Exception {
        if (list == null) {
            return;
        }
        JarFileVo codeTemplateJarFilVo = new JarFileVo();
        //模板界面信息写入到jar包
        XmlParse<CodeTemplateVo> xmlParse = new XmlParse<CodeTemplateVo>(CodeTemplateVo.class);
        String templateContent = xmlParse.getVoToXmlFileContent(list, path);
        codeTemplateJarFilVo.setFileName(path);
        codeTemplateJarFilVo.setContent(templateContent);
        codeTemplateJarFilVo.setJarFilePath(JarFileUtils.JAR_CONFIG_PATH);
        addFileList.add(codeTemplateJarFilVo);
    }

    private static void addLastOperate(List<JarFileVo> addFileList) throws Exception {
        if (lastOperateVo == null) {
            return;
        }
        XmlParse<LastOperateVo> xmlParse = new XmlParse<LastOperateVo>(LastOperateVo.class);
        List<LastOperateVo> lastOperateVos = new ArrayList<LastOperateVo>();
        lastOperateVos.add(lastOperateVo);
        String lastOperateContent = xmlParse.getVoToXmlFileContent(lastOperateVos, FileUtils.getLastOperatePath());
        JarFileVo lastOperateJarFileVo = new JarFileVo();
        lastOperateJarFileVo.setFileName(FileUtils.getLastOperatePath());
        lastOperateJarFileVo.setContent(lastOperateContent);
        lastOperateJarFileVo.setJarFilePath(JarFileUtils.JAR_CONFIG_PATH);
        addFileList.add(lastOperateJarFileVo);
    }

    private static void addUIComponent(List<JarFileVo> addFileList) throws Exception {
        if (uiTypeList == null) {
            return;
        }
        XmlParse<UIComponent> xmlParse = new XmlParse<UIComponent>(UIComponent.class);
        String uiTypeContent = xmlParse.getVoToXmlFileContent(uiTypeList, FileUtils.getUITypePath());
        JarFileVo uiTypeFileVo = new JarFileVo();
        uiTypeFileVo.setFileName(FileUtils.getUITypePath());
        uiTypeFileVo.setContent(uiTypeContent);
        uiTypeFileVo.setJarFilePath(JarFileUtils.JAR_CONFIG_PATH);
        addFileList.add(uiTypeFileVo);
    }

    private static void addDataTypeJarFile(List<JarFileVo> addFileList) throws Exception {
        if (dataTypeList == null) {
            return;
        }
        XmlParse<DataType> xmlParse = new XmlParse<DataType>(DataType.class);
        String dataTypeContent = xmlParse.getVoToXmlFileContent(dataTypeList, FileUtils.getDataTypePath());
        JarFileVo dataTypeFileVo = new JarFileVo();
        dataTypeFileVo.setFileName(FileUtils.getDataTypePath());
        dataTypeFileVo.setContent(dataTypeContent);
        dataTypeFileVo.setJarFilePath(JarFileUtils.JAR_CONFIG_PATH);
        addFileList.add(dataTypeFileVo);
    }

    private static void addConstantConfigJarFile(List<JarFileVo> addFileList) throws Exception {
        if (constList == null) {
            return;
        }
        XmlParse<ConstantConfigVo> xmlParse = new XmlParse<ConstantConfigVo>(ConstantConfigVo.class);
        String constantCfgContent = xmlParse.getVoToXmlFileContent(constList, FileUtils.getConstantConfigPath());
        JarFileVo constantCfgJarFileVo = new JarFileVo();
        constantCfgJarFileVo.setFileName(FileUtils.getConstantConfigPath());
        constantCfgJarFileVo.setContent(constantCfgContent);
        constantCfgJarFileVo.setJarFilePath(JarFileUtils.JAR_CONFIG_PATH);
        addFileList.add(constantCfgJarFileVo);
    }

    private static void addOutFileRootPathVo(List<JarFileVo> addFileList) throws Exception {
        if (outFileRootPathVo == null) {
            return;
        }
        XmlParse<OutFileRootPathVo> xmlParse = new XmlParse<OutFileRootPathVo>(OutFileRootPathVo.class);
        List<OutFileRootPathVo> outfileRootPaths = new ArrayList<OutFileRootPathVo>();
        outfileRootPaths.add(outFileRootPathVo);
        String outFileRootPathContent = xmlParse.getVoToXmlFileContent(outfileRootPaths, FileUtils.getOutFileRootPath());
        JarFileVo lastOperateJarFileVo = new JarFileVo();
        lastOperateJarFileVo.setFileName(FileUtils.getOutFileRootPath());
        lastOperateJarFileVo.setContent(outFileRootPathContent);
        lastOperateJarFileVo.setJarFilePath(JarFileUtils.JAR_CONFIG_PATH);
        addFileList.add(lastOperateJarFileVo);
    }


    /**
     * 把临时目录下的模板文件写入到jar包里去，同时删除临时文件夹下的模板
     */
    public static void addTempTemplate(List<JarFileVo> addFileList) throws Exception {
        File file = new File(JarFileUtils.TEMP_TEMPLATE_FILE_PATH);
        if (!file.exists()) {
            return;
        }
        String[] templateFiles = file.list();
        if (templateFiles == null) {
            return;
        }
        for (String fileName : templateFiles) {
            JarFileVo templateVo = new JarFileVo();
            File originalFile = new File(JarFileUtils.TEMP_TEMPLATE_FILE_PATH + File.separator + fileName);
            String content = FileUtils.readFile(originalFile);
            templateVo.setJarFilePath(JarFileUtils.JAR_TEMPLATE_PATH);
            templateVo.setContent(content);
            templateVo.setFileName(fileName);
            addFileList.add(templateVo);
            //删除临时文件下模板
            originalFile.delete();
        }
        log.info("成功同步临时文件中模板到Jar中");
    }

}
