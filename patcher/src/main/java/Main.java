import patch.Patcher;
import patch.implementation.PatcherImpl;

public class Main {
    public static void main(String[] args){
        System.out.println("--Hello World from the patcher!--\nStarting patching the rpm process:");
        patchRPM();
//        patchThermometer();
    }

    /**
     * Patches RPM
     */
    private static void patchRPM (){
        Patcher rpmPatch = new PatcherImpl();
        //1) Stop process to be patched
//        System.out.println("1)Stopping rpm process...");
        //2) Download repo
        System.out.println("2)Getting patch...");
        rpmPatch.downloadPatchCode("myrepo");
        //3) Build new jar package and replace old
//        System.out.println("3)Building new package and replacing old");
        //4) delete repo
//        System.out.println("4)Clean up...");
        //5) run patch process again
//        System.out.println("5)Run patched process again...");
    }

    /**
     * Patches RPM
     */
    private static void patchThermometer (){
        //1) Stop process to be patched
        System.out.println("1)Stopping thermometer process...");
        //2) Download repo
        System.out.println("2)Getting patch...");
        //3) Build new jar package and replace old
        System.out.println("3)Building new package and replacing old");
        //4) delete repo
        System.out.println("4)Clean up...");
        //5) run patch process again
        System.out.println("5)Run patched process again...");
    }

}
