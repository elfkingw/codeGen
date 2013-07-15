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
// Created on 2013-7-14

package org.richie.codeGen.ui.model;

/**
 * 用户最后一次操作记录
 * 
 * @author elfkingw
 */
public class LastOperateVo {

    private String pdmFilePath;     // 最后一次打开的pdm文件
    private String complateFilePath; // 最后一次导入模板的路径
    private String outFileRootPath; // 最后一次打开生成文件根目录

    public String getPdmFilePath() {
        return pdmFilePath;
    }

    public void setPdmFilePath(String pdmFilePath) {
        this.pdmFilePath = pdmFilePath;
    }

    public String getComplateFilePath() {
        return complateFilePath;
    }

    public void setComplateFilePath(String complateFilePath) {
        this.complateFilePath = complateFilePath;
    }

    public String getOutFileRootPath() {
        return outFileRootPath;
    }

    public void setOutFileRootPath(String outFileRootPath) {
        this.outFileRootPath = outFileRootPath;
    }

}
