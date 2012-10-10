package ro.penteker.auktion.services.impl;

import java.util.List;
import java.util.Map;

import org.primefaces.model.SortOrder;

import ro.penteker.auktion.dao.AukCategory;
import ro.penteker.auktion.services.api.CategoryPersistenceService;
import ro.penteker.auktion.services.api.CategoryService;

public class CategoryServiceImpl implements CategoryService {

  private CategoryPersistenceService categoryPersistenceService;

  public CategoryServiceImpl() {

  }

  public void setCategoryPersistenceService(CategoryPersistenceService categoryPersistenceService) {
    this.categoryPersistenceService = categoryPersistenceService;
  }

  @Override
  public AukCategory getCategory(String name) {
    return categoryPersistenceService.getCategory(name);
  }

  @Override
  public AukCategory createCategory(AukCategory category) {
    return categoryPersistenceService.saveCategory(category);
  }

  @Override
  public void deleteCategory(AukCategory category) {
    categoryPersistenceService.deleteCategory(category);
  }

  @Override
  public List<AukCategory> getCategories(int first, int pageSize, String sortField, SortOrder sortOrder,
      Map<String, String> filters) {
    return categoryPersistenceService.getCategories(first, pageSize, sortField, sortOrder, filters);
  }

}
