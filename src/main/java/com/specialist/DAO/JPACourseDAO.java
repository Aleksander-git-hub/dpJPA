package com.specialist.DAO;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import java.util.List;

@Service("jpaCourseService")
@Repository
@Transactional
public class JPACourseDAO implements CourseDAO
{
    private static final Log LOG = LogFactory.getLog(JPACourseDAO.class);

    @PersistenceContext
    private EntityManager entityManager;

    @Transactional(readOnly = true)
    @Override
    public Course findByID(int id) {
        TypedQuery<Course> query = entityManager.createQuery(
                "select c from Course c where c.id = :id", Course.class);
        query.setParameter("id", id);
        return query.getSingleResult();
    }

    @Transactional(readOnly = true)
    @Override
    public List<Course> findAll() {
        return entityManager.createNamedQuery("Course.findAll", Course.class).getResultList();
        /*return entityManager.createQuery("select c from Course c", Course.class).
                getResultList();*/
    }

    @Override
    public List<Course> findByTitle(String title) {
        TypedQuery<Course> query = entityManager.
                createQuery("select c from Course c where c.title LIKE :title", Course.class);
        query.setParameter("title", "%" + title + "%");
        return query.getResultList();
    }

    @Override
    public void insert(Course course) {
        entityManager.persist(course);
        LOG.info("Course saved with id: " + course.getId());
    }

    @Override
    public void update(Course course) {
        if (course.getId() != 0 &&
                entityManager.find(Course.class, course.getId()) != null) {
            entityManager.merge(course);
            LOG.info("Course updated with id: " + course.getId());
        }
    }

    @Override
    public void delete(int id) {
        entityManager.remove(entityManager.find(Course.class, id));
        LOG.info("Course deleted with id: " + id);
    }
}
