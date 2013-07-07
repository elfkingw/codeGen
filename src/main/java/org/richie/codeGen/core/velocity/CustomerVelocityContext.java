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
// $Id$

package org.richie.codeGen.core.velocity;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Properties;

import org.richie.codeGen.core.log.Log;
import org.richie.codeGen.core.log.LogFacotry;


/**
 * 获取客户自定义 velocity context
 * @author Administrator
 *
 */
public class CustomerVelocityContext {
   public static  Log log  = LogFacotry.getLogger(CustomerVelocityContext.class);
    public static Map<String , Object> getCustomerVelociTyContext(){
        Map<String,Object> map = new HashMap<String,Object>();
        InputStream in = null;
        try {
            log.debug("loading codeGen.properties");
            in = ClassLoader.getSystemResourceAsStream("codeGen.properties");
            if(in == null){
                log.info("don't fund codeGen.properties ");
                return null;
            }
            Properties p = new Properties();
            p.load(in);
            Iterator<Object> it = p.keySet().iterator();
            while(it.hasNext()){
                String key = (String) it.next();
                String className = p.getProperty(key);
                Object obj = getClassInstance(className, key);
                if(obj != null){
                    map.put(key, obj);
                    log.info("codeGen.properties key="+key+" class:["+className+"]success load into velocity context");
                }
            }
        } catch (IOException e) {
            log.error("read codeGen.properties failed , cause :"+e.getMessage(),e);
        }finally{
            try {
                if(in != null)
                    in.close();
            } catch (IOException e) {
            }
        }
        return map;
    }
    
    private static Object getClassInstance(String className,String key){
        Object obj = null;
        try {
            Class<?> clazz = Class.forName(className);
            obj = clazz.newInstance();
        } catch (ClassNotFoundException e) {
            log.error("codeGen.properties key="+key+" class:["+className+"] class not fund,this key load failed" );
        } catch (InstantiationException e) {
            log.error("codeGen.properties key="+key+" class:["+className+"] instainationException,this key load failed" ,e);
        } catch (IllegalAccessException e) {
            log.error("codeGen.properties key="+key+" class:["+className+"] IllegalAccessException,this key load failed" ,e);
        }
        return obj;
    }
}
