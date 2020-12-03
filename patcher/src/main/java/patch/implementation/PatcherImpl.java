package patch.implementation;

import patch.Patcher;
import utils.ConsoleColors;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;
import java.nio.file.Path;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;

public class PatcherImpl implements Patcher {
    Process process = null;


    @Override
    public void stopProcess(String name){

    }

    @Override
    public void applyPatchCode(String name){
        try {
            File outputFile =getOutputFile();
            Path outputPath = outputFile.toPath();

            Files.copy(getPatchFile(name), outputPath, StandardCopyOption.REPLACE_EXISTING);

            ProcessBuilder pb = new ProcessBuilder("bspatch", "bin/"+name+".jar","bin/"+name+".jar", "new.patch");
            pb.inheritIO();

//            pb.directory(new File(System.getProperty("user.dir"))); //Set current directory
//            pb.redirectError(new File(System.getProperty("user.dir")));//Log errors in specified log file.
            process = pb.start();
            process.waitFor();
            outputFile.delete();
            System.out.println( ConsoleColors.CYAN + "PROCESS PATCHED" + ConsoleColors.RESET);
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println(e.getMessage());
        }
    }

    private File getOutputFile(){
        final String dir = System.getProperty("user.dir");
        File file = new File(dir +"/new.patch");
        file.setWritable(true);
        file.setReadable(true);
        return file;
    }

    private InputStream getPatchFile(String name){
        ClassLoader classloader = Thread.currentThread().getContextClassLoader();
        InputStream input = classloader.getResourceAsStream(name+".patch");
        return input;
    }

    @Override
    public Boolean checkSum(String name){
        ProcessBuilder pb = new ProcessBuilder("MD5", "-q", "bin/"+name+".jar");
        try{
            process = pb.start();
            process.waitFor();
            BufferedReader br=new BufferedReader(new InputStreamReader(process.getInputStream()));
            String line;
            StringBuilder sb = new StringBuilder();
            while((line=br.readLine())!=null) sb.append(line);
            System.out.println("MD5 - "+sb.toString());
        }catch(Exception e){
            System.out.println(e);
            return false;
        }
        return true;
    }
}
