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
// Created on 2013-7-7

package org.richie.codeGen.database.util;

import java.util.List;

import junit.framework.TestCase;

import org.richie.codeGen.core.model.Column;
import org.richie.codeGen.core.model.Table;
import org.richie.codeGen.database.pdm.PdmParser;

/**
 * @author elfkingw
 */
public class PdmParserTest extends TestCase {

    /**
     * Test method for {@link org.richie.codeGen.database.pdm.PdmParser#parsePdmFile(java.lang.String)}.
     * @throws Exception 
     */
    public void testParsePDM_VO() throws Exception {
        String mysqlPdm = "mysqlPdm.xml";
        testParsePdm(mysqlPdm);
        String db2Pdm = "db2Pdm.xml";
        testParsePdm(db2Pdm);
        String informixPdm = "informixPdm.xml";
        testParsePdm(informixPdm);
        String myssqlPdm = "mssqlPdm.xml";
        testParsePdm(myssqlPdm);
        String oraclePdm = "oraclePdm.xml";
        testParsePdm(oraclePdm);
        String sybasePdm = "sybasePdm.xml";
        testParsePdm(sybasePdm);
    }

    public void testParsePdm(String filePath) throws Exception {
        List<Table> tabs;
        try {
            String path = ClassLoader.getSystemResource(filePath).getPath();
            tabs = PdmParser.parsePdmFile(path);
            for (Table tab : tabs) {
                System.out.println(tab);
                List<Column> cList = tab.getFields();
                for (Column column : cList) {
                    System.out.println("   " + column);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new Exception(filePath + "解析出错",e); 
        }
    }

}
