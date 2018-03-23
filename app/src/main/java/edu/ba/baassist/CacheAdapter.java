package edu.ba.baassist;

import android.content.Context;
import android.util.Log;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * Adapter-class to interact with the internal memory to save the userdata.
 */

public class CacheAdapter {

    //Define context == our application.
    static Context fileContext;

    private static final String FILE_NOT_FOUND_MESSAGE = "Datei nicht vorhanden";

    //Store string to file
    private static void store(String content, String fileName) throws IOException {
        FileOutputStream outputStream;

        outputStream = fileContext.openFileOutput(fileName, Context.MODE_PRIVATE);
        outputStream.write(content.getBytes());
        outputStream.close();
        Log.d(CacheAdapter.class.getSimpleName(), "Successfully wrote to: " + fileName);
    }

    //Save calender to memory.
    static void saveCaltoMem(String cal) throws IOException {store(cal, "userCal");}

    //Save semester to memory.
    static void saveFstoMem(String fs) throws IOException {store(fs, "userFs");}

    //Save credits to memory.
    static void saveCredittoMem(String credits) throws IOException {store(credits, "userCredits");}

    //Save exams to memory.
    static void saveExamstoMem(String exams) throws IOException {store(exams, "userExams");}

    //Save user id to memory.
    static void saveUserGlobaltoMem(String user) throws IOException {store(user, "userGlobal");}

    //Save the hash to internal memory.
    static void saveHashGlobaltoMem(String hash) throws IOException {store(hash, "HashGlobal");}

    //Save the GroupFilter to internal memory.
    public static void saveFiltertoMem(String filter) throws IOException {store(filter, "userFilter");}

    //Check if a file exists.
    public static boolean getFileExistence(String fileName) {
        File file = fileContext.getFileStreamPath(fileName);
        if (!file.exists()) {
            Log.w(CacheAdapter.class.getSimpleName(), "File not found: " + fileName);
            return false;
        }

        return true;
    }

    private static String getFileContent(String fileName) {
        if (!getFileExistence(fileName))
            return FILE_NOT_FOUND_MESSAGE;

        String output;

        try {
            FileInputStream fis = fileContext.openFileInput(fileName);
            InputStreamReader isr = new InputStreamReader(fis);
            BufferedReader bufferedReader = new BufferedReader(isr);
            StringBuilder sb = new StringBuilder();
            String line;

            while ((line = bufferedReader.readLine()) != null) {
                sb.append(line);
            }
            output = sb.toString();
        }

        catch (IOException e) {
            Log.e(CacheAdapter.class.getSimpleName(), "Could not get content from: " + fileName, e);
            output = e.toString();
        }

        return output;
    }

    //Method to get the calender from memory.
    static String getCalfromMem() {return getFileContent("userCal");}

    //Method to get the semester from memory.
    static String getFsfromMem() {return getFileContent("userFs");}

    //Method to get the credits from memory.
    static String getCreditsfromMem() {return getFileContent("userCredits");}

    //Method to get exam status from memory.
    static String getExamsfromMem() {return getFileContent("userExams");}

    //Method to get the user id from memory.
    static String getUserGlobalfromMem() {return getFileContent("userGlobal");}

    //Get the global hash of the User from memory.
    static String getHashGlobalfromMem() {return getFileContent("HashGlobal");}

    //Method to get the Groupfilter from memory.
    public static String getFilterfromMem() {return getFileContent("userFilter");}

    //Method to check if the data is up to date and replace it with newer one if given.
    public static String checkDiff(String input, String cname) throws IOException {
        String oldContent = getFileContent(cname);

        if (oldContent.equals(FILE_NOT_FOUND_MESSAGE))
            return FILE_NOT_FOUND_MESSAGE;

        if (oldContent.equals(input)) {
            Log.d(CacheAdapter.class.getSimpleName(), "File '" + cname + "' is up to date");
            return "Kein Unterschied.";
        }

        fileContext.deleteFile(cname);
        store(input, cname);

        return "Datei überschrieben.";
    }

    //Delete an entry in the cache.
    public static void deleteEntry(String input){fileContext.deleteFile(input);}
}
