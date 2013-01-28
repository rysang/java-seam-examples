package ro.penteker.auktion.services.impl;

import java.util.ArrayList;
import java.util.List;

import ro.penteker.auktion.dao.AukCategory;
import ro.penteker.auktion.dao.AukType;
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
  public AukCategory getCategoryAndTypes(String name) {
    return categoryPersistenceService.getCategoryAndTypes(name);
  }

  @Override
  public List<AukCategory> getCategoriesAndTypes() {
    return categoryPersistenceService.getCategoriesAndTypes();
  }

  @Override
  public AukCategory createCategory(AukCategory category) {

    List<AukType> types = new ArrayList<AukType>(category.getAukTypeList());
    category.getAukTypeList().clear();
    AukCategory c = categoryPersistenceService.saveCategory(category);

    for (AukType t : types) {
      t.setCategory(category);
      categoryPersistenceService.saveType(t);
    }

    return c;
  }

  @Override
  public AukCategory updateCategory(AukCategory category, List<AukType> removalList) {
    for (AukType t : removalList) {
      categoryPersistenceService.deleteType(t);
    }

    List<AukType> types = new ArrayList<AukType>(category.getAukTypeList());
    category.getAukTypeList().clear();
    categoryPersistenceService.updateCategory(category);

    for (AukType t : types) {
      t.setCategory(category);
      categoryPersistenceService.updateType(t);
    }

    return category;
  }

  @Override
  public void deleteCategory(AukCategory category) {
    categoryPersistenceService.deleteCategory(category);
  }

}
