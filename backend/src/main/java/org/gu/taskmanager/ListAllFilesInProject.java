package org.gu.taskmanager;

import java.io.File;

public class ListAllFilesInProject {
    public static void main(String[] args) {
        // 指定專案的根目錄 (可更改成專案路徑)
        File projectRoot = new File(".");

        System.out.println("專案中的所有檔案和文件 (跳過 node_modules 資料夾)：");
        listFilesRecursively(projectRoot, "");
    }

    // 遞迴列出檔案和文件的輔助方法
    public static void listFilesRecursively(File directory, String indent) {
        // 確認資料夾存在且可讀取
        if (directory.exists() && directory.canRead()) {
            File[] files = directory.listFiles();
            if (files != null) {
                for (File file : files) {
                    // 如果是 node_modules 資料夾，跳過
                    if (file.isDirectory() && file.getName().equals("node_modules")) {
                        System.out.println(indent + "跳過資料夾：" + file.getName());
                        continue;
                    }

                    // 檢查是檔案或資料夾並列出名稱
                    if (file.isFile()) {
                        System.out.println(indent + "檔案：" + file.getName());
                    } else if (file.isDirectory()) {
                        System.out.println(indent + "資料夾：" + file.getName());
                        // 遞迴列出子資料夾的內容
                        listFilesRecursively(file, indent + "    ");
                    }
                }
            }
        } else {
            System.out.println("無法訪問資料夾：" + directory.getPath());
        }
    }
}
