package ro.penteker.auktion.presentation.beans.admin.models;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.primefaces.model.LazyDataModel;
import org.primefaces.model.SortOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import ro.penteker.auktion.dao.AukCategory;
import ro.penteker.auktion.services.api.CategoryService;

@Scope("session")
@Component("categoryDataModel")
public class CategoryDataModel extends LazyDataModel<AukCategory> implements Serializable {

  private static final long serialVersionUID = 2654016626874491269L;

  private static final transient Logger LOG = Logger.getLogger(CategoryDataModel.class);
  private List<AukCategory> categories;

  @Autowired
  @Qualifier("categoryService")
  private transient CategoryService categoryService;

  public CategoryDataModel() {

  }

  @Override
  public List<AukCategory> load(int first, int pageSize, String sortField, SortOrder sortOrder,
      Map<String, String> filters) {
    LOG.info("Query : first=" + first + " pageSize=" + pageSize + " sortField=" + sortField + " sortOrder=" + sortOrder
        + " filters=" + filters);
    categories = categoryService.getCategories(first, pageSize, sortField, sortOrder, filters);
    setRowCount(categories.size());

    return categories;
  }

  @Override
  public AukCategory getRowData(String rowKey) {
    for (AukCategory u : categories) {
      if (u.getId().equals(new Long(rowKey))) {
        return u;
      }
    }

    return null;
  }

  @Override
  public Object getRowKey(AukCategory object) {
    return object.getId();
  }

}
