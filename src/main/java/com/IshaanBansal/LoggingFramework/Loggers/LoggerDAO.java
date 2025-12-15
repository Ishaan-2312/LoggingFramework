package com.IshaanBansal.LoggingFramework.Loggers;

import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Component;

//import javax.management.Query;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Transactional
@Component
public class LoggerDAO {

    @Autowired
    MongoTemplate mongoTemplate;

    private static Logger LOGGER = LoggerFactory.getLogger(LoggerDAO.class);


    public void saveOrUpdateLogBatchRequest(LogBatchRequest logEventList) {
        LOGGER.info("In LOGGERDAO!!!");
        LOGGER.info("In saveOrUpdate LogBatchRequest!!!!");
        mongoTemplate.save(logEventList);

    }

    public List<LogBatchRequest> getLogEventsByServiceName(String serviceName) {
        Query query = new Query(Criteria.where("logEvents").elemMatch(Criteria.where("serviceName").is(serviceName)));
        List<LogBatchRequest> logBatchRequests = new ArrayList<>();
        logBatchRequests = mongoTemplate.find(query,LogBatchRequest.class);
        if(logBatchRequests.isEmpty()){
            LOGGER.info("Log Batch Requests List is Empty!!!");
        }
        return logBatchRequests;
    }

    public List<LogBatchRequest> getLogEventsByServiceNameAndTime(String serviceName, LocalDateTime time) {
        LocalDateTime from = time.minusSeconds(5);
        LocalDateTime to   = time.plusSeconds(5);
        //Created a 10 sec window for time ranged query
        Query query = new Query(Criteria.where("logEvents").elemMatch(Criteria.where("serviceName").is(serviceName).and("timestamp").gte(from).lte(to)));
        List<LogBatchRequest> logBatchRequests = new ArrayList<>();
        logBatchRequests = mongoTemplate.find(query,LogBatchRequest.class);
        if(logBatchRequests.isEmpty()){
            LOGGER.info("Log Batch Requests List is Empty!!!");
        }
        return logBatchRequests;

    }
}
