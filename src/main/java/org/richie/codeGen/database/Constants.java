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

package org.richie.codeGen.database;

/**
 * @author elfkingw
 */
public interface Constants {

    // ---dataBase Reader Type
    public final static String DATABASE_READER_TYPE_FILE = "file";
    public final static String DATABASE_READER_TYPE_DB   = "db";
    public final static String DATABASE_READER_TYPE_PDM  = "pdm";

    // ---database Type
    public final static String DATABASE_TYPE_DB2         = "db2";
    public final static String DATABASE_TYPE_ORACLE      = "oracle";
    public final static String DATABASE_TYPE_MYSQL       = "mysql";
    public final static String DATABASE_TYPE_MSSQL       = "sqlserver";
    public final static String DATABASE_TYPE_INFORMIX    = "informix";

    //--database jdbc URL
    public final static String JDBC_URL_ORACLE           = "jdbc:oracle:thin:@ip:1521:database";
    public final static String JDBC_URL_MSQL             = "jdbc:mysql://ip:3306/database?useUnicode=true&characterEncoding=GBK&autoReconnect=true";
    public final static String JDBC_URL_DB2              = "jdbc:db2://ip/50000:database";
    public final static String JDBC_URL_MSSQL            = "jdbc:sqlserver://ip:1433; DatabaseName=database";
}
