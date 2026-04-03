package com.IshaanBansal.LoggingFramework.metrics;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.EntityTransaction;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.jpa.EntityManagerProxy;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
@Transactional
public class MetricDAO {

    @Autowired
    private EntityManagerFactory entityManagerFactory;

    private static Logger LOGGER = LoggerFactory.getLogger(MetricDAO.class);

    public void saveOrUpdateThroughputMetrics(ServiceMetricsDO serviceMetricsDO) {
        LOGGER.info("In SaveOrUpdateThroughPutMetrics !!!!");

            EntityManager entityManager = entityManagerFactory.createEntityManager();
            EntityTransaction txn = entityManager.getTransaction();
            try{
                txn.begin();
                Object mergedEntity = entityManager.merge(serviceMetricsDO);
                entityManager.persist(serviceMetricsDO);
                txn.commit();
            }catch (Exception e){
                if(txn.isActive()){
                    txn.rollback();
                }
                LOGGER.info("Save/Update failed", e);
            }
            finally {
                entityManager.close();
            }



    }

    public ServiceMetricsDO getThroughputMetrics(String serviceName, LocalDateTime startTime) {
        LOGGER.info("In getThroughPut Metrics!!!");

        EntityManager entityManager = entityManagerFactory.createEntityManager();
        CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
        CriteriaQuery<ServiceMetricsDO> criteriaQuery= criteriaBuilder.createQuery(ServiceMetricsDO.class);
        Root<ServiceMetricsDO> root = criteriaQuery.from(ServiceMetricsDO.class);

        criteriaQuery.where(criteriaBuilder.and(
                criteriaBuilder.equal(root.get("serviceName"), serviceName),
                criteriaBuilder.greaterThanOrEqualTo(root.get("createdAt"), startTime)
        ));

        criteriaQuery.groupBy(root.get("serviceName"));
        try {
            return entityManager.createQuery(criteriaQuery).getSingleResult();
        } catch (Exception e) {
            return null;
        }
    }
}
