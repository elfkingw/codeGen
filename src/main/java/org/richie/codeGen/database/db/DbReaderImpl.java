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

import java.sql.Connection;
import java.util.List;

import org.richie.codeGen.core.model.Table;
import org.richie.codeGen.database.BaseReader;
import org.richie.codeGen.database.DatabaseReader;

/**
 * @author elfkingw
 */
public abstract class DbReaderImpl extends BaseReader implements DatabaseReader {

    protected JdbcConnection jdbcCon;

    /**
     * @param databaseType
     */
    public DbReaderImpl(String databaseType){
        super(databaseType);
    }

    /*
     * (non-Javadoc)
     * @see org.richie.codeGen.database.DatabaseReader#getTables()
     */
    @Override
    public List<Table> getTables() throws Exception{
        // TODO Auto-generated method stub
        return null;
    }

    /*
     * (non-Javadoc)
     * @see org.richie.codeGen.database.DatabaseReader#getDettailByTableName(java.lang.String)
     */
    @Override
    public Table getDettailByTableCode(String code) {
        // TODO Auto-generated method stub
        return null;
    }

    public Connection getConnection() {
        // TODO 链接到数据库
        return null;
    }

    public JdbcConnection getJdbcCon() {
        return jdbcCon;
    }

    public void setJdbcCon(JdbcConnection jdbcCon) {
        this.jdbcCon = jdbcCon;
    }

}
