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
// Created on 2013-7-9

package org.richie.codeGen.ui.model;

import javax.swing.tree.DefaultMutableTreeNode;

import org.richie.codeGen.core.model.Column;
import org.richie.codeGen.core.model.Table;


/**
 * @author elfkingw
 *
 */
public class TableTreeNode extends DefaultMutableTreeNode{
    
    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    private Table table;
    private Column column;
    
    public Column getColumn() {
        return column;
    }
    
    public void setColumn(Column column) {
        this.column = column;
    }
    public TableTreeNode(Table table){
        super(table.getTableName()+":"+table.getTableCode());
        this.table = table;
    }
    public TableTreeNode(Column column){
        super(column.getName()+":"+column.getCode());
        this.column = column;
    }
    public TableTreeNode(String name){
        super(name);
    }
    public Table getTable() {
        return table;
    }

    public void setTable(Table table) {
        this.table = table;
    }
    

}
