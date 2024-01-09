package fr._14.pcd.codingweek15.dao;

import fr._14.pcd.codingweek15.model.Element;
import fr._14.pcd.codingweek15.model.Message;
import fr._14.pcd.codingweek15.model.User;
import fr._14.pcd.codingweek15.util.HibernateUtil;
import org.hibernate.SessionFactory;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;

public final class ElementDAO extends DAO<Element> {
    private static ElementDAO instance;
    private final EntityManagerFactory emf;
    private final EntityManager em;

    private ElementDAO(SessionFactory sf) {
        super(Element.class, sf);
        emf = Persistence.createEntityManagerFactory("michele");
        em = emf.createEntityManager();
        instance = this;
    }

    public static ElementDAO getInstance() {
        if (instance == null) {
            instance = new ElementDAO(HibernateUtil.getSessionFactory());
        }
        return instance;
    }

    public void createElement(String name, Integer price, String description, User owner) {
        em.getTransaction().begin();
        Element element = new Element(name, price, description, owner);
        em.persist(element);
        em.getTransaction().commit();
    }

    public List<Element> getAllElements() {
        return em.createQuery("SELECT u FROM Element u", Element.class).getResultList();
    }

    public void dropTable() {
        em.getTransaction().begin();
        em.createQuery("DELETE FROM Element").executeUpdate();
        em.getTransaction().commit();
    }

    @Override
    public List<Element> search(Element criteria) {
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Element> cr = cb.createQuery(Element.class);
        Root<Element> root = cr.from(Element.class);
        cr.select(root);

        System.out.println("criteria: ");
        List<Predicate> predicates = new ArrayList<>();
        if (criteria.getId() != null) {
            predicates.add(cb.equal(root.get("id"), criteria.getId()));
        }
        if (criteria.getName() != null) {
            predicates.add(cb.equal(root.get("name"), criteria.getName()));
        }
        if (criteria.getDescription() != null) {
            predicates.add(cb.equal(root.get("description"), criteria.getDescription()));
        }
        if (criteria.getPrice() != null) {
            predicates.add(cb.equal(root.get("price"), criteria.getPrice()));
        }
        if (predicates.isEmpty()) {
            return getAllElements();
        }

        Predicate orPredicate = cb.disjunction();
        for (Predicate predicate : predicates) {
            System.out.println("caca");
            orPredicate = cb.or(orPredicate, predicate);
        }
        cr.where(orPredicate);
        Query query = em.createQuery(cr);
        //noinspection unchecked
        return query.getResultList();
    }
}