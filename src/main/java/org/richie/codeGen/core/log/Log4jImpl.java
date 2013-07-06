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

import org.apache.log4j.Logger;


/**
 * @author elfkingw
 *
 */
public class Log4jImpl implements Log{

    Logger log = null ;
    public Log4jImpl(Class<?> clazz){
        log = Logger.getLogger(clazz);
    }
    /* (non-Javadoc)
     * @see org.richie.codeGen.core.log.Log#isDebugEnabled()
     */
    @Override
    public boolean isDebugEnabled() {
        return log.isDebugEnabled();
    }

    /* (non-Javadoc)
     * @see org.richie.codeGen.core.log.Log#isInfoEnabled()
     */
    @Override
    public boolean isInfoEnabled() {
        // TODO Auto-generated method stub
        return log.isInfoEnabled();
    }

    /* (non-Javadoc)
     * @see org.richie.codeGen.core.log.Log#info(java.lang.String)
     */
    @Override
    public void info(String msg) {
        log.info(msg);
    }

    /* (non-Javadoc)
     * @see org.richie.codeGen.core.log.Log#info(java.lang.String, java.lang.Throwable)
     */
    @Override
    public void info(String msg, Throwable e) {
        log.info(msg, e);
    }

    /* (non-Javadoc)
     * @see org.richie.codeGen.core.log.Log#debug(java.lang.String)
     */
    @Override
    public void debug(String msg) {
        log.debug(msg);
    }

    /* (non-Javadoc)
     * @see org.richie.codeGen.core.log.Log#debug(java.lang.String, java.lang.Throwable)
     */
    @Override
    public void debug(String msg, Throwable e) {
        log.debug(msg, e);
    }

    /* (non-Javadoc)
     * @see org.richie.codeGen.core.log.Log#warn(java.lang.String)
     */
    @Override
    public void warn(String msg) {
        log.warn(msg);
    }

    /* (non-Javadoc)
     * @see org.richie.codeGen.core.log.Log#warn(java.lang.String, java.lang.Throwable)
     */
    @Override
    public void warn(String msg, Throwable e) {
        log.warn(msg, e);
    }

    /* (non-Javadoc)
     * @see org.richie.codeGen.core.log.Log#error(java.lang.String)
     */
    @Override
    public void error(String msg) {
        log.error(msg);
    }

    /* (non-Javadoc)
     * @see org.richie.codeGen.core.log.Log#error(java.lang.String, java.lang.Throwable)
     */
    @Override
    public void error(String msg, Throwable e) {
        log.error(msg, e);
    }

}
