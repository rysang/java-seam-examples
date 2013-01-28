package ro.penteker.auktion.services.api;

import java.util.List;

import ro.penteker.auktion.dao.AukCategory;
import ro.penteker.auktion.dao.AukType;

public interface CategoryService {

  public AukCategory getCategory(String name);

  public AukCategory getCategoryAndTypes(String name);

  public AukCategory createCategory(AukCategory category);

  public AukCategory updateCategory(AukCategory category, List<AukType> removalList);

  public void deleteCategory(AukCategory category);

  public List<AukCategory> getCategoriesAndTypes();
}
