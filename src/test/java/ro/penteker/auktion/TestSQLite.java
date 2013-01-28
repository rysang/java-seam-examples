package ro.penteker.auktion;

import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit38.AbstractJUnit38SpringContextTests;

import ro.penteker.auktion.dao.AukCategory;
import ro.penteker.auktion.dao.AukProduct;
import ro.penteker.auktion.services.api.CategoryService;
import ro.penteker.auktion.services.api.ProductService;
import ro.penteker.auktion.services.api.SecurityService;

@ContextConfiguration(locations = { "classpath:application-context.xml", "classpath:db-context.xml" })
public class TestSQLite extends AbstractJUnit38SpringContextTests {

  private static final Logger LOG = Logger.getLogger(TestSQLite.class);

  @Autowired
  private SecurityService securityService;

  @Autowired
  private CategoryService categoryService;

  @Autowired
  private ProductService productService;

  @Override
  protected void setUp() throws Exception {
    super.setUp();
  }

  public void testEntities() throws Exception {

    List<AukCategory> categories = categoryService.getCategoriesAndTypes();

    AukProduct product = new AukProduct(null, "Some Cool Car", "Some Cool Car", new Date(), "system", 2323, "EUR");
    product.getAukTypeList().addAll(categories.get(1).getAukTypeList());

    productService.saveProduct(product);

  }
}
