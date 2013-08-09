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
// Created on 2013-8-8

package org.richie.codeGen.database.pdm;

import java.util.Comparator;

import org.richie.codeGen.core.model.Table;

/**
 * @author elfkingw
 */
public class TableSortComparator implements Comparator<Table> {

    /*
     * (non-Javadoc)
     * @see java.util.Comparator#compare(java.lang.Object, java.lang.Object)
     */
    @Override
    public int compare(Table table1, Table table2) {
        if (table1.getUpdateTime().compareTo(table2.getUpdateTime()) < 0) {
            return 1;
        } else {
            return -1;
        }
    }
}
