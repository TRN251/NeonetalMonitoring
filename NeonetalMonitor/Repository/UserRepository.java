package ACT.project.NeonetalMonitor.Repository;

import ACT.project.NeonetalMonitor.Entity.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository("userRepository")
public interface UserRepository extends CrudRepository<User, Long> {
	
	
	 User findByEmail(String email);

	// User finfById(Integer id);
	
	 User findByConfirmationToken(String confirmationToken);
	 
	  List<User> findAll();
}