package br.com.trustsystems.dao;

import java.util.List;

import javax.persistence.Query;
import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.hibernate5.HibernateCallback;
import org.springframework.orm.hibernate5.HibernateTemplate;
import org.springframework.stereotype.Repository;

import br.com.trustsystems.entities.Person;

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

	public void delete(Person e) {
		template.execute(session -> {
			session.delete(e);
			return true;
		});
	}

	public Person findById(Long id) {
		return template.execute((HibernateCallback<Person>) session -> {
			return session.find(Person.class, id);
		});
	}

	@SuppressWarnings("unchecked")
	public List<Person> findAll() {
		return template.execute((HibernateCallback<List<Person>>) session -> {
			String queryStr = "from Person";
			Query query = session.createQuery(queryStr);
			return query.getResultList();
		});
	}

}
