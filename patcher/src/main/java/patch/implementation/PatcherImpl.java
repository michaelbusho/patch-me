package patch.implementation;

import patch.Patcher;

import java.io.File;

public class PatcherImpl implements Patcher {
    Process process = null;


    @Override
    public void stopProcess(String name){

    }

    @Override
    public void downloadPatchCode(String name){
        try {
            //delete repo if exists
            StringBuilder sb = new StringBuilder();

            //For windows
            if (System.getProperty("os.name").contains("Windows")){
                sb.append("mkdir patch001");
                sb.append(" && cd patch001");
                sb.append(" && git clone https://github.com/michaelbusho/patch-me");
                sb.append(" && echo Code Downloaded");
            }else{
                sb.append("mkdir patch001");
                sb.append(" && cd patch001");
                sb.append(" && git clone https://github.com/michaelbusho/patch-me");
                sb.append(" && echo Code Downloaded");
            }

            ProcessBuilder pb = new ProcessBuilder("bash", "-c", sb.toString());
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
