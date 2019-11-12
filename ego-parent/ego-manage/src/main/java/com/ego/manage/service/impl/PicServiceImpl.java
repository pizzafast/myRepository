package com.ego.manage.service.impl;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.ego.commons.utils.FtpUtil;
import com.ego.manage.service.PicService;

@Service
public class PicServiceImpl implements PicService {
	@Value("${ftpclient.host}")
	private String host;
	@Value("${ftpclient.port}")
	private int port;
	@Value("${ftpclient.username}")
	private String username;
	@Value("${ftpclient.password}")
	private String password;
	// 为/home/ftpuser
	@Value("${ftpclient.basePath}")
	private String basePath;
	// *存放在/home/ftpuser的什么文件夹下
	@Value("${ftpclient.filepath}")
	private String filePath;
	// 直接访问路径
	@Value("${ftpclient.path}")
	private String path;

	@Override
	public Map<String, Object> uploadPic(MultipartFile uploadFile) throws IOException {
		String name = uploadFile.getOriginalFilename();
		String fileName = UUID.randomUUID().toString() + name.substring(name.lastIndexOf("."));
		Map<String, Object> map = new HashMap<>();

		boolean result = FtpUtil.uploadFile(host, port, username, password, basePath, filePath, fileName,uploadFile.getInputStream());
		if (result) {
			map.put("error", 0);
			map.put("url", path + fileName);
		} else {
			map.put("error", 1);
			map.put("message", "图片上传失败");
		}
		return map;
	}

}
