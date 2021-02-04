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
    private Log log = LogFactory.getLog(JPACourseDAO.class);

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
        return entityManager.createQuery("select c from Course c", Course.class).
                getResultList();
    }
}
