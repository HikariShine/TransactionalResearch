package com.mxixm.transactional.mybatis.listener;

import com.mxixm.transactional.mybatis.event.CustomerCreatedEvent;
import com.mxixm.transactional.mybatis.service.TokenGenerator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionalEventListener;

/**
 * Created by bslota on 30.06.17.
 */
@Component
@Profile("!async")
public class CustomerCreatedEventListener {

    private static final Logger LOGGER = LoggerFactory.getLogger(CustomerCreatedEventListener.class);

    private final TokenGenerator tokenGenerator;

    public CustomerCreatedEventListener(TokenGenerator tokenGenerator) {
        this.tokenGenerator = tokenGenerator;
    }

    @TransactionalEventListener
    public void processCustomerCreatedEvent(CustomerCreatedEvent event) {
        LOGGER.info("Event received: " + event);
        tokenGenerator.generateToken(event.getCustomer());
    }
}
