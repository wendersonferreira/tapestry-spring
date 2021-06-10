package com.codeprep.dao;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Query;
import javax.transaction.Transactional;

import com.codeprep.entities.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate5.HibernateCallback;
import org.springframework.orm.hibernate5.HibernateTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class PersonDAO {

	@Autowired
	HibernateTemplate template;

	public void setTemplate(HibernateTemplate template) {
		this.template = template;
	}

	@Transactional
	public void persist(Person e) {
		template.execute(session -> {
			session.save(e);
			return e.getId();
		});
	}

	public void updateEmployee(Person e) {
		template.update(e);
	}

	@Transactional
	public void delete(Person e) {
		template.delete(e);
	}

	@Transactional
	public Person findById(Long id) {
		return template.execute((HibernateCallback<Person>) session -> {
			return session.find(Person.class, id);
		});
	}

	@SuppressWarnings("unchecked")
	public List<Person> findAll() {
		List<Person> people = new ArrayList<>();
		people = template.execute((HibernateCallback<List<Person>>) session -> {
			String queryStr = "from Person";
			Query query = session.createQuery(queryStr);
			return query.getResultList();
		});
		return people;
	}

}
