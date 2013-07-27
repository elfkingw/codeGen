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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.richie.codeGen.core.log.Log;
import org.richie.codeGen.core.log.LogFacotry;
import org.richie.codeGen.core.model.DataType;
import org.richie.codeGen.core.util.StringUtil;
import org.richie.codeGen.ui.model.CodeTemplateVo;
import org.richie.codeGen.ui.model.ConstantConfigVo;
import org.richie.codeGen.ui.model.LastOperateVo;
import org.richie.codeGen.ui.model.OutFileRootPathVo;
import org.richie.codeGen.ui.util.FileUtils;
import org.richie.codeGen.ui.util.XmlParse;

/**
 * @author elfkingw
 */
public class GlobalData {

    private static Log                   log         = LogFacotry.getLogger(GlobalData.class);
    public static List<CodeTemplateVo>   templateList;                                         // 模板
    public static List<ConstantConfigVo> constList;                                            // 常量
    public static OutFileRootPathVo      outFileRootPathVo;                                    // 输出路径根目录
    public static LastOperateVo          lastOperateVo;                                        // 用户最后一次操
    public static List<DataType>         dataTypeList;                                         // 数据类型

    public static String[]               costantType = new String[] { "常量", "类" };
    public static String[]               uiType      = new String[] { "TextField", "DateField", "ComboBox",
            "ComboBoxTree", "ComboBoxGrid", "Radio", "Textarea", "Tree", "CheckBox", "Other" };

    public static List<CodeTemplateVo> getTemplateList() throws Exception {
        if (templateList == null) {
            XmlParse<CodeTemplateVo> xmlParse = new XmlParse<CodeTemplateVo>(CodeTemplateVo.class);
            templateList = xmlParse.parseXmlFileToVo(FileUtils.getConfigTemplatePath());
        }
        return templateList;
    }

    public static void setTemplateList(List<CodeTemplateVo> templateList) {
        GlobalData.templateList = templateList;
    }

    public static CodeTemplateVo getTemplateByName(String templateName) throws Exception {
        CodeTemplateVo vo = null;
        templateList = getTemplateList();
        if (templateList == null) return null;
        for (CodeTemplateVo codeTemplateVo : templateList) {
            if (codeTemplateVo.getTemplateName() != null && codeTemplateVo.getTemplateName().equals(templateName)) {
                vo = codeTemplateVo;
            }
        }
        return vo;
    }

    public static String[] getTemplateNames() throws Exception {
        String[] names = new String[] {};
        templateList = getTemplateList();
        if (templateList != null && templateList.size()>0) {
            names = new String[templateList.size()];
            for (int i = 0; i < templateList.size(); i++) {
                names[i] = templateList.get(i).getTemplateName();
            }
        }
        return names;
    }

    public static List<ConstantConfigVo> getConstantList() throws Exception {
        if (constList == null) {
            XmlParse<ConstantConfigVo> xmlParse = new XmlParse<ConstantConfigVo>(ConstantConfigVo.class);
            constList = xmlParse.parseXmlFileToVo(FileUtils.getConstantConfigPath());
        }
        return constList;
    }

    public static OutFileRootPathVo getOutFileRootPathVo() throws Exception {
        if (outFileRootPathVo == null) {
            XmlParse<OutFileRootPathVo> xmlParse = new XmlParse<OutFileRootPathVo>(OutFileRootPathVo.class);
            List<OutFileRootPathVo> list = xmlParse.parseXmlFileToVo(FileUtils.getOutFileRootPath());
            if (list != null && list.size() != 0) {
                outFileRootPathVo = list.get(0);
            }
        }
        if(outFileRootPathVo == null){
            outFileRootPathVo = new OutFileRootPathVo();
        }
        return outFileRootPathVo;
    }

    public static String[] getOutFileRootNames() throws Exception {
        String[] names = new String[] {};
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

    public static List<DataType> getDataType() throws Exception {
        XmlParse<DataType> xmlParse = new XmlParse<DataType>(DataType.class);
        dataTypeList = xmlParse.parseXmlFileToVo(FileUtils.getDataTypePath());
        return dataTypeList;
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

    public static void saveLastOperateVo() throws Exception {
        XmlParse<LastOperateVo> xmlParse = new XmlParse<LastOperateVo>(LastOperateVo.class);
        xmlParse.genVoToXmlFile(lastOperateVo, FileUtils.getLastOperatePath());
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

    public static boolean isClassType(String type) {
        if (costantType[1].equals(type)) {
            return true;
        }
        return false;
    }
}
