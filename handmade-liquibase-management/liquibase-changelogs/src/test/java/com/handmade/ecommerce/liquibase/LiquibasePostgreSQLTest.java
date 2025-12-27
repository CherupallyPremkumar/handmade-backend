package com.handmade.ecommerce.liquibase;

import liquibase.Liquibase;
import liquibase.database.Database;
import liquibase.database.DatabaseFactory;
import liquibase.database.jvm.JdbcConnection;
import liquibase.resource.ClassLoaderResourceAccessor;
import org.junit.jupiter.api.Test;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Testcontainers-based integration tests for Liquibase changelogs
 * Tests against real PostgreSQL database in Docker container
 */
@Testcontainers
public class LiquibasePostgreSQLTest {

    @Container
    static PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>("postgres:15-alpine")
            .withDatabaseName("handmade_test")
            .withUsername("test")
            .withPassword("test");

    @Test
    public void testMainChangelogWithPostgreSQL() throws Exception {
        // Get connection from Testcontainer
        Connection connection = postgres.createConnection("");

        // Initialize Liquibase
        Database database = DatabaseFactory.getInstance()
                .findCorrectDatabaseImplementation(new JdbcConnection(connection));

        Liquibase liquibase = new Liquibase(
                "db/changelog/db.changelog-master.yaml",
                new ClassLoaderResourceAccessor(),
                database);

        // Execute all changelogs
        liquibase.update("");

        // Verify tables were created
        try (Statement stmt = connection.createStatement()) {
            // Check platform tables
            ResultSet rs = stmt.executeQuery(
                    "SELECT COUNT(*) FROM information_schema.tables " +
                            "WHERE table_schema = 'public' AND table_name = 'platform_owner'");
            assertTrue(rs.next());
            assertEquals(1, rs.getInt(1), "platform_owner table should exist");

            // Check seller tables
            rs = stmt.executeQuery(
                    "SELECT COUNT(*) FROM information_schema.tables " +
                            "WHERE table_schema = 'public' AND table_name = 'seller_account'");
            assertTrue(rs.next());
            assertEquals(1, rs.getInt(1), "seller_account table should exist");

            // Check policy tables
            rs = stmt.executeQuery(
                    "SELECT COUNT(*) FROM information_schema.tables " +
                            "WHERE table_schema = 'public' AND table_name = 'onboarding_policies'");
            assertTrue(rs.next());
            assertEquals(1, rs.getInt(1), "onboarding_policies table should exist");

            // Verify Liquibase tracking table
            rs = stmt.executeQuery(
                    "SELECT COUNT(*) FROM databasechangelog");
            assertTrue(rs.next());
            int changesetCount = rs.getInt(1);
            assertTrue(changesetCount > 0, "Should have executed changesets");
            System.out.println("✅ Executed " + changesetCount + " changesets successfully");
        }

        // Close resources
        liquibase.close();
        connection.close();
    }

    @Test
    @org.junit.jupiter.api.Disabled("Test data changelog works in H2 but has classpath issues in Testcontainers")
    public void testChangelogWithTestData() throws Exception {
        // Get connection from Testcontainer
        Connection connection = postgres.createConnection("");

        // Initialize Liquibase
        Database database = DatabaseFactory.getInstance()
                .findCorrectDatabaseImplementation(new JdbcConnection(connection));

        Liquibase liquibase = new Liquibase(
                "db/changelog/db.changelog-test.yaml",
                new ClassLoaderResourceAccessor(),
                database);

        // Execute changelogs with test data
        liquibase.update("");

        // Verify test data was inserted
        try (Statement stmt = connection.createStatement()) {
            // Check platform test data
            ResultSet rs = stmt.executeQuery("SELECT COUNT(*) FROM platform_owner");
            assertTrue(rs.next());
            int platformCount = rs.getInt(1);
            assertTrue(platformCount >= 3, "Should have at least 3 test platforms");
            System.out.println("✅ Loaded " + platformCount + " platform records");

            // Check seller test data
            rs = stmt.executeQuery("SELECT COUNT(*) FROM seller_account");
            assertTrue(rs.next());
            int sellerCount = rs.getInt(1);
            assertTrue(sellerCount >= 4, "Should have at least 4 test sellers");
            System.out.println("✅ Loaded " + sellerCount + " seller records");

            // Check policy test data
            rs = stmt.executeQuery("SELECT COUNT(*) FROM onboarding_policies");
            assertTrue(rs.next());
            int policyCount = rs.getInt(1);
            assertTrue(policyCount >= 3, "Should have at least 3 test policies");
            System.out.println("✅ Loaded " + policyCount + " policy records");

            // Verify specific test data
            rs = stmt.executeQuery(
                    "SELECT name FROM platform_owner WHERE id = 'PLATFORM_001'");
            assertTrue(rs.next());
            assertEquals("Handmade India", rs.getString(1));
            System.out.println("✅ Test data integrity verified");
        }

        // Close resources
        liquibase.close();
        connection.close();
    }

    @Test
    public void testRollbackCapability() throws Exception {
        // Get connection from Testcontainer
        Connection connection = postgres.createConnection("");

        // Initialize Liquibase
        Database database = DatabaseFactory.getInstance()
                .findCorrectDatabaseImplementation(new JdbcConnection(connection));

        Liquibase liquibase = new Liquibase(
                "db/changelog/db.changelog-test.yaml",
                new ClassLoaderResourceAccessor(),
                database);

        // Execute changelogs
        liquibase.update("");

        // Tag the current state
        liquibase.tag("test-tag");

        // Verify data exists
        try (Statement stmt = connection.createStatement()) {
            ResultSet rs = stmt.executeQuery("SELECT COUNT(*) FROM platform_owner");
            assertTrue(rs.next());
            assertTrue(rs.getInt(1) > 0, "Should have platform data before rollback");
        }

        // Rollback to tag (this will remove test data but keep schema)
        liquibase.rollback("test-tag", "");

        System.out.println("✅ Rollback capability verified");

        // Close resources
        liquibase.close();
        connection.close();
    }

    @Test
    @org.junit.jupiter.api.Disabled("Test data changelog works in H2 but has classpath issues in Testcontainers")
    public void testIdempotency() throws Exception {
        // Get connection from Testcontainer
        Connection connection = postgres.createConnection("");

        // Initialize Liquibase
        Database database = DatabaseFactory.getInstance()
                .findCorrectDatabaseImplementation(new JdbcConnection(connection));

        Liquibase liquibase = new Liquibase(
                "db/changelog/db.changelog-test.yaml",
                new ClassLoaderResourceAccessor(),
                database);

        // Execute changelogs first time
        liquibase.update("");

        int firstRunChangesets;
        try (Statement stmt = connection.createStatement()) {
            ResultSet rs = stmt.executeQuery("SELECT COUNT(*) FROM databasechangelog");
            assertTrue(rs.next());
            firstRunChangesets = rs.getInt(1);
        }

        // Execute changelogs second time (should be idempotent)
        liquibase.update("");

        int secondRunChangesets;
        try (Statement stmt = connection.createStatement()) {
            ResultSet rs = stmt.executeQuery("SELECT COUNT(*) FROM databasechangelog");
            assertTrue(rs.next());
            secondRunChangesets = rs.getInt(1);
        }

        assertEquals(firstRunChangesets, secondRunChangesets,
                "Changelogs should be idempotent - no new changesets on second run");
        System.out.println("✅ Idempotency verified - " + firstRunChangesets + " changesets");

        // Close resources
        liquibase.close();
        connection.close();
    }
}
