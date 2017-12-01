package com.example.springfiveflux.config;

import com.example.springfiveflux.core.DefaultCorePackage;
import com.example.springfiveflux.core.domain.DefaultDomainPackage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.cassandra.config.AbstractReactiveCassandraConfiguration;
import org.springframework.data.cassandra.config.SchemaAction;
import org.springframework.data.cassandra.repository.config.EnableReactiveCassandraRepositories;

@Configuration
@EnableReactiveCassandraRepositories(basePackageClasses = DefaultDomainPackage.class)
@ComponentScan(basePackageClasses = {DefaultCorePackage.class})
public class CassandraConfig extends AbstractReactiveCassandraConfiguration {

    @Value("${spring.data.cassandra.keyspace-name}")
    String keySpaceName;

    @Value("${spring.data.cassandra.contact-points}")
    String contactPoints;

    @Value("${spring.data.cassandra.port}")
    Integer port;


    @Override
    protected String getKeyspaceName() {
        return keySpaceName;
    }

    @Override
    public String getContactPoints() {
        return contactPoints;
    }

    @Override
    public SchemaAction getSchemaAction() {
        return SchemaAction.RECREATE;
    }
}
