package org.price.test.controllers;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.price.test.beans.Contact;
import org.price.test.beans.converters.EntityConverter;
import org.price.test.dao.services.api.TestDaoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.KeyFactory;

@Controller
public class ContactController {
	private List<Contact> contacts = new ArrayList<Contact>();

	@Autowired
	@Qualifier("testService")
	private TestDaoService testService;

	@Autowired
	private EntityConverter entityConverter;

	@Autowired
	private Validator validator;

	public ContactController() {

	}

	@RequestMapping(value = { "/index", "/" })
	public String listContacts(Map<String, Object> map) throws Exception {
		List<Entity> entities = testService.getAllBeans();
		Entity ep = new Entity(Contact.NAME);
		ep.setProperty("email", "price@fdsf.df");

		contacts = new ArrayList<Contact>(entities.size());
		for (Entity e : entities) {
			Contact c = new Contact();
			c.setId(KeyFactory.keyToString(e.getKey()));
			c.setEmail(e.getProperty("email") == null ? null : e.getProperty(
					"email").toString());
		}

		map.put("entity", ep);
		map.put("contactList", contacts);
		return "contact";
	}

	@RequestMapping(value = { "/new" })
	public String newContact(Map<String, Object> map) {
		map.put("contact", new Contact());
		return "new_contact";
	}

	@RequestMapping(value = { "/secure/contact" })
	public String testSecure(Map<String, Object> map) {
		map.put("contact", new Contact());
		return "secure/contact";
	}

	@RequestMapping(value = "/add", method = RequestMethod.POST)
	public String addContact(@ModelAttribute("contact") @Valid Contact contact,
			BindingResult result) throws Exception {

		if (result.hasErrors()) {
			return "new_contact";
		}

		Entity entity = entityConverter.convertToEntity(contact);
		testService.txSaveBean(entity);

		return "redirect:/secure/contact";
	}

	@RequestMapping("/delete")
	public String deleteContact(@RequestParam("contactId") String contactId) {
		testService.txDelete(contactId);
		return "redirect:/index";
	}
}