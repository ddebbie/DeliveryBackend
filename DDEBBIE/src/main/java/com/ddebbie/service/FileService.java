package com.ddebbie.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ddebbie.handler.FileHandler;

@RestController
@RequestMapping(value="File")
public class FileService extends BaseService {
	
	@Autowired
	FileHandler fileHandler;

}
