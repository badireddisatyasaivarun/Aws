package com.Aws.Aws.Service;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.Aws.Aws.Config.ConfigData;
import com.amazonaws.SdkClientException;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.AmazonS3Exception;
import com.amazonaws.services.s3.model.S3Object;
import com.amazonaws.services.s3.model.S3ObjectInputStream;
import com.amazonaws.util.IOUtils;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public @Data class AwsImpl implements AwsService {
		
	private ConfigData configData;
    private String bucketName = configData.buketName;
	
	@Autowired
	private AmazonS3 s3Client;
	
	public String uploadFile(MultipartFile file) {
		
		File fileObj = convertMultipartFileToFile(file);
		String fileName = System.currentTimeMillis()+"_"+file.getOriginalFilename();
		s3Client.putObject(bucketName, fileName, fileObj);
		fileObj.delete();
		
		return "FileUploaded.. "+fileName;
	}
	
	public byte[] downoladFile(String fileName) {
		
		S3Object s3Object;
		s3Object = s3Client.getObject(bucketName,fileName);
	
		try {
			S3ObjectInputStream inputStream = s3Object.getObjectContent();
			byte[] content = IOUtils.toByteArray(inputStream);
			return content;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return null;
	}
	
	
	public String getURL(String fileName) {
		try {
		S3Object s3Object = s3Client.getObject(bucketName,fileName);}
		catch(Exception e) {
			return "File Doesn't exists";
		}
		
		return s3Client.generatePresignedUrl(bucketName, fileName, new DateTime().plusMinutes(5).toDate()).toString();
	}
	
	public String deleteFile(String fileName) {
		s3Client.deleteObject(bucketName,fileName);
		return fileName+" removed...";
	}
	
	private File convertMultipartFileToFile(MultipartFile file) {
		File convertedFile = new File(file.getOriginalFilename());
		try(FileOutputStream fos = new FileOutputStream(convertedFile)){
			fos.write(file.getBytes());
		}catch(IOException e) {
			log.error("error converting multipartFile to file",e);
		}
		return convertedFile;
	}
	
}
