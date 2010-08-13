package org.test;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.jboss.seam.ScopeType;
import org.jboss.seam.annotations.Name;
import org.jboss.seam.annotations.Scope;
import org.test.datalists.PaginatingDataModel;

@Scope(ScopeType.SESSION)
@Name("pagAccessExtendedDataModel")
public class PagAccessExtendedDataModel extends
        PaginatingDataModel<User, String> {

    private EntityManager entityManager;
    private List<User> returnList;

    private int firstRow = 0;
    private int maxResults = 0;

    public PagAccessExtendedDataModel() {

    }

    public void setEntityManager(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    public EntityManager getEntityManager() {
        if (entityManager == null) {
            entityManager = EntityManagerCreator.createManager();
        }

        return entityManager;
    }
 
    @Override
    public List<User> findObjects(int firstRow, int maxResults,
            String sortField, boolean ascending) {

        if ((this.firstRow != firstRow) || (this.maxResults != maxResults)) {
            Query q = getEntityManager().createQuery(
                    "select u from User u order by u.firstName");
            q.setFirstResult(firstRow);
            q.setMaxResults(maxResults);

            returnList = q.getResultList();
            // setRowCount(null);
            this.firstRow = firstRow;
            this.maxResults = maxResults;
        }
        return returnList;

    }

    @Override
    public String getDefaultSortField() {
        return "attribute(EOD)";
    }

    @Override
    public String getId(User object) {
        return object.getId();
    }

    @Override
    public int getNumRecords() {
        Query q = getEntityManager().createQuery("select count(u) from User u");
        int count = (int) (long) (Long) q.getSingleResult();

        return count;
    }

    @Override
    public User getObjectById(String id) {
        if (returnList != null) {
            for (User user : returnList) {
                if (user.getId().equals(id)) {
                    return user;
                }
            }
        }
        return getEntityManager().find(User.class, id);
    }
}
