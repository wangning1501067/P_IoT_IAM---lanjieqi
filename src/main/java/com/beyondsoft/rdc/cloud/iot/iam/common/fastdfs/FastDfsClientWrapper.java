package com.beyondsoft.rdc.cloud.iot.iam.common.fastdfs;

import com.github.tobato.fastdfs.domain.MataData;
import com.github.tobato.fastdfs.domain.StorePath;
import com.github.tobato.fastdfs.exception.FdfsUnsupportStorePathException;
import com.github.tobato.fastdfs.proto.storage.DownloadByteArray;
import com.github.tobato.fastdfs.service.FastFileStorageClient;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.Charset;
import java.security.NoSuchAlgorithmException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


/**
 * fastDFS工具类.
 *
 * @author chencong
 * @version 1.0
 * @date 19-2-26
 */
@Slf4j
@Component
public class FastDfsClientWrapper {

    /**
     * storageClient 注入
     */
    private FastFileStorageClient storageClient;

    @Value("${fdfs.fastdfs-server-address}")
    private String fastdfsServerAddress;
    @Value("${fdfs.fastdfs-server-port}")
    private Integer fastdfsServerPort;


    @Autowired
    public FastDfsClientWrapper(FastFileStorageClient storageClient) {
        this.storageClient = storageClient;
    }

    /**
     * 将一段字符串生成一个文件上传
     *
     * @param content       文件内容
     * @param fileExtension 扩展名
     * @return 路径
     */
    public String uploadTextToFile(String content, String fileExtension) {
        byte[] buff = content.getBytes(Charset.forName("UTF-8"));
        ByteArrayInputStream stream = new ByteArrayInputStream(buff);
        StorePath storePath = storageClient.uploadFile(stream, buff.length, fileExtension, null);
        return storePath.toString();
    }

    /**
     * 上传文件
     *
     * @param file 文件
     */
    public String uploadFile(MultipartFile file)  {
        StorePath path = new StorePath();
        InputStream inputStream =null;
        try {
            inputStream = file.getInputStream();
            path = storageClient.uploadFile(inputStream, file.getSize(),
                    FilenameUtils.getExtension(file.getOriginalFilename()), null);
            log.debug("========== 上传文件路径{}", path);
        } catch (IOException e) {
            log.error(e.getMessage());
        }finally {
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return fileHttpFullPath(path.getFullPath());
    }

    /**
     * 上传图片，并且生成缩略图
     *
     * @param file 文件
     */
    public String uploadImageAndCrtThumbImage(MultipartFile file) throws IOException {
        StorePath path = new StorePath();
        InputStream inputStream = null;
        try {
            inputStream = file.getInputStream();
            path = storageClient.uploadImageAndCrtThumbImage(inputStream, file.getSize(),
                    FilenameUtils.getExtension(file.getOriginalFilename()), null);
            log.debug("========== 上传文件路径{}", path);
        } catch (IOException e) {
            log.error(e.getMessage());
        } finally {
            if (inputStream != null) {
                inputStream.close();
            }
        }
        return fileHttpFullPath(path.getFullPath());
    }

    /**
     * 删除文件
     *
     * @param fileUrl 文件访问地址
     */
    public void deleteFile(String fileUrl) {
        if (StringUtils.isEmpty(fileUrl)) {
            return;
        }
        try {
            String[] url = fileUrl.replace("http://" + fastdfsServerAddress + ":" + fastdfsServerPort + "/", "").split("[?]");
            StorePath storePath = StorePath.praseFromUrl(url[0]);
            storageClient.deleteFile(storePath.getGroup(), storePath.getPath());
        } catch (FdfsUnsupportStorePathException e) {
            log.error(e.getMessage());
        }
    }

    /**
     * 获取文件的相对路径
     *
     * @param fileUrl
     * @return
     */
    public String getRelativeFilePath(String fileUrl) {
        if (StringUtils.isEmpty(fileUrl)) {
            return null;
        }
        String[] url = fileUrl.replace("http://" + fastdfsServerAddress + ":" + fastdfsServerPort + "/", "").split("[?]");
        return url[0];
    }

    /**
     * 设置 mata data 信息
     *
     * @return metaDataSet
     */
    private Set<MataData> createMataData(List<MataData> mataData) {
        return new HashSet<>(mataData);
    }

    /**
     * 文件路径
     *
     * @param path 全路径
     * @return 网络路径
     */
    private String fileWebPath(StorePath path) {
        return path.getGroup() + "/" + path.getPath();
    }

    /**
     * 拼接http预览路径
     *
     * @param storagePath
     * @return
     */
    public String fileHttpFullPath(String storagePath) {
        //路径处理（截取路进：/group1/M00/00/12/wKgHjl01a-mAX_mxAAggVMgnpCc808.jpg）
        //storagePath = pathHandle(storagePath);

        if (StringUtils.isEmpty(storagePath)) {
            return null;
        }
        int ts = (int) (System.currentTimeMillis() / 1000);
        String token = null;
        try {
            token = FastdfsWrapperUtils.getToken(storagePath, ts);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return "http://" + fastdfsServerAddress + ":" + fastdfsServerPort + "/" + storagePath + "?token=" + token + "&ts=" + ts;
    }

    /**
     * 路径处理（截取路进：/group1/M00/00/12/wKgHjl01a-mAX_mxAAggVMgnpCc808.jpg）
     *
     * @param storagePath
     * @return
     */
    public String pathHandle(String storagePath) {
        String str = "http://" + fastdfsServerAddress + ":" + fastdfsServerPort + "/";
        log.debug("==拼接http预览路径==>str:{}", str);
        if (storagePath.startsWith(str)) {
            storagePath = StringUtils.remove(storagePath, str);
        }
        log.debug("==拼接http预览路径==>storagePath:{}", storagePath);
        String sp = "&";
        storagePath = storagePath.replace("?", sp);
        if (storagePath.indexOf(sp) > 0) {
            String[] splitArray = storagePath.split(sp);
            storagePath = splitArray[0];
        }
        log.debug("==拼接http预览路径==>storagePath:{}", storagePath);
        return storagePath;
    }

    /**
     * 文件下载
     *
     * @param filePath fastDFS文件存储路径
     * @param response HttpServletResponse
     * @param fileName 指定下载后的文件名称
     * @throws IOException
     */
    public void downloadFile(String filePath, HttpServletResponse response, String fileName) throws IOException {
        StorePath storePath = StorePath.praseFromUrl(filePath);
        byte[] bytes = storageClient.downloadFile(storePath.getGroup(), storePath.getPath(), new DownloadByteArray());
        response.reset();
        response.setContentType("applicatoin/octet-stream");
        response.setHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode(fileName, "UTF-8"));
        ServletOutputStream out = response.getOutputStream();
        out.write(bytes);
        out.close();
    }

}
