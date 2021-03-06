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

import java.util.List;

import org.richie.codeGen.core.model.Table;

/**
 * @author elfkingw
 */
public class BaseReader {

    protected String      databaseType;

    protected List<Table> list;

    public BaseReader(String databaseType){
        this.databaseType = databaseType;
    }

    public String getDatabaseType() {
        return databaseType;
    }

    public void setDatabaseType(String databaseType) {
        this.databaseType = databaseType;
    }

    public List<Table> getList() {
        return list;
    }

    public void setList(List<Table> list) {
        this.list = list;
    }

}
