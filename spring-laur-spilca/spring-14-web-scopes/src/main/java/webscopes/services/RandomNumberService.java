package webscopes.services;

import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.stereotype.Service;
import org.springframework.web.context.WebApplicationContext;

import java.util.Random;

/**
 * we have:
 * WebApplicationContext.SCOPE_REQUEST = every reload of the page we call the constructor (prototype scope)
 * For this we need proxyMode = ScopedProxyMode.TARGET_CLASS to work as a prototype, or else we will
 * get the same RandomNumberService instance (singleton)
 * we also can have WebApplicationContext.SCOPE_SESSION which will create a 25 min. session for every user
 * (which is also configurable through application.properties)
 * WebApplicationContext.SCOPE_APPLICATION = singleton
 */
@Service
@Scope(value = WebApplicationContext.SCOPE_REQUEST, proxyMode = ScopedProxyMode.TARGET_CLASS)
public class RandomNumberService {

    private final int value;

    public RandomNumberService() {
        this.value = new Random().nextInt(1000);
    }

    public int getValue() {
        // new RuntimeException().printStackTrace(); // to see the whole stack trace when we load/refresh the page
        return value;
    }
}
