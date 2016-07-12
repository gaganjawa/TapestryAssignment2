package com.psl.tapestry.service;

import java.util.List;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.psl.tapestry.entities.User;
import com.psl.tapestry.exception.NoUserPresentException;
import com.psl.tapestry.repo.UserRepository;

@Service
@Transactional(rollbackFor=Exception.class)
public class UserServiceImpl implements UserService {

	private final Logger logger = Logger.getLogger(UserServiceImpl.class.getName());

	@Autowired
	private UserRepository userRepository;

	public User createUser(User user) {
		logger.info(user.toString());
		userRepository.save(user);
		return userRepository.findOne(user.getId());
	}

	public User findUserById(int id) {
		return userRepository.findOne(id);
	}

	public User updateUser(int id, User user) {
		userRepository.delete(id);
		return userRepository.save(user);
	}

	public boolean deleteUser(int id) throws NoUserPresentException {
		if (userRepository.exists(id)) {
			userRepository.delete(id);
			return true;
		} else {
			throw new NoUserPresentException("User with id:"+id+" does not exists.");
		}
	}

	public List<User> getAllUsers() {
		List<User> users = (List<User>) userRepository.findAll();
		return users;
	}
}
