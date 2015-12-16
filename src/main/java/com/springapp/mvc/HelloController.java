package com.springapp.mvc;

import com.springapp.dao.PersonDao;
import com.springapp.model.Person;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Iterator;
import java.util.List;

@Controller
@RequestMapping("/")
public class HelloController {

	@Autowired
	private PersonDao repository;

	@Autowired
	private DriverManagerDataSource dataSource;

	@Autowired
	private JpaTransactionManager tm;


	@RequestMapping(value = "/printWelcome", method = RequestMethod.GET)
	public String printWelcome(ModelMap model) {

		Iterable<Person> res = repository.findAll();
		StringBuilder str = new StringBuilder();
		Iterator it = res.iterator();
		while(it.hasNext())
			str.append(it.next());

		try {
			Connection c = dataSource.getConnection();
			Statement stmt = c.createStatement();
			String sql = "SELECT id, name, surname FROM person";
			ResultSet rs = stmt.executeQuery(sql);
			while(rs.next()){
				//Retrieve by column name
				int id  = rs.getInt("id");
				String name = rs.getString("name");
				String surname = rs.getString("surname");
				str.append("id:" + id + " name:" + name + " surname:" + surname);
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}

		model.addAttribute("message", "Hello world! qddd" + str);

		return "hello";
	}

	@RequestMapping(value = "/testQuery/{search}", method = RequestMethod.GET)
	@ResponseBody
	public String printWelcome(@PathVariable String search) {

		List<Person> res = this.repository.findByFuzzIs(search);
		return res.toString();

	}

	@RequestMapping(value = "/add/{name}/{surname}", method = RequestMethod.POST)
	@ResponseBody
	public String addPerson(@PathVariable String name, @PathVariable String surname){

		Person person = new Person();
		person.setName(name);
		person.setSurname(surname);
		this.repository.save(person);

		return "User created with ID : " + person.getId();
	}
}