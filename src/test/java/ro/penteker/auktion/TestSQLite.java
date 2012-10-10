package ro.penteker.auktion;

import java.util.Date;
import java.util.Hashtable;
import java.util.List;

import org.apache.log4j.Logger;
import org.primefaces.model.SortOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit38.AbstractJUnit38SpringContextTests;

import ro.penteker.auktion.dao.AukCategory;
import ro.penteker.auktion.services.api.CategoryService;
import ro.penteker.auktion.services.api.SecurityService;

@ContextConfiguration(locations = { "classpath:application-context.xml", "classpath:db-context.xml" })
public class TestSQLite extends AbstractJUnit38SpringContextTests {

  private static final Logger LOG = Logger.getLogger(TestSQLite.class);

  @Autowired
  private SecurityService securityService;

  @Autowired
  private CategoryService categoryService;

  @Override
  protected void setUp() throws Exception {
    super.setUp();
  }

  public void testEntities() throws Exception {

    AukCategory category = new AukCategory(null, "Brands", new Date(), "system");
    category.setDescription("The car brands.");
    category = categoryService.createCategory(category);

    List<AukCategory> categories = categoryService.getCategories(0, 15, null, SortOrder.ASCENDING,
        new Hashtable<String, String>());

    LOG.info(categories);

    //categoryService.deleteCategory(category);

    LOG.info(category);
  }
}
