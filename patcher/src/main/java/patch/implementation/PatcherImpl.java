package patch.implementation;

import patch.Patcher;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class PatcherImpl implements Patcher {
    Process process = null;


    @Override
    public void stopProcess(String name){

    }

    @Override
    public void downloadPatchCode(String name){
        try {
//            List<String> cmdList = new ArrayList<String>();
//            if (System.getProperty("os.name").contains("Windows")){
//                cmdList.add("ls");
//            }else{
//                cmdList.add("pwd");
//            }

            String[] command = {"git", "clone", "https://github.com/michaelbusho/patch-me"};
            ProcessBuilder pb = new ProcessBuilder(command);
            pb.inheritIO();
            pb.directory(new File(System.getProperty("user.home") +"/Desktop")); //Set current directory
//            pb.redirectError(new File(System.getProperty("user.home") +"/Desktop"));//Log errors in specified log file.
            process = pb.start();
            process.waitFor();

        } catch (Exception e) {
            e.printStackTrace();
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void buildPatch(){

    }

    @Override
    public void removeDownloadedFiles(){

    }
}
