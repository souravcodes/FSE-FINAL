package com.fse.s1.projectmanager.service;

import java.util.List;

import com.fse.s1.projectmanager.to.UserTo;

public interface IUserService {

	public UserTo addUser(UserTo userTo);
	public List<UserTo> getAllUser();
	public UserTo updateUser(UserTo userTo);
	public String deleteUser(long id);
}
