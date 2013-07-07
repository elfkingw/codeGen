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

package org.richie.codeGen.database.db;

import org.richie.codeGen.database.Constants;

/**
 * @author elfkingw
 */
public class JdbcConnection {

    private String url;
    private String driverClassName;
    private String userName;
    private String password;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getDriverClassName() {
        return driverClassName;
    }

    public void setDriverClassName(String driverClassName) {
        this.driverClassName = driverClassName;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
    
    public String getDatabaseType(){
        if(url == null){
            return null;
        }
        String databaseType = null;
        if(url.toLowerCase().contains(Constants.DATABASE_TYPE_DB2)){
            databaseType = Constants.DATABASE_TYPE_DB2;
        }else if(url.toLowerCase().contains(Constants.DATABASE_TYPE_MSSQL)){
            databaseType = Constants.DATABASE_TYPE_MSSQL;
        }else if(url.toLowerCase().contains(Constants.DATABASE_TYPE_MYSQL)){
            databaseType = Constants.DATABASE_TYPE_MYSQL;
        }else if(url.toLowerCase().contains(Constants.DATABASE_TYPE_ORACLE)){
            databaseType = Constants.DATABASE_TYPE_ORACLE;
        }
        return databaseType;
    }
}
