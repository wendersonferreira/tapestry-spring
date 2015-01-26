package br.com.trustsystems.dao;

import br.com.trustsystems.database.DatabasePersistenceProvider;
import br.com.trustsystems.entities.Person;
import br.com.trustsystems.persistence.dao.IPersistenceProvider;
import br.com.trustsystems.persistence.dao.TypedDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PersonDAO extends TypedDao<Long, Person>{

	@Autowired
	DatabasePersistenceProvider databasePersistenceProvider;
	
	public PersonDAO() {
		super(Long.class, Person.class);
	}

	@Override
	protected IPersistenceProvider getPersistenceProvider() {
		return databasePersistenceProvider;
	}
}
