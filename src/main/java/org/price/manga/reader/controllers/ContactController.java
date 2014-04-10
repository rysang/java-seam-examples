package org.price.manga.reader.controllers;

import java.util.Map;

import javax.validation.Valid;

import org.price.manga.reader.dao.services.api.UserMgmtDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class ContactController {

	@Autowired
	@Qualifier("userMgmtDao")
	private UserMgmtDao userMgmtDao;

	// @Autowired
	// @Qualifier("contactConverter")
	// private Converter<Contact> contactConverter;

	// @Autowired
	// private Validator validator;

	public ContactController() {

	}

	@RequestMapping(value = { "/index", "/" })
	public String listContacts(Map<String, Object> map) throws Exception {
		// List<Entity> entities = testService.getAllBeans();
		// Entity ep = new Entity(Contact.NAME);
		// ep.setProperty("email", "price@fdsf.df");
		//
		// contacts = new ArrayList<Contact>(entities.size());
		// for (Entity e : entities) {
		// Contact c = contactConverter.convertEntity(e);
		// contacts.add(c);
		// }

		// map.put("entity", ep);
		// map.put("contactList", contacts);
		return "contact";
	}

	@RequestMapping(value = { "/new" })
	public String newContact(Map<String, Object> map) {
		// map.put("contact", new Contact());
		return "new_contact";
	}

	@RequestMapping(value = { "/secure/contact" })
	public String testSecure(Map<String, Object> map) {
		// map.put("contact", new Contact());
		return "secure/contact";
	}

	@RequestMapping(value = "/add", method = RequestMethod.POST)
	public String addContact(@ModelAttribute("contact") @Valid Object contact,
			BindingResult result) throws Exception {

		if (result.hasErrors()) {
			return "new_contact";
		}

		// Entity entity = contactConverter.convertType(contact);
		// testService.txSaveBean(entity);

		return "redirect:/secure/contact";
	}

	@RequestMapping("/delete")
	public String deleteContact(@RequestParam("contactId") String contactId) {
		// testService.txDelete(contactId);
		return "redirect:/index";
	}
}