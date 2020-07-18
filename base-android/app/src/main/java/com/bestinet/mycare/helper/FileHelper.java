package com.bestinet.mycare.helper;

import android.content.Context;
import android.os.Environment;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class FileHelper {


    public static boolean isFileExist(Context context, String filename) {
        String filePath = context.getExternalFilesDir(null) + "/" + filename;

        File file = new File(filePath);// context.getFileStreamPath(filename);
        return file != null && file.exists();
    }

    public static boolean isFilePathExist(Context context, String filePath) {

        File file = new File(filePath);// context.getFileStreamPath(filename);
        return file != null && file.exists();
    }


    public static String getFileLocation(Context context) {
        return context.getFilesDir().toString();
    }


    public static byte[] convertPDFToByteArray(String filePath) {

        File file = new File(filePath);

        FileInputStream fis = null;
        try {
            fis = new FileInputStream(file);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        byte[] buf = new byte[1024];
        try {
            for (int readNum; (readNum = fis.read(buf)) != -1;) {
                bos.write(buf, 0, readNum); //no doubt here is 0
                //Writes len bytes from the specified byte array starting at offset off to this byte array output stream.
                System.out.println("read " + readNum + " bytes,");
            }
        } catch (IOException ex) {
            //Logger.getLogger(genJpeg.class.getName()).log(Level.SEVERE, null, ex);
        }
        byte[] bytes = bos.toByteArray();
        return bytes;
    }

    public static String getPrivateExternalStorageBaseDir(Context context, String dirType)
    {
        String ret = "";
        if(isExternalStorageMounted()) {
            File file = context.getExternalFilesDir(dirType);
            ret = file.getAbsolutePath();
        }
        return ret;
    }

    public static boolean isExternalStorageMounted() {

        String dirState = Environment.getExternalStorageState();
        return Environment.MEDIA_MOUNTED.equals(dirState);
    }

    public static String getStringFromFile (String filePath) throws Exception {
        File fl = new File(filePath);
        FileInputStream fin = new FileInputStream(fl);
        String ret = convertStreamToString(fin);
        //Make sure you close all streams.
        fin.close();
        return ret;
    }

    public static String convertStreamToString(InputStream is) throws Exception {
        BufferedReader reader = new BufferedReader(new InputStreamReader(is));
        StringBuilder sb = new StringBuilder();
        String line = null;
        while ((line = reader.readLine()) != null) {
            sb.append(line).append("\n");
        }
        reader.close();
        return sb.toString();
    }

}



