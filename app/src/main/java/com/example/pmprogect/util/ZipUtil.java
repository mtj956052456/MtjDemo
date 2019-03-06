package com.example.pmprogect.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

/**
 * @author mtj
 * @time 2018/8/28 2018 08
 * @des
 */

public class ZipUtil {

    public static void zip(String src,String dest) throws IOException {
        //定义压缩输出流
        ZipOutputStream out = null;
        try {
            //传入源文件
            File outFile= new File(dest);
            File fileOrDirectory= new File(src);
            //传入压缩输出流
            out = new ZipOutputStream(new FileOutputStream(outFile));
            //判断是否是一个文件或目录
            //如果是文件则压缩
            if (fileOrDirectory.isFile()){
                zipFileOrDirectory(out,fileOrDirectory, "");
            } else {
                //否则列出目录中的所有文件递归进行压缩
                File[]entries = fileOrDirectory.listFiles();
                for (int i= 0; i < entries.length;i++) {
                    zipFileOrDirectory(out,entries[i],"");
                }
            }
        }catch(IOException ex) {
            ex.printStackTrace();
        }finally{
            if (out!= null){
                try {
                    out.close();
                }catch(IOException ex) {
                    ex.printStackTrace();
                }
            }
        }
    }

    private static void zipFileOrDirectory(ZipOutputStream out, File fileOrDirectory, String curPath)throws IOException {
        FileInputStream in = null;
        try {
            //判断目录是否为null
            if (!fileOrDirectory.isDirectory()){
                byte[] buffer= new byte[4096];
                int bytes_read;
                in= new FileInputStream(fileOrDirectory);
                //归档压缩目录
                ZipEntry entry = new ZipEntry(curPath + fileOrDirectory.getName());
                //将压缩目录写到输出流中
                out.putNextEntry(entry);
                while ((bytes_read= in.read(buffer))!= -1) {
                    out.write(buffer,0, bytes_read);
                }
                out.closeEntry();
            } else {
                //列出目录中的所有文件
                File[]entries = fileOrDirectory.listFiles();
                for (int i= 0; i < entries.length;i++) {
                    //递归压缩
                    zipFileOrDirectory(out,entries[i],curPath + fileOrDirectory.getName()+ "/");
                }
            }
        }catch(IOException ex) {
            ex.printStackTrace();
        }finally{
            if (in!= null){
                try {
                    in.close();
                }catch(IOException ex) {
                    ex.printStackTrace();
                }
            }
        }
    }


    //压缩
    public static void zip(File srcFile, File desFile)throws IOException {
        GZIPOutputStream zos = null;
        FileInputStream fis = null;
        try {
            //创建压缩输出流,将目标文件传入
            zos = new GZIPOutputStream(new FileOutputStream(desFile));
            //创建文件输入流,将源文件传入
            fis = new FileInputStream(srcFile);
            byte[] buffer= new byte[1024];
            int len= -1;
            //利用IO流写入写出的形式将源文件写入到目标文件中进行压缩
            while ((len= (fis.read(buffer)))!= -1) {
                zos.write(buffer,0, len);
            }
        }finally{
            close(zos);
            close(fis);
        }
    }

    //解压
    public static void unZip(File srcFile,File desFile) throws IOException {
        GZIPInputStream zis= null;
        FileOutputStream fos = null;
        try {
            //创建压缩输入流,传入源文件
            zis = new GZIPInputStream(new FileInputStream(srcFile));
            //创建文件输出流,传入目标文件
            fos = new FileOutputStream(desFile);
            byte[] buffer= new byte[1024];
            int len= -1;
            //利用IO流写入写出的形式将压缩源文件解压到目标文件中
            while ((len= (zis.read(buffer)))!= -1) {
                fos.write(buffer,0, len);
            }
        }finally{
            close(zis);
            close(fos);
        }
    }

    private static void close(InputStream zis) {
        try {
            zis.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void close(OutputStream fos) {
        try {
            fos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
