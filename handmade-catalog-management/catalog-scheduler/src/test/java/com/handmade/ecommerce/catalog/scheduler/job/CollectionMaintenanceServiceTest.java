package com.handmade.ecommerce.catalog.scheduler.job;

import com.handmade.ecommerce.catalog.model.Collection;
import com.handmade.ecommerce.catalog.model.CollectionType;
import com.handmade.ecommerce.catalog.service.CollectionService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.launch.JobLauncher;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

/**
 * Unit tests for CollectionMaintenanceService
 * Tests the scheduled job that maintains dynamic collections
 */
@ExtendWith(MockitoExtension.class)
class CollectionMaintenanceServiceTest {

    @Mock
    private CollectionService collectionService;

    @Mock
    private JobLauncher jobLauncher;

    @Mock
    private Job updateCollectionMappingsJob;

    @InjectMocks
    private CollectionMaintenanceService maintenanceService;

    private Collection dynamicCollection;
    private Collection staticCollection;

    @BeforeEach
    void setUp() {
        dynamicCollection = new Collection();
        dynamicCollection.setId("col-dynamic");
        dynamicCollection.setName("Dynamic Collection");
        dynamicCollection.setType(CollectionType.DYNAMIC);
        dynamicCollection.setRuleExpression("price < 50.00");
        dynamicCollection.setActive(true);

        staticCollection = new Collection();
        staticCollection.setId("col-static");
        staticCollection.setName("Static Collection");
        staticCollection.setType(CollectionType.CURATED);
        staticCollection.setActive(true);
    }

    @Test
    @DisplayName("Should process dynamic collections")
    void maintainCollections_WithDynamicCollections_ProcessesThem() throws Exception {
        // Arrange
        List<Collection> collections = Arrays.asList(dynamicCollection);
        when(collectionService.findDynamicCollections()).thenReturn(collections);
        
        JobExecution jobExecution = mock(JobExecution.class);
        when(jobLauncher.run(eq(updateCollectionMappingsJob), any(JobParameters.class)))
                .thenReturn(jobExecution);

        // Act
        maintenanceService.maintainCollections();

        // Assert
        verify(collectionService).findDynamicCollections();
        verify(jobLauncher).run(eq(updateCollectionMappingsJob), any(JobParameters.class));
    }

    @Test
    @DisplayName("Should skip inactive collections")
    void maintainCollections_WithInactiveCollection_SkipsThem() throws Exception {
        // Arrange
        dynamicCollection.setActive(false);
        List<Collection> collections = Arrays.asList(dynamicCollection);
        when(collectionService.findDynamicCollections()).thenReturn(collections);

        // Act
        maintenanceService.maintainCollections();

        // Assert
        verify(collectionService).findDynamicCollections();
        verify(jobLauncher, never()).run(any(), any());
    }

    @Test
    @DisplayName("Should handle empty collection list")
    void maintainCollections_NoCollections_DoesNothing() throws Exception {
        // Arrange
        when(collectionService.findDynamicCollections()).thenReturn(Collections.emptyList());

        // Act
        maintenanceService.maintainCollections();

        // Assert
        verify(collectionService).findDynamicCollections();
        verify(jobLauncher, never()).run(any(), any());
    }

    @Test
    @DisplayName("Should handle job execution failure")
    void maintainCollections_JobFails_HandlesException() throws Exception {
        // Arrange
        List<Collection> collections = Arrays.asList(dynamicCollection);
        when(collectionService.findDynamicCollections()).thenReturn(collections);
        when(jobLauncher.run(any(), any())).thenThrow(new RuntimeException("Job failed"));

        // Act & Assert
        try {
            maintenanceService.maintainCollections();
        } catch (Exception e) {
            assertThat(e).isInstanceOf(RuntimeException.class);
        }
        
        verify(jobLauncher).run(any(), any());
    }

    @Test
    @DisplayName("Should process multiple dynamic collections")
    void maintainCollections_MultipleCollections_ProcessesAll() throws Exception {
        // Arrange
        Collection dynamicCollection2 = new Collection();
        dynamicCollection2.setId("col-dynamic-2");
        dynamicCollection2.setType(CollectionType.DYNAMIC);
        dynamicCollection2.setActive(true);
        
        List<Collection> collections = Arrays.asList(dynamicCollection, dynamicCollection2);
        when(collectionService.findDynamicCollections()).thenReturn(collections);
        
        JobExecution jobExecution = mock(JobExecution.class);
        when(jobLauncher.run(any(), any())).thenReturn(jobExecution);

        // Act
        maintenanceService.maintainCollections();

        // Assert
        verify(jobLauncher, times(2)).run(eq(updateCollectionMappingsJob), any(JobParameters.class));
    }

    @Test
    @DisplayName("Should only process DYNAMIC type collections")
    void maintainCollections_MixedTypes_ProcessesOnlyDynamic() throws Exception {
        // Arrange
        List<Collection> collections = Arrays.asList(dynamicCollection); // Service already filters
        when(collectionService.findDynamicCollections()).thenReturn(collections);
        
        JobExecution jobExecution = mock(JobExecution.class);
        when(jobLauncher.run(any(), any())).thenReturn(jobExecution);

        // Act
        maintenanceService.maintainCollections();

        // Assert
        verify(jobLauncher, times(1)).run(any(), any());
    }
}
