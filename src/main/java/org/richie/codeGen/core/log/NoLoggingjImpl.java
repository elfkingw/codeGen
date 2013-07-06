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


/**
 * @author elfkingw
 *
 */
public class NoLoggingjImpl implements Log{

    private Class<?> clazz;
    public NoLoggingjImpl(Class<?> clazz){
        this.clazz = clazz;
    }
    /* (non-Javadoc)
     * @see org.richie.codeGen.core.log.Log#isDebugEnabled()
     */
    @Override
    public boolean isDebugEnabled() {
        return false;
    }

    /* (non-Javadoc)
     * @see org.richie.codeGen.core.log.Log#isInfoEnabled()
     */
    @Override
    public boolean isInfoEnabled() {
        return false;
    }

    /* (non-Javadoc)
     * @see org.richie.codeGen.core.log.Log#info(java.lang.String)
     */
    @Override
    public void info(String msg) {
        System.out.println(logStr(msg));
    }

    private String logStr (String msg){
        StringBuilder str = new StringBuilder();
        str.append(clazz.getName()+" : "+ msg);
        return str.toString();
    }
    /* (non-Javadoc)
     * @see org.richie.codeGen.core.log.Log#info(java.lang.String, java.lang.Throwable)
     */
    @Override
    public void info(String msg, Throwable e) {
        System.out.println(logStr(msg));
        e.printStackTrace();
    }

    /* (non-Javadoc)
     * @see org.richie.codeGen.core.log.Log#debug(java.lang.String)
     */
    @Override
    public void debug(String msg) {
        System.out.println(logStr(msg));
    }

    /* (non-Javadoc)
     * @see org.richie.codeGen.core.log.Log#debug(java.lang.String, java.lang.Throwable)
     */
    @Override
    public void debug(String msg, Throwable e) {
        System.out.println(logStr(msg));
        e.printStackTrace();
    }

    /* (non-Javadoc)
     * @see org.richie.codeGen.core.log.Log#warn(java.lang.String)
     */
    @Override
    public void warn(String msg) {
        System.out.println(logStr(msg));
    }

    /* (non-Javadoc)
     * @see org.richie.codeGen.core.log.Log#warn(java.lang.String, java.lang.Throwable)
     */
    @Override
    public void warn(String msg, Throwable e) {
        System.out.println(logStr(msg));
        e.printStackTrace();
    }

    /* (non-Javadoc)
     * @see org.richie.codeGen.core.log.Log#error(java.lang.String)
     */
    @Override
    public void error(String msg) {
        System.err.println(logStr(msg));
    }

    /* (non-Javadoc)
     * @see org.richie.codeGen.core.log.Log#error(java.lang.String, java.lang.Throwable)
     */
    @Override
    public void error(String msg, Throwable e) {
        System.err.println(logStr(msg));
        e.printStackTrace();
    }

}
