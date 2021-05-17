package com.Aws.Aws.Service;

import org.springframework.web.multipart.MultipartFile;

public interface AwsService {

	public String uploadFile(MultipartFile file);
	public byte[] downoladFile(String fileName);
	public String getURL(String fileName);
	public String deleteFile(String fileName);
	
}
