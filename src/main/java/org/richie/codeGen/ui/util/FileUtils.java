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
// Created on 2013-7-13

package org.richie.codeGen.ui.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;

/**
 * @author elfkingw
 */
public class FileUtils {

    public static final String TEMPLATE_FOLDER               = "template";
    public static final String CONFIG_FOLDER                 = "config";
    public static final String CONFIG_TEMPLATE_FILENAME      = "CodeTemplate.xml";
    public static final String CONFIG_CONSTANT_FILENAME      = "ConstantConfig.xml";
    public static final String CONFIG_OUTFILEPATH_FILENAME   = "OutFilePath.xml";
    public static final String CONFIG_LASTOPERATE_FILENAME   = "LastOperate.xml";
    public static final String CONFIG_LASTTTEMPLATE_FILENAME = "LastTemplate.xml";
    public static final String CONFIG_DATA_TYPE_FILENAME     = "DataType.xml";

    /**
     * upload the file to given filePath
     * 
     * @param upLoadPath
     * @param file
     * @throws IOException
     */
    public static void uploadFile(String upLoadPath, String outFileName, File file) throws IOException {
        FileOutputStream fos = null;
        FileInputStream fis = null;
        try {
            File outFilePath = new File(upLoadPath);
            if (!outFilePath.exists()) {
                outFilePath.mkdirs();
            }
            File outFile = new File(outFilePath + File.separator + outFileName);
            if (!outFile.exists()) {
                outFile.createNewFile();
            }
            fos = new FileOutputStream(outFile);
            fis = new FileInputStream(file);
            byte[] buffer = new byte[1024];
            int len = 0;
            while ((len = fis.read(buffer)) > 0) {
                fos.write(buffer, 0, len);
            }
        } finally {
            if (fos != null) {
                fos.close();
            }
            if (fis != null) {
                fis.close();
            }
        }
    }

    /**
     * @param filePath
     * @param fileName
     * @param content
     * @throws IOException
     */
    public static void witerFile(String filePath, String fileName, String content) throws IOException {
        FileWriter fos = null;
        try {
            File outFilePath = new File(filePath);
            if (!outFilePath.exists()) {
                outFilePath.mkdirs();
            }
            File outFile = new File(outFilePath + File.separator + fileName);
            if (!outFile.exists()) {
                outFile.createNewFile();
            }
            fos = new FileWriter(outFile);
            fos.write(content);
        } finally {
            if (fos != null) {
                fos.close();
            }
        }
    }

    public static String getConfigPath() {
        String path = ClassLoader.getSystemResource("").getPath() + File.separator + CONFIG_FOLDER;
        File file = new File(path);
        if (!file.exists()) {
            file.mkdirs();
        }
        return path;
    }

    public static String getTemplatePath() {
        String path = ClassLoader.getSystemResource("").getPath() + File.separator + TEMPLATE_FOLDER;
        File file = new File(path);
        if (!file.exists()) {
            file.mkdirs();
        }
        return path;
    }

    public static String getConfigTemplatePath() throws IOException {
        String path = getConfigPath() + File.separator + CONFIG_TEMPLATE_FILENAME;
        return path;
    }

    public static String getConstantConfigPath() throws IOException {
        String path = getConfigPath() + File.separator + CONFIG_CONSTANT_FILENAME;
        return path;
    }

    public static String getOutFileRootPath() throws IOException {
        String path = getConfigPath() + File.separator + CONFIG_OUTFILEPATH_FILENAME;
        return path;
    }

    public static String getLastOperatePath() throws IOException {
        String path = getConfigPath() + File.separator + CONFIG_LASTOPERATE_FILENAME;
        return path;
    }

    public static String getLastTemplatePath() throws IOException {
        String path = getConfigPath() + File.separator + CONFIG_LASTTTEMPLATE_FILENAME;
        return path;
    }

    public static String getDataTypePath() throws IOException {
        String path = getConfigPath() + File.separator + CONFIG_DATA_TYPE_FILENAME;
        return path;
    }
}
