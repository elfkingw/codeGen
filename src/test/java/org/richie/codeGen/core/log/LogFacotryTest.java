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
// Created on 2013-7-6

package org.richie.codeGen.core.log;

import junit.framework.TestCase;


/**
 * @author elfkingw
 *
 */
public class LogFacotryTest extends TestCase {

    /**
     * Test method for {@link org.richie.codeGen.core.log.LogFacotry#getLogger(java.lang.Class)}.
     */
    public void testGetLogger() {
        try {
            Log log =LogFacotry.getLogger(LogFacotryTest.class);
            log.info("test");
        } catch (Exception e) {
            e.printStackTrace();
        }
        
    }
    public void testTryImplemtation() {
        try {
        } catch (Exception e) {
            e.printStackTrace();
        }
        
    }
}
