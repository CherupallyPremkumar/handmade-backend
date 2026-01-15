package com.handmade.ecommerce.test;

import jakarta.persistence.Entity;
import jakarta.persistence.EntityManager;
import jakarta.persistence.metamodel.EntityType;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.reflections.Reflections;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import java.util.Set;
import java.util.stream.Collectors;

@SpringBootTest
public class AutomatedEntityIntegrationTest {

    @Autowired
    private EntityManager entityManager;

    /**
     * TEST 1: Liquibase vs Hibernate Validation
     * 
     * Since 'spring.jpa.hibernate.ddl-auto' is set to 'validate' in application.yml,
     * the ApplicationContext will fail to start if Hibernate finds any discrepancy
     * between the Java Entities and the Liquibase-generated Schema.
     * 
     * So, if we reach this test method, it means:
     * 1. Liquibase ran successfully (Schema created).
     * 2. Hibernate validated all mappings successfully.
     */
    @Test
    void verifySchemaValidation() {
        Assertions.assertNotNull(entityManager, "EntityManager should be available.");
    }

    /**
     * TEST 2: Entity Discovery
     * 
     * Verifies that all classes annotated with @Entity are actually registered
     * with the EntityManager. This catches issues where an entity might be
     * created but not scanned/picked up by Spring.
     */
    @Test
    void verifyAllEntitiesRegistered() {
        // 1. Scan for all @Entity classes in the domain package
        Reflections reflections = new Reflections("com.handmade.ecommerce");
        Set<Class<?>> detectedEntities = reflections.getTypesAnnotatedWith(Entity.class);

        // 2. Get all entities registered in Hibernate/JPA
        Set<Class<?>> registeredEntities = entityManager.getMetamodel().getEntities().stream()
                .map(EntityType::getJavaType)
                .collect(Collectors.toSet());

        System.out.println("DEBUG: Detected Entities: " + detectedEntities.stream().map(Class::getSimpleName).collect(Collectors.toList()));
        System.out.println("DEBUG: Registered Entities: " + registeredEntities.stream().map(Class::getSimpleName).collect(Collectors.toList()));
        
        // 3. Compare
        detectedEntities.forEach(entity -> {
            Assertions.assertTrue(registeredEntities.contains(entity), 
                "Entity [" + entity.getSimpleName() + "] was found in classpath but NOT registered with JPA. " +
                "Check package scanning or @Entity annotation.");
        });

        System.out.println("✅ VERIFIED: " + registeredEntities.size() + " Entities are correctly mapped and registered.");
    }
    
    @SpringBootApplication(scanBasePackages = "com.handmade.ecommerce")
    @org.springframework.boot.autoconfigure.domain.EntityScan(basePackages = "com.handmade.ecommerce")
    static class TestConfig {
        // Minimal app to bootstrap the test context
    }
}
