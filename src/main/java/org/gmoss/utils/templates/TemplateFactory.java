package org.gmoss.utils.templates;

import java.io.IOException;

import freemarker.cache.ClassTemplateLoader;
import freemarker.template.Configuration;
import freemarker.template.Template;

public class TemplateFactory {
	private static Configuration cfg;

	private TemplateFactory() {

	}

	public static Template getTemplate(String name) throws IOException {
		if (cfg == null) {
			cfg = new Configuration();
			ClassTemplateLoader ctl = new ClassTemplateLoader(
					TemplateFactory.class, "/org/gmoss/service/templates");
			cfg.setTemplateLoader(ctl);
		}
		return cfg.getTemplate(name);
	}
}
