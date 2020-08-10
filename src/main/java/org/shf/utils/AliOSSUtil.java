package org.shf.utils;

import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;

import java.io.InputStream;
import java.util.UUID;

public class AliOSSUtil {

    /**OSS上传文件
     *
     * @param is 文件输入流 InputStream
     * @param fileName 文件名带后缀
     * @return
     */
    public static String uploadFile(InputStream is, String fileName){

        //上传到的服务器所在地
        String endpoint = "oss-cn-beijing.aliyuncs.com";

        //账号
        String accessKeyId = "LTAI4FzguVgVpaiyiLrdhRhw";

        //密码
        String accessKeySecret = "qpsUIIS8Pout4Z5FNAYIwJNYmfY1F1";

        //上传到的文件夹
        String bucketName = "shf001";

        //存放文件的路径
        String addFilePath = "http://shf001.oss-cn-beijing.aliyuncs.com/";

        //实例化OSS对象，设置服务器地址、账号、密码
        OSS ossClient = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);

        //把文件重新命名防止重复
        String uuidFileName = UUID.randomUUID().toString()+fileName;
        // 上传文件流，
        ossClient.putObject(bucketName, uuidFileName, is);
        // 关闭OSSClient。
        ossClient.shutdown();
        //返回文件路径
        return addFilePath+uuidFileName;

    }

    /**
     *
     */
    public static void deleteFile(String fileName){
        //上传到的服务器所在地
        String endpoint = "oss-cn-beijing.aliyuncs.com";

        //账号
        String accessKeyId = "LTAI4FzguVgVpaiyiLrdhRhw";

        //密码
        String accessKeySecret = "qpsUIIS8Pout4Z5FNAYIwJNYmfY1F1";

        //实例化OSS对象，设置服务器地址、账号、密码
        OSS ossClient = new OSSClientBuilder().build(endpoint, accessKeyId, accessKeySecret);

        int i = fileName.lastIndexOf("/");
        String name = fileName.substring(i + 1);


        ossClient.deleteBucketCname("shf001",name);
    }


}
