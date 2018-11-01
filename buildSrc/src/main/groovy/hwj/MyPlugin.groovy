package hwj

import org.gradle.api.Plugin
import org.gradle.api.Project


class MyPlugin implements Plugin<Project> {

    final String BIN_GENERATOR_CMD = "solcjs \"%s\" --bin --abi --optimize --overwrite -o \"%s\""
    final String HOLDER_GENERATOR_CMD = "web3j solidity generate \"%s\" \"%s\" -o ../../java -p \"%s\""
    final String SOLIDITY_OUT_PATH = "src/main/solidity/build"
    final String CLASS_OUT_PATH = "src/main/java/contracts"
    final String FILE_PATH = "src/main/solidity/"

    @Override
    void apply(Project project) {

        project.extensions.add("myPlug", MyPluginExtension)


        project.beforeEvaluate {
            println "----------------------began----------------------"
            def solFile = new File(project.rootDir, FILE_PATH)

            solFile.eachFile { f ->
                if (f.name.endsWith(".sol")) {
                    def solPath = f.name.replace(".sol", "")
                    def holderCmd = String.format(BIN_GENERATOR_CMD, FILE_PATH + solPath + ".sol", SOLIDITY_OUT_PATH)
                    holderCmd = holderCmd + "\n" + String.format(HOLDER_GENERATOR_CMD, SOLIDITY_OUT_PATH + solPath + ".abi", SOLIDITY_OUT_PATH + solPath + ".bin", CLASS_OUT_PATH)
                    println "holderCmd=" + holderCmd
                    def sout = new StringBuilder(), serr = new StringBuilder()
                    def excuteFile = project.rootProject.file("temp_exe.bat")
                    excuteFile.write(holderCmd)

                    //设置成可执行文件
                    def p = ("chmod 777 " + excuteFile.getAbsolutePath()).execute()
                    p.consumeProcessOutput(sout, serr)
                    p.waitForOrKill(100000)

                    //执行文件
                    sout = new StringBuilder()
                    serr = new StringBuilder()
                    def proc = (excuteFile.getAbsolutePath()).execute()
                    proc.consumeProcessOutput(sout, serr)
                    proc.waitForOrKill(100000)
                    excuteFile.delete()
                }
            }
            println "----------------------end----------------------"
        }
    }
}