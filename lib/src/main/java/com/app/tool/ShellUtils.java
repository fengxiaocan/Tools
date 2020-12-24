package com.app.tool;

import java.io.DataOutputStream;
import java.util.List;

class ShellUtils {

    /**
     * 检查是否已经root
     */
    public static boolean checkRootPermission() {
        return execCmd("echo root", true) == 0;
    }

    /**
     * 是否是在root下执行命令
     *
     * @param command 命令
     * @param isRoot  是否需要root权限执行
     * @return CommandResult
     */
    public static int execCmd(String command, boolean isRoot) {
        return execCmd(new String[]{command}, isRoot);
    }

    /**
     * 是否是在root下执行命令
     *
     * @param commands 命令链表
     * @param isRoot   是否需要root权限执行
     * @return CommandResult
     */
    public static int execCmd(List<String> commands, boolean isRoot) {
        return execCmd(commands == null ? null : commands.toArray(new String[]{}), isRoot);
    }

    /**
     * 是否是在root下执行命令
     *
     * @param commands 命令数组
     * @param isRoot   是否需要root权限执行
     * @return CommandResult
     */
    public static int execCmd(String[] commands, boolean isRoot) {
        if (commands == null || commands.length == 0) {
            return -1;
        }
        Process process = null;
        DataOutputStream os = null;
        try {
            process = Runtime.getRuntime().exec(isRoot ? "su" : "sh");
            os = new DataOutputStream(process.getOutputStream());
            for (String command : commands) {
                if (command == null)
                    continue;
                os.write(command.getBytes());
                os.writeBytes("\n");
                os.flush();
            }
            os.writeBytes("exit\n");
            os.flush();
            return process.waitFor();
        } catch (Exception e) {
            e.printStackTrace();
            return -1;
        } finally {
            CloseUtils.closeIO(os);
            if (process != null) {
                process.destroy();
            }
        }
    }
}