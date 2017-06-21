package edu.ba.baassist;

import android.content.Context;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * Adapter class to interact with the internal memory to save the userdata.
 */

public class cacheAdapter{

    public static Context fileContext;


    //Save calender to memory.


    public boolean saveCaltoMem(String cal){

        String filename = "userCal";

        FileOutputStream outputStream;

        try {
            outputStream = fileContext.openFileOutput(filename, Context.MODE_PRIVATE);
            outputStream.write(cal.getBytes());
            outputStream.close();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    //save semester to memory.
    public boolean saveFstoMem(String Fs){

        String filename = "userFs";

        FileOutputStream outputStream;

        try {
            outputStream = fileContext.openFileOutput(filename, Context.MODE_PRIVATE);
            outputStream.write(Fs.getBytes());
            outputStream.close();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    //save credits to memory.
    public boolean saveCredittoMem(String Credits){

        String filename = "userCredits";

        FileOutputStream outputStream;

        try {
            outputStream = fileContext.openFileOutput(filename, Context.MODE_PRIVATE);
            outputStream.write(Credits.getBytes());
            outputStream.close();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    //Save exams to memory.
    public boolean saveExamstoMem(String Exams){

        String filename = "userExams";

        FileOutputStream outputStream;

        try {
            outputStream = fileContext.openFileOutput(filename, Context.MODE_PRIVATE);
            outputStream.write(Exams.getBytes());
            outputStream.close();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    //Save user id to memory.
    public boolean saveUserGlobaltoMem(String user){

        String filename = "userGlobal";

        FileOutputStream outputStream;

        try {
            outputStream = fileContext.openFileOutput(filename, Context.MODE_PRIVATE);
            outputStream.write(user.getBytes());
            outputStream.close();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    //Save hash to memory.
    public boolean saveHashGlobaltoMem(String hash){

        String filename = "HashGlobal";

        FileOutputStream outputStream;

        try {
            outputStream = fileContext.openFileOutput(filename, Context.MODE_PRIVATE);
            outputStream.write(hash.getBytes());
            outputStream.close();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean getFileExistence(String fileName){
        File file = fileContext.getFileStreamPath(fileName);
        return file.exists();
    }

    //Method to get the calender from memory.
    public String getCalfromMem() throws FileNotFoundException {
       
        if (getFileExistence("userCal")){
           
           String filename = "userCal";
           FileInputStream fis;
           fis = fileContext.openFileInput(filename);
           InputStreamReader isr = new InputStreamReader(fis);
           BufferedReader bufferedReader = new BufferedReader(isr);
           StringBuilder sb = new StringBuilder();
           String line;
           String output;
           try {
                while ((line = bufferedReader.readLine()) != null) {
                    sb.append(line);
                }
                    output =sb.toString();
                } catch (IOException e) {
                    e.printStackTrace();
                    output=e.toString();
                }
                return output;
            
                }else {
                    return "Datei nicht vorhanden.";   
                }
    }

    //Method to get the semester from memory.
    public String getFsfromMem() throws FileNotFoundException {

        if (getFileExistence("userFs")){

            String filename = "userFs";
            FileInputStream fis;
            fis = fileContext.openFileInput(filename);
            InputStreamReader isr = new InputStreamReader(fis);
            BufferedReader bufferedReader = new BufferedReader(isr);
            StringBuilder sb = new StringBuilder();
            String line;
            String output;
            try {
                while ((line = bufferedReader.readLine()) != null) {
                    sb.append(line);
                }
                output =sb.toString();
            } catch (IOException e) {
                e.printStackTrace();
                output=e.toString();
            }
            return output;

        }else {
            return "Datei nicht vorhanden.";
        }
    }

    //Method to get the credits from memory.
    public String getCreditsfromMem() throws FileNotFoundException {

        if (getFileExistence("userCredits")){

            String filename = "userCredits";
            FileInputStream fis;
            fis = fileContext.openFileInput(filename);
            InputStreamReader isr = new InputStreamReader(fis);
            BufferedReader bufferedReader = new BufferedReader(isr);
            StringBuilder sb = new StringBuilder();
            String line;
            String output;
            try {
                while ((line = bufferedReader.readLine()) != null) {
                    sb.append(line);
                }
                output =sb.toString();
            } catch (IOException e) {
                e.printStackTrace();
                output=e.toString();
            }
            return output;

        }else {
            return "Datei nicht vorhanden.";
        }
    }

    //Method to get exam status from memory.
    public String getExamsfromMem() throws FileNotFoundException {

        if (getFileExistence("userExams")){

            String filename = "userExams";
            FileInputStream fis;
            fis = fileContext.openFileInput(filename);
            InputStreamReader isr = new InputStreamReader(fis);
            BufferedReader bufferedReader = new BufferedReader(isr);
            StringBuilder sb = new StringBuilder();
            String line;
            String output;
            try {
                while ((line = bufferedReader.readLine()) != null) {
                    sb.append(line);
                }
                output =sb.toString();
            } catch (IOException e) {
                e.printStackTrace();
                output=e.toString();
            }
            return output;

        }else {
            return "Datei nicht vorhanden.";
        }
    }

    //Method to get the user id from memory.
    public String getUserGlobalfromMem() throws FileNotFoundException {

        if (getFileExistence("userGlobal")){

            String filename = "userGlobal";
            FileInputStream fis;
            fis = fileContext.openFileInput(filename);
            InputStreamReader isr = new InputStreamReader(fis);
            BufferedReader bufferedReader = new BufferedReader(isr);
            StringBuilder sb = new StringBuilder();
            String line;
            String output;
            try {
                while ((line = bufferedReader.readLine()) != null) {
                    sb.append(line);
                }
                output =sb.toString();
            } catch (IOException e) {
                e.printStackTrace();
                output=e.toString();
            }
            return output;

        }else {
            return "Datei nicht vorhanden.";
        }
    }



    //method to check if the data is up to date and replace it with newer one if given.
    public String checkdiff(String input, String Cname) throws FileNotFoundException {
        if (getFileExistence(Cname)){

            FileOutputStream outputStream;
            FileInputStream inputStream;

            inputStream= fileContext.openFileInput(Cname);
            InputStreamReader isr = new InputStreamReader(inputStream);
            BufferedReader bufferedReader = new BufferedReader(isr);
            StringBuilder sb = new StringBuilder();
            String line;
            String output;
            try {
                while ((line = bufferedReader.readLine()) != null) {
                    sb.append(line);
                }
                output =sb.toString();
            } catch (IOException e) {
                e.printStackTrace();
                output=e.toString();
            }

            if (output.equals(input)){

                return "Kein Unterschied.";

            }else {
                fileContext.deleteFile(Cname);
                try {
                    outputStream = fileContext.openFileOutput(Cname, Context.MODE_PRIVATE);
                    outputStream.write(input.getBytes());
                    outputStream.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                return "Datei überschrieben.";
            }

        }else{

            return "File nicht gefunden.";

        }

    }

    //get the global hash of the User from memory.
    public String getHashGlobalfromMem() throws FileNotFoundException {

        if (getFileExistence("HashGlobal")){

            String filename = "HashGlobal";
            FileInputStream fis;
            fis = fileContext.openFileInput(filename);
            InputStreamReader isr = new InputStreamReader(fis);
            BufferedReader bufferedReader = new BufferedReader(isr);
            StringBuilder sb = new StringBuilder();
            String line;
            String output;
            try {
                while ((line = bufferedReader.readLine()) != null) {
                    sb.append(line);
                }
                output =sb.toString();
            } catch (IOException e) {
                e.printStackTrace();
                output=e.toString();
            }
            return output;

        }else {

            return "Datei nicht vorhanden.";
        }

    }

    public boolean deleteEntry(String input){
        if (getFileExistence(input)){

            fileContext.deleteFile(input);
            return true;

        }else {
            return false;
        }
    }
}
