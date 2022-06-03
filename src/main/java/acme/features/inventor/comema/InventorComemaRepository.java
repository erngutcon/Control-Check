package acme.features.inventor.comema;

import java.util.Collection;

import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import acme.entities.Comema;
import acme.entities.Configuration;
import acme.entities.Item;
import acme.framework.entities.UserAccount;
import acme.framework.repositories.AbstractRepository;
import acme.roles.Inventor;

@Repository
public interface InventorComemaRepository extends AbstractRepository {

	@Query("SELECT userAccount FROM UserAccount userAccount WHERE userAccount.id = :id")
	UserAccount findOneUserAccountById(int id);
	
	@Query("SELECT inventor FROM Inventor inventor WHERE inventor.id = :id")
	Inventor findOneInventorById(int id);
	
	@Query("SELECT inventor FROM Inventor inventor WHERE inventor.userAccount.id = :id")
	Inventor findOneInventorByAccountId(int id);
	
	@Query("SELECT c FROM Comema c WHERE c.id = :id")
	Comema findOneComemaById(int id);
	
	@Query("SELECT i FROM Item i WHERE i.id = :id")
	Item findOneItemById(int id);
	
	@Query("SELECT c FROM Comema c where c.item.type = 'COMPONENT'")
	Collection<Comema> findAllComemas();
	
	@Query("SELECT c FROM Comema c where c.item.inventor.id = :id and c.item.type = 'COMPONENT'")
	Collection<Comema> findAllComemasByInventorId(int id);
	
	@Query("select configuration from Configuration configuration")
	Configuration findConfiguration();
}