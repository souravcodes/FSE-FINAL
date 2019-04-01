package com.fse.s1.projectmanager.service;

import java.util.List;

import com.fse.s1.projectmanager.entity.Task;
import com.fse.s1.projectmanager.to.SearchCriteria;

public interface ITaskService {

	public Task getTask(long id);
	
	public List<Task> getFilteredTask(SearchCriteria criteria);

	public Task saveTask(Task taskDetails);

	public Task updateTask(Task taskDetails);

	public void deleteTask(long id);
}
