import patch.Patcher;
import patch.implementation.PatcherImpl;

public class Main {
    public static void main(String[] args){
        System.out.println("--Hello World from the patcher!--\nStarting patching the rpm process:");
        patchRPM();
        patchThermometer();
    }

    /**
     * Patches RPM
     */
    private static void patchRPM (){
        Patcher rpmPatch = new PatcherImpl();
        //1) Stop process to be patched
        System.out.println("1)TODO: Stopping rpm process...");
        //2) Apply patch
        System.out.println("2)Applying patch...");
        rpmPatch.applyPatchCode("rpm-sensor-0.0.1");
        //5) run patch process again
        System.out.println("3)TODO: Run patched process again...");
    }

    /**
     * Patches RPM
     */
    private static void patchThermometer (){
        Patcher tempPatch = new PatcherImpl();
        //1) Stop process to be patched
        System.out.println("1)TODO: Stopping temp process...");
        //2) Apply patch
        System.out.println("2)Applying patch...");
        tempPatch.applyPatchCode("temp-sensor-0.0.1");
        //5) run patch process again
        System.out.println("3)TODO: Run patched process again...");
    }

}
