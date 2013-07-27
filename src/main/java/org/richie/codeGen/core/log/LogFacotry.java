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

import java.lang.reflect.Constructor;


/**
 * @author elfkingw
 *
 */
public class LogFacotry {

    @SuppressWarnings("rawtypes")
    private static Constructor logConstructor = null;
    static{
        tryImplementation("org.apache.log4j.Logger", "org.richie.codeGen.core.log.Log4jImpl");
        tryImplementation("org.slf4j.Logger", "org.richie.codeGen.core.log.Slf4jImpl");
        if(logConstructor == null){
            try {
                logConstructor = NoLoggingjImpl.class.getConstructor(Class.class);
            } catch (SecurityException e) {
                e.printStackTrace();
            } catch (NoSuchMethodException e) {
                e.printStackTrace();
            } 
        }
    }
    public static Log getLogger(Class<?> clazz){
        Log log;
        try {
            log = (Log) logConstructor.newInstance(clazz);
        } catch (Throwable e) {
            throw new RuntimeException("Error creating logger for class " + clazz + ".  Cause: " + e, e);
        }
        return log;
    }
    
    public static void tryImplementation(String testClassName,String implClassName){
        if(logConstructor != null){
            return;
        }
        try {
            Class.forName(testClassName);
            logConstructor =  Class.forName(implClassName).getConstructor(Class.class);
        } catch (Exception e) {
        }
    }
}
