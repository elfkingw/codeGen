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

package org.richie.codeGen.database.pdm;

import java.util.Collections;
import java.util.List;

import org.richie.codeGen.core.model.Table;
import org.richie.codeGen.database.FileReaderImpl;


/**
 * @author elfkingw
 *
 */
public class PdmReaderImpl extends FileReaderImpl {

    /**
     * @param databaseType
     */
    public PdmReaderImpl(String databaseType){
        super(databaseType);
    }
   
    public PdmReaderImpl(String databaseType,String filePath){
        super(databaseType,filePath);
    }
    

    /* (non-Javadoc)
     * @see org.richie.codeGen.database.FileReaderImpl#parseFile(java.io.File)
     */
    @Override
    public List<Table> parseFile(String filePath) throws Exception{
        List<Table> list = PdmParser.parsePdmFile(filePath);
        if(list != null){
            Collections.sort(list,new TableSortComparator());
        }
        return list;
    }

}
