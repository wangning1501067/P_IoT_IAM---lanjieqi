package com.beyondsoft.rdc.cloud.iot.iam.common.fastdfs;

import com.github.tobato.fastdfs.FdfsClientConfig;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableMBeanExport;
import org.springframework.context.annotation.Import;
import org.springframework.jmx.support.RegistrationPolicy;

/**
 * @Author: chencong
 * @Description: fastDfs 配置
 * @Date: 19-2-26
 * @Version: 1.0
 */
@Configuration
@Import(FdfsClientConfig.class)
//解决jmx重复注册bean问题
@EnableMBeanExport(registration = RegistrationPolicy.IGNORE_EXISTING)
public class FastDFS {
}
