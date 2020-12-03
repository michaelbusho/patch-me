package patch;

public interface Patcher {
    /**
     * Stops the process to be patched
     * @param name - the process id
     */
    void stopProcess(String name);

    /**
     * Downloads the updated code from the repo
     * @param name - The repo's name
     */
    void applyPatchCode(String name);

    /**
     * Checks MD5 SUM of patched jar
     * @param name
     * @return
     */
    public Boolean checkSum(String name);
}
