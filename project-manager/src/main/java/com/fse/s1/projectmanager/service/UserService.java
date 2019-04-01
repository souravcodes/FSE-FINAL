package com.fse.s1.projectmanager.service;

import java.util.LinkedList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fse.s1.projectmanager.entity.User;
import com.fse.s1.projectmanager.repo.UserRepository;
import com.fse.s1.projectmanager.to.UserTo;
import com.fse.s1.projectmanager.util.Util;

@Service
public class UserService implements IUserService {

	@Autowired
	private UserRepository userRepo;
	
	@Override
	public UserTo addUser(UserTo userTo) {
		User user = Util.exchangeUserAndUserTo(userTo, new User());
		user = userRepo.save(user);
		if(user != null && user.getUserId() != 0L)
			return Util.exchangeUserAndUserTo(user, userTo);
		else
			return userTo;
	}

	@Override
	public List<UserTo> getAllUser() {
		List<UserTo> userList = new LinkedList<>();
		userRepo.findAll().forEach(e -> {
			UserTo ut = Util.exchangeUserAndUserTo(e, new UserTo());
			userList.add(ut);
		});
		return userList;
	}

	@Override
	public UserTo updateUser(UserTo userTo) {
		User user = this.userRepo.findById(userTo.getUserId()).orElse(new User());
		if(user != null && user.getUserId() != 0L){
			user.setFirstName(userTo.getFirstName());
			user.setLastName(userTo.getLastName());
			user.setEmployeeId(userTo.getEmployeeId());
			this.userRepo.save(user);
			return Util.exchangeUserAndUserTo(user, userTo);
		}
		return new UserTo();
	}

	@Override
	public String deleteUser(long id) {
		boolean exists = this.userRepo.existsById(id);
		if(exists)
			this.userRepo.deleteById(id);
		else
			return "User already deleted";
		return "Successfully deleted";
	}

}
