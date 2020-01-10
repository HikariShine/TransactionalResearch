package com.mxixm.transactional.jpa.listener;

import com.mxixm.transactional.jpa.event.CustomerCreatedEvent;
import com.mxixm.transactional.jpa.service.TokenGenerator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Profile;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionalEventListener;

/**
 * Created by bslota on 30.06.17.
 */
@Component
@Profile("async")
public class CustomerCreatedEventAsyncListener {

    private static final Logger LOGGER = LoggerFactory.getLogger(CustomerCreatedEventAsyncListener.class);

    private final TokenGenerator tokenGenerator;

    public CustomerCreatedEventAsyncListener(TokenGenerator tokenGenerator) {
        this.tokenGenerator = tokenGenerator;
    }

    @TransactionalEventListener
    @Async
    public void processCustomerCreatedEvent(CustomerCreatedEvent event) {
        LOGGER.info("Event received: " + event);
        tokenGenerator.generateToken(event.getCustomer());
    }
}
