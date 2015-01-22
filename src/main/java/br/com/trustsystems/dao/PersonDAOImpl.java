package br.com.trustsystems.dao;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import br.com.trustsystems.model.Person;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Service
public class PersonDAOImpl implements PersonDAO {
	
	private static final Logger logger = LoggerFactory.getLogger(PersonDAOImpl.class);

	@PersistenceContext
	private EntityManager entityManager;

	@Transactional
	@Override
	public void addPerson(Person p)
	{
		entityManager.persist(p);
		logger.info("Person saved successfully, Person Details="+p);
	}

	@Transactional
	@Override
	public void updatePerson(Person p) {
		entityManager.merge(p);
		logger.info("Person updated successfully, Person Details="+p);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Person> listPersons() {
		List<Person> personsList = entityManager.createQuery("from Person p").getResultList();
		for(Person p : personsList){
			logger.info("Person List::"+p);
		}
		return personsList;
	}

	@Override
	public Person getPersonById(int id) {
		Person p = (Person) entityManager.find(Person.class, new Integer(id));
		logger.info("Person loaded successfully, Person details="+p);
		return p;
	}

	@Transactional
	@Override
	public void removePerson(int id) {

		Person p = (Person) entityManager.find(Person.class, new Integer(id));
		if(null != p){
			entityManager.remove(p);
		}
		logger.info("Person deleted successfully, person details="+p);
	}

}
