package ro.penteker.auktion.services.api;

import java.util.List;

import ro.penteker.auktion.dao.AukCategory;
import ro.penteker.auktion.dao.AukType;

public interface CategoryPersistenceService {

  public AukCategory getCategory(String name);

  public AukCategory getCategoryAndTypes(String name);

  public AukCategory saveCategory(AukCategory category);

  public void deleteCategory(AukCategory category);

  public AukType getType(AukCategory category, String name);

  public AukType saveType(AukType type);

  public void deleteType(AukType type);

  public void updateCategory(AukCategory category);

  public void updateType(AukType type);

  public List<AukCategory> getCategoriesAndTypes();
}
