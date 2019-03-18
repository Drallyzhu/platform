package com.cloudsoft.oss;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 文件上传Factory
 * @author zhujianrong
 */
@Component
public final class OSSFactory {
	@Autowired
	CloudStorageConfig config;

	public CloudStorageService build() {
		// 获取云存储配置信息
		if (config.getType() == OssConstant.CloudService.QINIU.getValue()) {
			return new QiniuCloudStorageService(config);
		} else if (config.getType() == OssConstant.CloudService.ALIYUN.getValue()) {
			return new AliyunCloudStorageService(config);
		} else if (config.getType() == OssConstant.CloudService.QCLOUD.getValue()) {
			return new QcloudCloudStorageService(config);
		}
		return null;
	}
}
