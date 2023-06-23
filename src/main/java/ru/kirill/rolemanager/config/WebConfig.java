package ru.kirill.rolemanager.config;

import jakarta.xml.ws.Endpoint;
import org.apache.cxf.Bus;
import org.apache.cxf.jaxws.EndpointImpl;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.kirill.rolemanager.service.RoleService;
import ru.kirill.rolemanager.service.UserService;

@Configuration
@EnableCaching
public class WebConfig {

    public static final String USER_SERVICE_URI = "/users";
    public static final String ROLE_SERVICE_URI = "/roles";

    @Bean
    public Endpoint userEndpoint(Bus bus, UserService userService) {
        EndpointImpl endpoint = new EndpointImpl(bus , userService);
        endpoint.publish(USER_SERVICE_URI);
        return endpoint;
    }

    @Bean
    public Endpoint roleEndpoint(Bus bus, RoleService roleService) {
        EndpointImpl endpoint = new EndpointImpl(bus , roleService);
        endpoint.publish(ROLE_SERVICE_URI);
        return endpoint;
    }
}