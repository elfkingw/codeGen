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

import org.richie.codeGen.core.exception.CGException;
import org.richie.codeGen.core.model.Column;
import org.richie.codeGen.core.model.Table;

import junit.framework.TestCase;

/**
 * @author elfkingw
 */
public class PdmParserTest extends TestCase {

    /**
     * Test method for {@link org.richie.codeGen.database.util.PdmParser#parsePdmFile(java.lang.String)}.
     */
    public void testParsePDM_VO() {
        PdmParser pp = new PdmParser();
        List<Table> tabs;
        try {
            tabs = pp.parsePdmFile("C:\\Users\\Administrator\\Desktop\\velocity.pdm");
            for (Table tab : tabs) {
                System.out.println(tab);
                List<Column> cList = tab.getFieldList();
                for (Column column : cList) {
                    System.out.println("   " + column);
                }
            }
        } catch (CGException e) {
            e.printStackTrace();
        }
    }

}
