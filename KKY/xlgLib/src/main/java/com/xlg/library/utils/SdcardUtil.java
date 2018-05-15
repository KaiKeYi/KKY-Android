package com.xlg.library.utils;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;
import android.os.StatFs;
import android.graphics.Bitmap.CompressFormat;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.util.LinkedList;
import java.util.List;

/**
 * @Author: Jason
 * @Time: 2018/4/19 11:15
 * @Description:
 */
public class SdcardUtil {
    public SdcardUtil() {
    }

    public static List<Object> searchFiles(File file, String[] extensionName) {
        if(file != null && extensionName != null) {
            List<Object> files = null;
            File[] fs = file.listFiles();
            if(fs != null && fs.length != 0) {
                files = new LinkedList();
                String filePath = null;
                File[] var8 = fs;
                int var7 = fs.length;

                for(int var6 = 0; var6 < var7; ++var6) {
                    File f = var8[var6];
                    if(f.isDirectory()) {
                        List<Object> flist = searchFiles(f, extensionName);
                        if(flist != null) {
                            files.addAll(flist);
                        }
                    } else {
                        filePath = f.getName();
                        String[] var12 = extensionName;
                        int var11 = extensionName.length;

                        for(int var10 = 0; var10 < var11; ++var10) {
                            String ex = var12[var10];
                            if(filePath.endsWith(ex)) {
                                files.add(f.getAbsolutePath());
                                break;
                            }
                        }
                    }
                }

                return files;
            } else {
                return null;
            }
        } else {
            return null;
        }
    }

    public static boolean sdcardIsAlready() {
        return Environment.getExternalStorageState().equals("mounted");
    }

    public static String getSDPath() {
        String sdDir = null;
        if(sdcardIsAlready()) {
            sdDir = Environment.getExternalStorageDirectory().toString();
        }

        return sdDir;
    }

    public static File getSDPathFile() {
        String sDcString = Environment.getExternalStorageState();
        return sDcString.equals("mounted")?Environment.getExternalStorageDirectory():null;
    }

    public long getSDCardTotalSpace() {
        File pathFile = getSDPathFile();
        if(pathFile != null) {
            StatFs statfs = new StatFs(pathFile.getPath());
            long nBlocSize = (long)statfs.getBlockSize();
            long nTotalBlocks = (long)statfs.getBlockCount();
            return nBlocSize * nTotalBlocks;
        } else {
            return 0L;
        }
    }

    public static long getSDCardSurplusSpace() {
        File pathFile = getSDPathFile();
        if(pathFile != null) {
            StatFs statfs = new StatFs(pathFile.getPath());
            long nAvailaBlock = (long)statfs.getAvailableBlocks();
            long nBlocSize = (long)statfs.getBlockSize();
            return nBlocSize * nAvailaBlock;
        } else {
            return 0L;
        }
    }

    public static String getInternalMemoryPath() {
        return Environment.getDataDirectory().getPath();
    }

    public static String getExternalMemoryPath() {
        return "/mnt/sdcard";
    }

    public static String getSDCard2MemoryPath() {
        return "/mnt/sdcard1";
    }

    public static StatFs getStatFs(String path) {
        try {
            return new StatFs(path);
        } catch (Exception var2) {
            var2.printStackTrace();
            return null;
        }
    }

    public static float calculateSizeInMB(StatFs stat) {
        return stat != null?(float)stat.getAvailableBlocks() * ((float)stat.getBlockSize() / 1048576.0F):0.0F;
    }

    public static float getAvailableInternalMemorySize() {
        String path = getInternalMemoryPath();
        StatFs stat = getStatFs(path);
        return calculateSizeInMB(stat);
    }

    public static float getAvailableExternalMemorySize() {
        String path = getExternalMemoryPath();
        StatFs stat = getStatFs(path);
        return calculateSizeInMB(stat);
    }

    public static float getAvailableSDCard2MemorySize() {
        String path = getSDCard2MemoryPath();
        StatFs stat = getStatFs(path);
        return calculateSizeInMB(stat);
    }

    public static Bitmap getBitmapFromBytes(byte[] bitmapBytes) {
        if(bitmapBytes != null && bitmapBytes.length != 0) {
            Bitmap bitmap = BitmapFactory.decodeByteArray(bitmapBytes, 0, bitmapBytes.length);
            return bitmap;
        } else {
            return null;
        }
    }

    public static boolean saveBytes(File file, byte[] bytes) {
        boolean b = true;
        FileOutputStream fos = null;
        BufferedOutputStream bos = null;

        try {
            fos = new FileOutputStream(file);
            bos = new BufferedOutputStream(fos);
            bos.write(bytes);
            bos.flush();
        } catch (FileNotFoundException var16) {
            b = false;
            var16.printStackTrace();
        } catch (IOException var17) {
            b = false;
            var17.printStackTrace();
        } finally {
            try {
                if(bos != null) {
                    bos.close();
                }

                if(fos != null) {
                    fos.close();
                }
            } catch (IOException var15) {
                var15.printStackTrace();
            }

        }

        return b;
    }

    public static boolean saveBitmap(int quality, File file, Bitmap bitmap) {
        if(file == null) {
            return false;
        } else {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            bitmap.compress(CompressFormat.PNG, quality, baos);
            byte[] bitmapByteArray = baos.toByteArray();
            boolean b = true;
            FileOutputStream fos = null;
            BufferedOutputStream bos = null;

            try {
                fos = new FileOutputStream(file);
                bos = new BufferedOutputStream(fos);
                bos.write(bitmapByteArray);
                bos.flush();
            } catch (FileNotFoundException var19) {
                b = false;
                var19.printStackTrace();
            } catch (IOException var20) {
                b = false;
                var20.printStackTrace();
            } finally {
                try {
                    if(baos != null) {
                        baos.close();
                    }

                    if(bos != null) {
                        bos.close();
                    }

                    if(fos != null) {
                        fos.close();
                    }
                } catch (IOException var18) {
                    var18.printStackTrace();
                }

            }

            return b;
        }
    }

    public static boolean saveObject(File file, Object obj) {
        if(file == null) {
            return false;
        } else {
            FileOutputStream fos = null;
            ObjectOutputStream oos = null;
            boolean b = true;

            try {
                fos = new FileOutputStream(file);
                oos = new ObjectOutputStream(fos);
                oos.writeObject(obj);
                oos.flush();
            } catch (FileNotFoundException var16) {
                b = false;
                var16.printStackTrace();
            } catch (IOException var17) {
                b = false;
                var17.printStackTrace();
            } finally {
                try {
                    if(fos != null) {
                        fos.close();
                    }

                    if(oos != null) {
                        oos.close();
                    }
                } catch (IOException var15) {
                    var15.printStackTrace();
                }

            }

            return b;
        }
    }

    public static boolean saveData(File file, InputStream inputStream) {
        if(file == null) {
            return false;
        } else {
            BufferedInputStream bis = null;
            FileOutputStream fos = null;
            BufferedOutputStream bos = null;
            boolean b = true;
            byte[] buffer = new byte[8192];
            boolean var7 = false;

            try {
                bis = new BufferedInputStream(inputStream);
                fos = new FileOutputStream(file);
                bos = new BufferedOutputStream(fos);

                int len;
                while((len = bis.read(buffer)) != -1) {
                    bos.write(buffer, 0, len);
                }
            } catch (FileNotFoundException var19) {
                b = false;
                var19.printStackTrace();
            } catch (IOException var20) {
                b = false;
                var20.printStackTrace();
            } finally {
                try {
                    if(bos != null) {
                        bos.close();
                    }

                    if(fos != null) {
                        fos.close();
                    }

                    if(bis != null) {
                        bis.close();
                    }

                    if(inputStream != null) {
                        inputStream.close();
                    }
                } catch (IOException var18) {
                    var18.printStackTrace();
                }

            }

            return b;
        }
    }

    public static boolean saveData(File outputFile, File inputFile) {
        if(outputFile == null) {
            return false;
        } else {
            boolean b = true;

            try {
                InputStream fin = new FileInputStream(inputFile);
                b = saveData(outputFile, (InputStream)fin);
            } catch (FileNotFoundException var4) {
                b = false;
                var4.printStackTrace();
            }

            return b;
        }
    }

    public static Object getObject(InputStream os) {
        if(os == null) {
            return null;
        } else {
            ObjectInputStream ins = null;
            Object obj = null;

            try {
                ins = new ObjectInputStream(os);
                obj = ins.readObject();
            } catch (IOException var14) {
                var14.printStackTrace();
            } catch (ClassNotFoundException var15) {
                var15.printStackTrace();
            } finally {
                try {
                    if(ins != null) {
                        ins.close();
                    }

                    if(os != null) {
                        os.close();
                    }
                } catch (IOException var13) {
                    var13.printStackTrace();
                }

            }

            return obj;
        }
    }

    public static OutputStream getOutputStream(File file) {
        if(file == null) {
            return null;
        } else {
            FileOutputStream os = null;

            try {
                os = new FileOutputStream(file);
            } catch (FileNotFoundException var3) {
                var3.printStackTrace();
            }

            return os;
        }
    }

    public static boolean deleteFile(File file) {
        if(file != null && file.exists()) {
            file.delete();
            return true;
        } else {
            return false;
        }
    }

    public static boolean deleteFiles(File dirFile) {
        if(dirFile != null && dirFile.exists()) {
            File[] files = dirFile.listFiles();
            File[] var5 = files;
            int var4 = files.length;

            for(int var3 = 0; var3 < var4; ++var3) {
                File file = var5[var3];
                if(file.isFile()) {
                    file.delete();
                } else if(file.isDirectory()) {
                    deleteFiles(file);
                }
            }

            dirFile.delete();
            return true;
        } else {
            return false;
        }
    }

    public static boolean isSdcardReadable() {
        String state = Environment.getExternalStorageState();
        return "mounted_ro".equals(state) || "mounted".equals(state);
    }

    public static boolean isSdcardWritable() {
        String state = Environment.getExternalStorageState();
        return "mounted".equals(state);
    }
}
