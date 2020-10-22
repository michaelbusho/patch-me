import patch.Patcher;
import patch.implementation.PatcherImpl;

public class Main {
    public static void main(String[] args){
        System.out.println("Hello World from the patcher!"); // Display the string.
        patchRPM();
        //patch Thermometer
    }

    /**
     * Patches RPM
     */
    private static void patchRPM (){
        Patcher rpmPatch = new PatcherImpl();
        //1) Stop process to be patched
        //2) Download repo
        rpmPatch.downloadPatchCode("myrepo");
        //3) Build new jar package and replace old
        //4) delete repo
        //5) run patch process again
    }

}
