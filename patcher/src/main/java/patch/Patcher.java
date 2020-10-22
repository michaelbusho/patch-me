package patch;

public interface Patcher {
    /**
     *
     * @param name
     */
    void stopProcess(String name);

    /**
     *
     * @param name
     */
    void downloadPatchCode(String name);

    /**
     *
     */
    void buildPatch();

    /**
     *
     */
    void removeDownloadedFiles();
}
