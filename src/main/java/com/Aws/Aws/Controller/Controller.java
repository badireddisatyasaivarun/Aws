package com.Aws.Aws.Controller;

import java.net.URL;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.Aws.Aws.Service.AwsImpl;
import com.Aws.Aws.Service.AwsService;
import com.amazonaws.services.s3.model.AmazonS3Exception;

@RestController
public class Controller {

	@Autowired
	private AwsService awsService;
	
	@PostMapping("/")
	public String uploadFile(@RequestParam(value="file") MultipartFile file) {
		return awsService.uploadFile(file);
	}
	
	@GetMapping("/download/{fileName}")
	public ResponseEntity<ByteArrayResource> downloadFile(@PathVariable String fileName) {
		
		try {
		byte[] data = awsService.downoladFile(fileName);
		ByteArrayResource response = new ByteArrayResource(data);
		return ResponseEntity
				.ok()
				.contentLength(data.length)
				.header("Content-type","application/octet-stream")
				.header("Content-disposition","attachment; filename=\""+ fileName + "\"")
				.body(response);}
		catch(Exception e) {
			return null;
		}
		
	}
	
	@GetMapping("/{fileName}")
	public String getURL(@PathVariable String fileName) {
		return awsService.getURL(fileName);
	}
	
	@DeleteMapping("/{fileName}")
	public String deleteFile(@PathVariable String fileName) {
		return awsService.deleteFile(fileName);
	}
}
