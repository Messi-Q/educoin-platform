package team.educoin.transaction.util;

import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * @description: 文件上传下载目录处理工具
 * @author: PandaClark
 * @create: 2019-05-21
 */
public class FileUtil {

    public static String UPLOAD_DIR;

    static {
        Path path = Paths.get("..","upload").toAbsolutePath().normalize();
        UPLOAD_DIR = path.toString();
        // System.out.println(UPLOAD_DIR);

        try {
            if (!Files.exists(path)){
                Files.createDirectory(path);
            }
        } catch (IOException e){
            e.printStackTrace();
        }
    }

    // 工具函数：文件大小形式化(例：convert 1024 to 1KB)
    public static String getFormatSize(long size) {
        double kiloByte = size / 1024;
        if (kiloByte < 1) {
            return size + "Byte(s)";
        }
        double megaByte = kiloByte / 1024;
        if (megaByte < 1) {
            BigDecimal result1 = new BigDecimal(Double.toString(kiloByte));
            return result1.setScale(2, BigDecimal.ROUND_HALF_UP).toPlainString() + "KB";
        }
        double gigaByte = megaByte / 1024;
        if (gigaByte < 1) {
            BigDecimal result2 = new BigDecimal(Double.toString(megaByte));
            return result2.setScale(2, BigDecimal.ROUND_HALF_UP).toPlainString() + "MB";
        }
        double teraBytes = gigaByte / 1024;
        if (teraBytes < 1) {
            BigDecimal result3 = new BigDecimal(Double.toString(gigaByte));
            return result3.setScale(2, BigDecimal.ROUND_HALF_UP).toPlainString() + "GB";
        }
        BigDecimal result4 = new BigDecimal(teraBytes);
        return result4.setScale(2, BigDecimal.ROUND_HALF_UP).toPlainString() + "TB";
    }

}
