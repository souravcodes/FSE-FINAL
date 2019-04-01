package com.fse.s1.projectmanager.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.fse.s1.projectmanager.entity.ParentTask;
import com.fse.s1.projectmanager.service.ParentTaskService;

@RestController
@RequestMapping(value="/api")
@CrossOrigin(origins="http://localhost:4200")
public class ParentTaskController {

	@Autowired
	private ParentTaskService parentTaskService;
	
	@RequestMapping(value="/parent/{id}")
	public ResponseEntity<ParentTask> getParentById(@PathVariable(name="id") long id){
		ParentTask pt = parentTaskService.getParentTask(id);
		if(pt != null){
			return ResponseEntity.ok(pt);
		}else{
			return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
		}
	}
}
