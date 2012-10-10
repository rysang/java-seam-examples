package ro.penteker.auktion.services.api;

import java.util.List;
import java.util.Map;

import org.primefaces.model.SortOrder;

import ro.penteker.auktion.dao.AukCategory;

public interface CategoryPersistenceService {

  public AukCategory getCategory(String name);

  public AukCategory saveCategory(AukCategory category);

  public void deleteCategory(AukCategory category);
  
  public List<AukCategory> getCategories(int first, int pageSize, String sortField, SortOrder sortOrder,
      Map<String, String> filters);
}
