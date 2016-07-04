package com.peerless2012.sspai.common.utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * @author peerless2012
 * @Email peerless2012@126.com
 * @DateTime 2016/6/23 9:32
 * @Version V1.0
 * @Description :
 */
public class FileUtils {

    /**
     * 将字符串以UTF-8编码保存到文件中
     * @param str    保存的字符串
     * @param fileName 文件的名字
     * @return  是否保存成功
     */
    public static boolean saveStrToFile(String str, String fileName) {

        return saveStrToFile(str, fileName, "UTF-8");
    }


    /**
     * 将字符串以charsetName编码保存到文件中
     * @param str    保存的字符串
     * @param fileName  文件的名字
     * @param charsetName  字符串编码
     * @return  是否保存成功
     */
    public static boolean saveStrToFile(String str, String fileName, String charsetName) {

        if (str == null || "".equals(str)) {
            return false;
        }

        FileOutputStream stream = null;
        try {
            File file = new File(fileName);
            if (!file.getParentFile().exists()) {
                file.getParentFile().mkdirs();
            }

            byte[] b = null;
            if (charsetName != null && !"".equals(charsetName)) {
                b = str.getBytes(charsetName);
            }
            else {
                b = str.getBytes();
            }

            stream = new FileOutputStream(file);
            stream.write(b, 0, b.length);
            stream.flush();
            return true;
        } catch (Exception e) {
            return false;
        } finally {
            if (stream != null) {
                try {
                    stream.close();
                    stream = null;
                } catch (Exception e) {
                }
            }
        }
    }

    public static StringBuilder readFile(String filePath, String charsetName) {

        File file = new File(filePath);
        StringBuilder fileContent = new StringBuilder("");
        if (file == null || !file.isFile()) {
            return null;
        }

        BufferedReader reader = null;
        try {
            InputStreamReader is = new InputStreamReader(
                    new FileInputStream(file), charsetName);
            reader = new BufferedReader(is);
            String line = null;
            while ((line = reader.readLine()) != null) {
                if (!fileContent.toString().equals("")) {
                    fileContent.append("\r\n");
                }
                fileContent.append(line);
            }
            return fileContent;
        } catch (IOException e) {
            throw new RuntimeException("IOException occurred. ", e);
        } finally {
            IOUtils.close(reader);
        }
    }
}
