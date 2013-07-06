/*
 * Copyright 2013  elfkingw
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
// Created on 2013-6-29
package org.richie.codeGen.core.model;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author wanghua 2013-6-29
 * 
 */
public class Table implements ITable {
	private String name;
	private String cnName;
	private List<ITableColumn> fieldList;
	private String javaName;
	private String packageName;
	private Map<ITableColumn, ITable> oneToOneMap;
	private Map<ITableColumn, ITable> oneToManyMap;
	private long updateTime;
	/*
	 * (non-Javadoc)
	 * 
	 * @see org.richie.database.model.ITable#getName()
	 */
	@Override
	public String getName() {
		return name;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.richie.database.model.ITable#getCnName()
	 */
	@Override
	public String getCnName() {
		return cnName;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.richie.database.model.ITable#getFields()
	 */
	@Override
	public List<ITableColumn> getFields() {
		return fieldList;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.richie.database.model.ITable#getJavaName()
	 */
	@Override
	public String getJavaName() {
		return javaName;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.richie.database.model.ITable#getPackageName()
	 */
	@Override
	public String getPackageName() {
		return packageName;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.richie.database.model.ITable#getFullJavaName()
	 */
	@Override
	public String getFullJavaName() {
		return getPackageName() + "." + getJavaName();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.richie.database.model.ITable#getOneToOne()
	 */
	@Override
	public Map<ITableColumn, ITable> getOneToOne() {
		return oneToOneMap;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.richie.database.model.ITable#getOneToMany()
	 */
	@Override
	public Map<ITableColumn, ITable> getOneToMany() {
		return oneToManyMap;
	}

	public void addOneToOneTable(ITableColumn column, ITable table) {
		if (oneToOneMap == null) {
			oneToOneMap = new HashMap<ITableColumn, ITable>();
		}
		oneToOneMap.put(column, table);
	}
	
	public void addOneToManyTable(ITableColumn column, ITable table) {
		if (oneToManyMap == null) {
			oneToManyMap = new HashMap<ITableColumn, ITable>();
		}
		oneToManyMap.put(column, table);
	}

	public void setColumnList(List<ITableColumn> columnList) {
		this.fieldList = columnList;
	}

	public Map<ITableColumn, ITable> getOneToOneMap() {
		return oneToOneMap;
	}

	public void setOneToOneMap(Map<ITableColumn, ITable> oneToOneMap) {
		this.oneToOneMap = oneToOneMap;
	}

	public Map<ITableColumn, ITable> getOneToManyMap() {
		return oneToManyMap;
	}

	public void setOneToManyMap(Map<ITableColumn, ITable> oneToManyMap) {
		this.oneToManyMap = oneToManyMap;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setCnName(String cnName) {
		this.cnName = cnName;
	}

	public void setJavaName(String javaName) {
		this.javaName = javaName;
	}

	public void setPackageName(String packageName) {
		this.packageName = packageName;
	}

	/* (non-Javadoc)
	 * @see org.richie.database.model.ITable#getUpdateTime()
	 */
	@Override
	public long getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(long updateTime) {
		this.updateTime = updateTime;
	}
}
