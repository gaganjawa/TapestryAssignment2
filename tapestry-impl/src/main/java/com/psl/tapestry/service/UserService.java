package com.psl.tapestry.service;

import java.util.List;

import com.psl.tapestry.entities.User;
import com.psl.tapestry.exception.NoUserPresentException;

/**
 * UserService Interface to declare CRUD functions.
 * @author gagan_jawa
 *
 */
public interface UserService {

	User createUser(User user);

	User findUserById(int id);

	User updateUser(int id, User user);

	boolean deleteUser(int id) throws NoUserPresentException;

	List<User> getAllUsers();

}
