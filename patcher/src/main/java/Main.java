import patch.Patcher;
import patch.implementation.PatcherImpl;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import RPMReceiverInterface.RPMReceiverInterface;
import utils.ConsoleColors;

public class Main {
    public static void main(String[] args){
        System.out.println("**** PATCHER ****");

        patchRPM("rpm-sensor-0.0.4");
//        patchThermometer("temp-sensor-0.0.4");
    }

    /**
     * Patches RPM
     */
    private static void patchRPM (String rpm_oldVersion){
        Patcher rpmPatch = new PatcherImpl();
        System.out.println(ConsoleColors.BLUE + "----- PATCHING RPM -----" + ConsoleColors.RESET);
        try{
            //1) Stop process to be patched
            System.out.println("1) Stopping main RPM process...");
            Registry registry = LocateRegistry.getRegistry(RPMReceiverInterface.portNumber);
            RPMReceiverInterface checker = (RPMReceiverInterface) registry.lookup(RPMReceiverInterface.processName);
            Boolean status = checker.stopMainRpm();
            if(status){
                System.out.println( ConsoleColors.CYAN + "SECONDARY RPM PROCESS NOW ACTIVE - MAIN RPM PROCESS DOWN & READY TO BE PATCHED" + ConsoleColors.RESET);
            }else{
                System.out.println(ConsoleColors.RED +"MAIN RPM PROCESS COULD NOT BE SHUT DOWN - RPM PATCHING SKIPPED"+ ConsoleColors.RESET);
            }

            //2) Apply patch
            System.out.println("2) Applying patch on main RPM process...");
            rpmPatch.applyPatchCode(rpm_oldVersion);

            //3) TEST md hash
            System.out.println("3) Run RPM sanity checks");
            Boolean sanity = rpmPatch.checkSum(rpm_oldVersion);
            if(sanity){
                System.out.println( ConsoleColors.CYAN + "INTEGRITY VERIFIED" + ConsoleColors.RESET);
            }else{
                System.out.println(ConsoleColors.RED +"Could not verify integrity"+ ConsoleColors.RESET);
            }
            System.out.println(ConsoleColors.WHITE_BOLD +"--------  RPM PROCESS PATCHED AND READY  -----------"+ ConsoleColors.RESET);
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
