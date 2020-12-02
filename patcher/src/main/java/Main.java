import patch.Patcher;
import patch.implementation.PatcherImpl;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import RPMReceiverInterface.RPMReceiverInterface;

public class Main {
    public static void main(String[] args){
        System.out.println("**** PATCHER ****");

        patchRPM("rpm-sensor-0.0.3");
//        patchThermometer("temp-sensor-0.0.1");
    }

    /**
     * Patches RPM
     */
    private static void patchRPM (String rpm_oldVersion){
        Patcher rpmPatch = new PatcherImpl();
        System.out.println("----- PATCHING RPM -----");
        try{
            //1) Stop process to be patched
            System.out.println("1)Stopping main RPM process...");
            Registry registry = LocateRegistry.getRegistry(RPMReceiverInterface.portNumber);
            RPMReceiverInterface checker = (RPMReceiverInterface) registry.lookup(RPMReceiverInterface.processName);
            Boolean status = checker.stopMainRpm();
            if(status){
                System.out.println("SECONDARY RPM PROCESS NOW ACTIVE - MAIN RPM PROCESS DOWN & READY TO BE PATCHED");
            }else{
                System.out.println("MAIN RPM PROCESS COULD NOT BE SHUT DOWN - RPM PATCHING SKIPPED");
            }

            //2) Apply patch
            System.out.println("2)Applying patch on main process...");
            rpmPatch.applyPatchCode(rpm_oldVersion);

            //3) TEST md hash
            System.out.println("3)TODO: Run RPM sanity checks");
            //5) run patch process again
//            System.out.println("4)SWITCHING TO ACTIVE & PATCHED PROCESS...");
//            Boolean restoration_status = checker.restoreMainRpm();
//            if(restoration_status){
//                System.out.println("MAIN PROCESS PATCHED AND RUNNING");
//            }else{
//                System.out.println("FAILED TO INITIATED MAIN PATCHED PROCESS");
//            }
            System.out.println("-------------------");
        }catch(Exception e){
            System.out.println(e);
            System.exit(1);
        }
    }

    /**
     * Patches RPM
     */
    private static void patchThermometer (String temp_oldVersion){
        System.out.println("----- PATCHING THERMOMETER -----");

        Patcher tempPatch = new PatcherImpl();
        //1) Stop process to be patched
        System.out.println("1)TODO: Stopping temp process...");
        //2) Apply patch
        System.out.println("2)Applying patch...");
        tempPatch.applyPatchCode(temp_oldVersion);
        //5) run patch process again
        System.out.println("3)TODO: Run patched process again...");
    }

}
