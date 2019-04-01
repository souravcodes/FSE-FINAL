package com.fse.s1.projectmanager;

import java.sql.Date;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fse.s1.projectmanager.TaskManagerApplication;
import com.fse.s1.projectmanager.controller.TaskController;
import com.fse.s1.projectmanager.entity.ParentTaskEntity;
import com.fse.s1.projectmanager.entity.TaskEntity;
import com.fse.s1.projectmanager.to.SearchCriteria;

@RunWith(SpringRunner.class)
@SpringBootTest
@ContextConfiguration(classes ={TaskManagerApplication.class})
public class TaskManagerApplicationTests {

	private MockMvc mockMvc;

	@Autowired
	private TaskController controller;
	private TaskEntity task;

	@Before
	public void setUp() {
		mockMvc = MockMvcBuilders.standaloneSetup(controller).build();

		ParentTaskEntity pt = new ParentTaskEntity();
		pt.setParentTask("parent_test_task");
		task = new TaskEntity();
		task.setTask("test_task");
		task.setParent(pt);
		task.setPriority(19);
		task.setStartDate(new Date(System.currentTimeMillis()));
		task.setEndDate(new Date(System.currentTimeMillis()+2592000000L));
	}

	@Test
	public void contextLoads() {

	}

	@Test
	public void addTask() throws Exception{
		System.out.println("****** ADDING NEW TASK");
		RequestBuilder builder = MockMvcRequestBuilders.post("/api/task/add")
				.content(getValueAsString(task)).contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON);
		MvcResult result = mockMvc.perform(builder).andReturn();
		task = new ObjectMapper().readValue(result.getResponse().getContentAsString(), 
				TaskEntity.class);
		if(result.getResponse().getStatus() == 201){ 
			System.out.println(task);
			System.out.println("****** ADDITION OF NEW TASK PASSED");
			checkById();
			checkByFilter();
			updateTask();
			deleteTask();
		}
	}

	public void checkById() throws Exception{
		System.out.println("****** FETCHING TASK BY ID");
		RequestBuilder builder = MockMvcRequestBuilders.get("/api/task/" + this.task.getTaskId());
		MvcResult result = mockMvc.perform(builder).andReturn();
		TaskEntity task = null;
		task = new ObjectMapper().readValue(result.getResponse().getContentAsString(), TaskEntity.class);
		if(result.getResponse().getStatus() == 200 ){
			System.out.println(task);
			System.out.println("****** FETCHING TASK BY ID PASSED");
		}
	}

	public void checkByFilter() throws Exception{
		System.out.println("****** FETCHING TASK BY FILTER");
		TaskEntity[] localTask = null;
		SearchCriteria criteria = new SearchCriteria();
		criteria.setParentTask(String.valueOf(this.task.getParent().getParentId()));
		System.out.println("Search By Parent ID:");
		RequestBuilder builder = MockMvcRequestBuilders.put("/api/tasks/filtered").content(getValueAsString(criteria))
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON);
		MvcResult result = mockMvc.perform(builder).andReturn();
		localTask = new ObjectMapper().readValue(result.getResponse().getContentAsString(), TaskEntity[].class);

		if(result.getResponse().getStatus() == 200 
				&& localTask[0].getParent().getParentId() == this.task.getParent().getParentId())
			System.out.println("PASSED **");
		System.out.println("****** FETCHING TASK BY FILTER PASSED");
	}

	public void updateTask() throws Exception{
		System.out.println("****** UPDATE TASK");
		task.setPriority(30);
		ObjectMapper mapper = new ObjectMapper();
		String content = mapper.writeValueAsString(task);
		RequestBuilder builder = MockMvcRequestBuilders.put("/api/task/update")
				.content(content).contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON);
		MvcResult result = mockMvc.perform(builder).andReturn();

		TaskEntity localTask = new ObjectMapper().readValue(result.getResponse().getContentAsString(), 
				TaskEntity.class);
		if(result.getResponse().getStatus() == 200 
				&& this.task.getPriority() == localTask.getPriority()){
			System.out.println(task);
			System.out.println("****** UPDATING TASK PASSED");
		}
	}

	public void deleteTask() throws Exception{
		System.out.println("****** DELETE TASK BY ID");
		RequestBuilder builder = MockMvcRequestBuilders.delete("/api/task/delete/" + this.task.getTaskId());
		MvcResult result = mockMvc.perform(builder).andReturn();

		if(result.getResponse().getStatus() == 204){
			System.out.println("****** DELETE TASK BY ID PASSED");
		}
	}

	private String getValueAsString(Object obj){
		ObjectMapper mapper = new ObjectMapper();
		try {
			return mapper.writeValueAsString(obj);
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
		return null;
	}

}
