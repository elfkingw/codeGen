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

import java.io.*;
import java.net.URLDecoder;

import org.richie.codeGen.core.log.Log;
import org.richie.codeGen.core.log.LogFacotry;

/**
 * @author elfkingw
 */
public class FileUtils {

    public static final String TEMPLATE_FOLDER = "template";
    public static final String CONFIG_FOLDER = "config";
    public static final String OUTFILE_FOLDER = "out";
    public static final String HELP_FOLDER = "help";
    public static final String EXAMPLE_FOLDER = "example";
    public static final String EXAMPLE_PDM_FILE = "example.pdm";
    public static final String CONFIG_TEMPLATE_FILENAME = "CodeTemplate.xml";
    public static final String CONFIG_CONSTANT_FILENAME = "ConstantConfig.xml";
    public static final String CONFIG_OUTFILEPATH_FILENAME = "OutFilePath.xml";
    public static final String CONFIG_LASTOPERATE_FILENAME = "LastOperate.xml";
    public static final String CONFIG_LASTTTEMPLATE_FILENAME = "LastTemplate.xml";
    public static final String CONFIG_DATA_TYPE_FILENAME = "DataTypeConfig.xml";
    public static final String CONFIG_UI_TYPE_FILENAME = "UIType.xml";
    public static final String ENCODING = "UTF-8";

    public static String CONFIG_ROOT_PATH = null;
    private static Log log = LogFacotry.getLogger(FileUtils.class);

    static {
        FileUtils utils = new FileUtils();
        String path = utils.getClass().getProtectionDomain().getCodeSource().getLocation().getPath();
        try {
            // 解决jar放在带有中文的目录下问题
            path = URLDecoder.decode(path, "utf-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        // 测试环境的路径放在项目根目录下
        if (path.endsWith("classes/")) {
            path = path.replace("target/classes/", "");
        }
        int lastIndex = path.lastIndexOf("/") + 1;
        File f = new File(path.substring(0, lastIndex));
        CONFIG_ROOT_PATH = f.getAbsolutePath();
        log.info("-----配置根路径：" + CONFIG_ROOT_PATH);
    }

    /**
     * upload the file to given filePath
     *
     * @param targetPath     上传文件目标文件夹
     * @param targetFileName 上传文件目标文件名
     * @param file
     * @throws IOException
     */
    public static void uploadFile(String targetPath, String targetFileName, File file) throws IOException {
        FileOutputStream fos = null;
        FileInputStream fis = null;
        try {
            File targetFolder = new File(targetPath);
            if (!targetFolder.exists()) {
                targetFolder.mkdirs();
            }
            File outFile = new File(targetFolder + File.separator + targetFileName);
            if (!outFile.exists()) {
                outFile.createNewFile();
            }
            // 如果目标文件和上传文件是一个文件直接返回
            if (outFile.getPath().endsWith(file.getPath())) {
                return;
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
        BufferedWriter fos = null;
        try {
            File outFilePath = new File(filePath);
            if (!outFilePath.exists()) {
                outFilePath.mkdirs();
            }
            File outFile = new File(outFilePath + File.separator + fileName);
            if (!outFile.exists()) {
                outFile.createNewFile();
            }
            fos = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(outFile), ENCODING));
            fos.write(content);
        } finally {
            if (fos != null) {
                fos.close();
            }
        }
    }

    /**
     * 读取文件内容
     *
     * @param file
     * @return
     * @throws IOException
     */
    public static String readFile(File file) throws IOException {
        InputStream inputStream = new FileInputStream(file);
        return readFile(inputStream);
    }

    public static String readFile(InputStream inputStream) throws IOException {
        StringBuilder sb = new StringBuilder();
        BufferedReader reader = null;
        try {
            reader = new BufferedReader(new InputStreamReader(inputStream, ENCODING));
            String lineStr = null;
            while ((lineStr = reader.readLine()) != null) {
                sb.append(lineStr + "\n");
            }
        } finally {
            if (null != reader) {
                reader.close();
            }
        }
        return sb.toString();
    }

    public static String getConfigPath() {
        return "";
    }

    public static String getTemplatePath() {
        return "";
    }

    public static String getDefaultOutPath() {
        String path = CONFIG_ROOT_PATH + File.separator + OUTFILE_FOLDER;
        File file = new File(path);
        if (!file.exists()) {
            file.mkdirs();
        }
        return path;
    }

    public static String getHelpPath() throws Exception{
        String path = JarFileUtils.TEMP_FILE_PATH + File.separator + HELP_FOLDER;
        File file = new File(path);
        if (!file.exists()) {
            file.mkdirs();
        }
        String[] files = file.list();
        if (files == null || files.length == 0) {
            copyHelpFile();
        }
        return path;
    }

    private static void copyHelpFile() throws Exception {
        String[] helpFileNames = new String[]{"Velocity模板使用指南中文版.pdf", "帮助文档.docx"};
        for (String fileName : helpFileNames) {
            FileOutputStream fos = null;
            InputStream fis = JarFileUtils.class.getResourceAsStream("/resources/help/" + fileName);
            try {
                File outFile = new File(JarFileUtils.TEMP_FILE_PATH +File.separator+"help"+ File.separator + fileName);
                if (!outFile.exists()) {
                    outFile.createNewFile();
                }
                fos = new FileOutputStream(outFile);
                byte[] buffer = new byte[1024];
                int len = 0;
                while ((len = fis.read(buffer)) > 0) {
                    fos.write(buffer, 0, len);
                }
                fos.flush();
            } finally {
                if (fos != null) {
                    fos.close();
                }
                if (fis != null) {
                    fis.close();
                }
            }
        }
    }

    public static String getConfigTemplatePath() throws IOException {
        String path = CONFIG_TEMPLATE_FILENAME;
        return path;
    }

    public static String getConstantConfigPath() throws IOException {
        String path = CONFIG_CONSTANT_FILENAME;
        return path;
    }

    public static String getOutFileRootPath() throws IOException {
        String path = CONFIG_OUTFILEPATH_FILENAME;
        return path;
    }

    public static String getLastOperatePath() throws IOException {
        String path = CONFIG_LASTOPERATE_FILENAME;
        return path;
    }

    public static String getLastTemplatePath() throws IOException {
        String path = CONFIG_LASTTTEMPLATE_FILENAME;
        return path;
    }

    public static String getDataTypePath() throws IOException {
        String path = CONFIG_DATA_TYPE_FILENAME;
        return path;
    }

    public static String getUITypePath() throws IOException {
        String path = CONFIG_UI_TYPE_FILENAME;
        return path;
    }

    public static void main(String[] args) {
        String path = "C:/Users/Administrator/Desktop/codeGen/codeGen-0.0.1-SNAPSHOT-jar-with-dependencies.jar";
        int lastIndex = path.lastIndexOf("/") + 1;
        CONFIG_ROOT_PATH = path.substring(0, lastIndex);
        log.info("-----配置根路径" + path);
        log.info("-----配置根路径1" + CONFIG_ROOT_PATH);
    }
}
