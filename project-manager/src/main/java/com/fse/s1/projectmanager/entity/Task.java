package com.fse.s1.projectmanager.entity;

import java.sql.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name="T_TASK_DETAILS")
public class Task {
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name="TD_TASK_ID")
	private long taskId;
	
	@Column(name="TD_TASK_NAME", nullable=false)
	private String task;
	
	@JoinColumn(name="TD_PARENT_ID", nullable=true)
	@ManyToOne(fetch=FetchType.EAGER)
	private ParentTask parent;
	
	@Column(name="TD_PRIORITY", nullable=false)
	private int priority;
	
	@Column(name="TD_START_DATE", nullable=false)
	private Date startDate;
	
	@Column(name="TD_END_DATE", nullable=true)
	private Date endDate;
	
	private transient String parentName;

	public long getTaskId() {
		return taskId;
	}

	public void setTaskId(long taskId) {
		this.taskId = taskId;
	}

	public String getTask() {
		return task;
	}

	public void setTask(String task) {
		this.task = task;
	}

	public int getPriority() {
		return priority;
	}

	public void setPriority(int priority) {
		this.priority = priority;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public ParentTask getParent() {
		return parent;
	}

	public void setParent(ParentTask parent) {
		this.parent = parent;
	}

	public String getParentName() {
		return this.parent.getParentTask();
	}
/*
	public void setParentName(String parentName) {
		this.parentName = parentName;
	}
*/
	@Override
	public String toString() {
		return "Task [taskId=" + taskId + ", task=" + task + ", parentId=" + parent + ", priority=" + priority
				+ ", startDate=" + startDate + ", endDate=" + endDate + ", parentName=" + parentName + "]";
	}
	
	public String toJson() {
		return "{'taskId':" + taskId + ", 'task':" + task + ", 'parentId':" + parent + ", 'priority':" + priority
				+ ", 'startDate':" + startDate + ", 'endDate':" + endDate + ", 'parentName':" + parentName + "}";
	}
}
