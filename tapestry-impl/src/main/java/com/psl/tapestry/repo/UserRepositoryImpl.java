package com.psl.tapestry.repo;

//@Transactional
public abstract class UserRepositoryImpl {//implements UserRepository {

	/*@PersistenceContext
	private EntityManager em;

	public List<User> getAllUser() {
		return em.createQuery("SELECT u FROM User u", User.class)
				.getResultList();
	}

	public void createUser(User user) {
		try {
			em.persist(user);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}

	public User findUserById(int id) {
		// TODO Auto-generated method stub
		return (User) em.createQuery("SELECT u FROM User u where id="+Integer.valueOf(id), User.class).getSingleResult();
	}

	public User updateUser(int id, User user) {
		// TODO Auto-generated method stub
		
		return null;
	}
*/
	

}
