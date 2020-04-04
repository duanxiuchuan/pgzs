package cn.com.config;

import cn.com.service.admin.FileService;
import cn.com.service.admin.impl.FileServiceImpl;
import cn.com.utils.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

import java.io.File;

/**
 * @author LiDaDa
 */
@Configuration
@ConditionalOnMissingBean(FileService.class)
public class FileSystemConfig {
    @Autowired
    Environment env;

    @Bean
    public FileService getFileService(ApplicationContext ctx) {

        String root = env.getProperty("localFile.root");
        if (StringUtils.isEmpty(root)) {
            String userDir = System.getProperty("user.dir");
            root = userDir + File.separator + "filesystem";
        }
        File f = new File(root);
        if (!f.exists()) {
            f.mkdirs();
        }
        return new FileServiceImpl(ctx, root);
    }
}
