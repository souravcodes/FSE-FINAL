package com.fse.s1.projectmanager.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fse.s1.projectmanager.entity.ParentTask;
import com.fse.s1.projectmanager.repo.ParentTaskRepository;

@Service
public class ParentTaskService implements IParentTaskService{

	@Autowired
	private ParentTaskRepository parentTaskRepository;
	
	
	@Override
	public ParentTask getParentTask(long id){
		return parentTaskRepository.findById(id).orElse(new ParentTask());
	}

	@Override
	public ParentTask parentTaskExists(String pt){
		return parentTaskRepository.findByParentTask(pt);
	}

	@Override
	public ParentTask addParentTask(ParentTask parentTask){
		return parentTaskRepository.save(parentTask);
	}
	
	@Override
	public ParentTask getParentByName(String name){
		return parentTaskRepository.findByParentTask(name);
	}
}
