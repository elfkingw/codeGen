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
// Created on 2013-7-21

package org.richie.codeGen.ui;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

/**
 * @author elfkingw
 */
public class CodeStatistics {

    private int codeCount;
    private int fileCount;

    public void codeStatis(String filePath) {
        BufferedReader reader = null;
        try {
            File file = new File(filePath);
            File[] files = file.listFiles();
            for (File subFile : files) {
                if (subFile.isDirectory()) {
                    codeStatis(subFile.getAbsolutePath());
                } else if(subFile.getName().endsWith(".java")) {
                    fileCount++;
                    reader = new BufferedReader(new FileReader(subFile));
                    String line = null;
                    while ((line = reader.readLine()) != null) {
                        codeCount++;
                    }
                }
            }
        } catch (Exception e) {

        } finally {
            try {
                if (reader != null) reader.close();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        }

    }
    public void printCodeLine(){
        System.out.println("共有文件"+ fileCount+"个");
        System.out.println("代码"+ codeCount+"行");
    }

    public static void main(String[] args) {
        String filePath = "E:\\workspace10\\druid\\src\\main\\java\\";
        CodeStatistics codeStatistics= new CodeStatistics();
        codeStatistics.codeStatis(filePath);
        codeStatistics.printCodeLine();
    }
}
