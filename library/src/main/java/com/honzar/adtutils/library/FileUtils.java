package com.honzar.adtutils.library;

import android.content.Context;
import android.os.Environment;
import android.util.Log;

import com.google.common.collect.ImmutableMap;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;

import cz.mafra.jizdnirady.lib.base.ApiDataIO.ApiClassVersionsMapCreator;
import cz.mafra.jizdnirady.lib.base.ApiDataIO.ApiDataInput;
import cz.mafra.jizdnirady.lib.base.ApiDataIO.ApiDataInputOutputBase;
import cz.mafra.jizdnirady.lib.base.ApiDataIO.ApiDataInputStreamWrp;
import cz.mafra.jizdnirady.lib.base.ApiDataIO.ApiDataOutput;
import cz.mafra.jizdnirady.lib.base.ApiDataIO.ApiDataOutputStreamWrp;

public class FileUtils {
    private static final String TAG = FileUtils.class.getSimpleName();

    private static final String TMP_FILE_POSTFIX = "~tmp";

    /* Checks if external storage is available for read and write */
    public static boolean isExternalStorageWritable() {
        String state = Environment.getExternalStorageState();
        return Environment.MEDIA_MOUNTED.equals(state);
    }

    /* Checks if external storage is available to at least read */
    public static boolean isExternalStorageReadable() {
        String state = Environment.getExternalStorageState();
        return Environment.MEDIA_MOUNTED.equals(state) ||
                Environment.MEDIA_MOUNTED_READ_ONLY.equals(state);
    }


    public static boolean readObjsFromFile(Context context, FileObjsStaticInfo info, ReadObjsCallback callback) {
        synchronized (info.getLock()) {
            String fileName = info.getFileName();
            ApiDataInputStreamWrp stream = null;
            try {
                if (!context.getFileStreamPath(fileName).exists()) {
                    fileName += TMP_FILE_POSTFIX; // Pokud dany soubor neexistuje, tak se podivame, jestli neexistuje soubor s priponou ~tmp a pripadne jej zkusime nacist

                    if (!context.getFileStreamPath(fileName).exists()) {
                        callback.setDefaults();
                        return false;
                    }
                }

                stream = new ApiDataInputStreamWrp(new DataInputStream(new BufferedInputStream(context.openFileInput(fileName))), info, info.getCustomFlags());
                if (!info.canReadFile(stream.getDataVersion())) {
                    callback.setDefaults();
                    stream.close();
                    stream = null;
                    context.deleteFile(fileName);
                    return false;
                }
                else {
                    callback.readObjects(stream);
                    return true;
                }
            }
            catch (Exception e) {
                Log.e(TAG, "Exception while reading " + fileName, e);
                callback.setDefaults();
                return false;
            }
            finally {
                if (stream != null)
                    stream.close();
            }
        }
    }

    public static boolean writeObjsToFile(Context context, FileObjsStaticInfo info, WriteObjsCallback callback) {
        final boolean ret;
        synchronized (info.getLock()) {
            ApiDataOutputStreamWrp stream = null;
            String fileNameTmp = info.getFileName() + TMP_FILE_POSTFIX;
            try {
                stream = new ApiDataOutputStreamWrp(new DataOutputStream(new BufferedOutputStream(
                        context.openFileOutput(fileNameTmp, Context.MODE_PRIVATE))), info.getDataVersion());
                callback.writeObjects(stream);
            }
            catch (Exception e) {
                Log.e(TAG, "Exception while writing " + info.getFileName(), e);
                return false;
            }
            finally {
                if (stream != null)
                    stream.close();
            }

            File tmpFile = context.getFileStreamPath(fileNameTmp);
            File origFile = context.getFileStreamPath(info.getFileName());

            if (origFile.exists())
                origFile.delete();
            ret = tmpFile.renameTo(origFile);
        }
        return ret;
    }

    public static void writeObjsToFileAsync(final Context context, final FileObjsStaticInfo info, final WriteObjsCallback callback) {
        Thread t = new Thread(new Runnable() {
            public void run() {
                writeObjsToFile(context, info, callback);
            }
        });
        t.start();
    }

    public static abstract class FileObjsStaticInfo implements ApiClassVersionsMapCreator {
        private final Object lock;
        private final String fileName;
        private final int dataVersion;
        private final int minReadDataVersion; // Tento rozsah nemusi obsahovat samotnou dataVersion - pokud je stejna verze nacitaneho souboru jako dataVersion, tak se vzdycky nacita...
        private final int maxReadDataVersion;
        private final int customFlags;

        public FileObjsStaticInfo(Object lock, String fileName, int dataVersion) {
            this(lock, fileName, dataVersion, Integer.MIN_VALUE, dataVersion);
        }

        public FileObjsStaticInfo(Object lock, String fileName, int dataVersion, int minReadDataVersion, int maxReadDataVersion) {
            this(lock, fileName, dataVersion, minReadDataVersion, maxReadDataVersion, ApiDataInputOutputBase.FLAG_NONE);
        }

        public FileObjsStaticInfo(Object lock, String fileName, int dataVersion, int minReadDataVersion, int maxReadDataVersion, int customFlags) {
            this.lock = lock;
            this.fileName = fileName;
            this.dataVersion = dataVersion;
            this.minReadDataVersion = minReadDataVersion;
            this.maxReadDataVersion = maxReadDataVersion;
            this.customFlags = customFlags;
        }

        public Object getLock() {
            return this.lock;
        }

        public String getFileName() {
            return this.fileName;
        }

        public int getDataVersion() {
            return this.dataVersion;
        }

        public int getCustomFlags() {
            return customFlags;
        }

        public boolean canReadFile(int fileDataVersion) {
            return fileDataVersion == dataVersion
                    || (fileDataVersion >= minReadDataVersion && fileDataVersion <= maxReadDataVersion);
        }


        public FileObjsStaticInfo createPortableInfoForWriting() {
            return new FileObjsStaticInfo(getLock(), getFileName() + ".port", getDataVersion(), minReadDataVersion, maxReadDataVersion, customFlags | ApiDataInputOutputBase.FLAG_PORTABLE) {
                @Override
                public ImmutableMap<String, Integer> createClassVersionsMap(int dataVersion) {
                    return ImmutableMap.of();
                }
            };
        }
    }

    public static class FileObjsStaticInfoDiscardLegacy extends FileObjsStaticInfo {
        public FileObjsStaticInfoDiscardLegacy(Object lock, String fileName, int dataVersion) {
            super(lock, fileName, dataVersion, dataVersion, dataVersion);
        }

        @Override
        public ImmutableMap<String, Integer> createClassVersionsMap(int dataVersion) {
            return ImmutableMap.of();
        }
    }

    public interface ReadObjsCallback {
        void readObjects(ApiDataInput d);
        void setDefaults();
    }

    public interface WriteObjsCallback {
        void writeObjects(ApiDataOutput d);
    }

    public interface FileObjsCallback extends WriteObjsCallback, ReadObjsCallback {

    }
}
