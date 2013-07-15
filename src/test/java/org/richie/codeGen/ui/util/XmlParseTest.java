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

import java.util.ArrayList;
import java.util.List;

import org.richie.codeGen.ui.model.CodeTemplateVo;

import junit.framework.TestCase;


/**
 * @author elfkingw
 *
 */
public class XmlParseTest extends TestCase {

    /**
     * Test method for {@link org.richie.codeGen.ui.util.XmlParse#parseXmlFileToVo(java.lang.String)}.
     */
    public void testParseXmlFileToVo() {
        CodeTemplateVo vo = new CodeTemplateVo();
        vo.setFileName("list1.vm");
        vo.setIsSelected(true);
        vo.setOutFilePath("list.vm");
        CodeTemplateVo vo1 = new CodeTemplateVo();
        vo1.setFileName("addjs.vm");
        vo1.setIsSelected(false);
        vo1.setOutFilePath("list.vm");
        List list = new ArrayList();
        list.add(vo);
        list.add(vo1);
        try {
            XmlParse<CodeTemplateVo> xml = new XmlParse<CodeTemplateVo>(CodeTemplateVo.class);
            xml.genVoToXmlFile(list,"E:\\workspace10\\codeGen\\src\\main\\java\\template\\test.xml");
            List list1 = xml.parseXmlFileToVo("E:\\workspace10\\codeGen\\src\\main\\java\\template\\test.xml");
            System.out.println(list1.size());
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    /**
     * Test method for {@link org.richie.codeGen.ui.util.XmlParse#genVoToXmlFile(java.lang.Object, java.lang.String)}.
     */
    public void testGenVoToXmlFileTString() {
        XmlParse<CodeTemplateVo> xml = new XmlParse<CodeTemplateVo>(CodeTemplateVo.class);
        List list1;
        try {
            list1 = xml.parseXmlFileToVo("E:\\workspace10\\codeGen\\src\\main\\java\\template\\test.xml");
            System.out.println(list1.size());
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    /**
     * Test method for {@link org.richie.codeGen.ui.util.XmlParse#genVoToXmlFile(java.util.List, java.lang.String)}.
     */
    public void testGenVoToXmlFileListOfTString() {
        fail("Not yet implemented");
    }

    /**
     * Test method for {@link org.richie.codeGen.ui.util.XmlParse#writeDocument(org.dom4j.Document, java.lang.String)}.
     */
    public void testWriteDocument() {
        fail("Not yet implemented");
    }

}
