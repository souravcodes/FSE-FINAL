package com.fse.s1.projectmanager.repo;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.fse.s1.projectmanager.entity.ParentTask;

@Repository
public interface ParentTaskRepository extends CrudRepository<ParentTask, Long> {

	public ParentTask findByParentTask(String parentTask);
}
