package com.ego.manage.service;

import java.io.IOException;
import java.util.Map;

import org.springframework.web.multipart.MultipartFile;

public interface PicService {
	/**
	 * 上传图片到服务器
	 * @param uploadFile
	 * @return
	 */
	Map<String, Object> uploadPic(MultipartFile uploadFile)throws IOException;
}
