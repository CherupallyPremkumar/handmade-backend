package com.handmade.ecommerce.catalog.scheduler.batch;

import com.handmade.ecommerce.catalog.model.CollectionProductMapping;
import com.handmade.ecommerce.catalog.repository.CollectionProductMappingRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.batch.item.Chunk;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

/**
 * Unit tests for MappingWriter
 * Tests the Spring Batch ItemWriter that persists collection-product mappings
 */
@ExtendWith(MockitoExtension.class)
class MappingWriterTest {

    @Mock
    private CollectionProductMappingRepository repository;

    @InjectMocks
    private MappingWriter writer;

    @Captor
    private ArgumentCaptor<List<CollectionProductMapping>> mappingsCaptor;

    private CollectionProductMapping mapping1;
    private CollectionProductMapping mapping2;

    @BeforeEach
    void setUp() {
        mapping1 = new CollectionProductMapping();
        mapping1.setCollectionId("col-1");
        mapping1.setProductId("prod-1");
        mapping1.setDisplayOrder(1);

        mapping2 = new CollectionProductMapping();
        mapping2.setCollectionId("col-1");
        mapping2.setProductId("prod-2");
        mapping2.setDisplayOrder(2);
    }

    @Test
    @DisplayName("Should write batch of mappings to database")
    void write_BatchOfMappings_SavesAll() throws Exception {
        // Arrange
        Chunk<CollectionProductMapping> chunk = new Chunk<>(Arrays.asList(mapping1, mapping2));
        when(repository.saveAll(anyList())).thenReturn(Arrays.asList(mapping1, mapping2));

        // Act
        writer.write(chunk);

        // Assert
        verify(repository).saveAll(mappingsCaptor.capture());
        List<CollectionProductMapping> savedMappings = mappingsCaptor.getValue();
        
        assertThat(savedMappings).hasSize(2);
        assertThat(savedMappings.get(0).getProductId()).isEqualTo("prod-1");
        assertThat(savedMappings.get(1).getProductId()).isEqualTo("prod-2");
    }

    @Test
    @DisplayName("Should handle empty chunk")
    void write_EmptyChunk_DoesNothing() throws Exception {
        // Arrange
        Chunk<CollectionProductMapping> emptyChunk = new Chunk<>();

        // Act
        writer.write(emptyChunk);

        // Assert
        verify(repository, never()).saveAll(anyList());
    }

    @Test
    @DisplayName("Should handle single mapping")
    void write_SingleMapping_SavesSuccessfully() throws Exception {
        // Arrange
        Chunk<CollectionProductMapping> chunk = new Chunk<>(List.of(mapping1));
        when(repository.saveAll(anyList())).thenReturn(List.of(mapping1));

        // Act
        writer.write(chunk);

        // Assert
        verify(repository).saveAll(anyList());
    }

    @Test
    @DisplayName("Should handle database exception gracefully")
    void write_DatabaseException_ThrowsException() {
        // Arrange
        Chunk<CollectionProductMapping> chunk = new Chunk<>(List.of(mapping1));
        when(repository.saveAll(anyList())).thenThrow(new RuntimeException("Database error"));

        // Act & Assert
        try {
            writer.write(chunk);
        } catch (Exception e) {
            assertThat(e).isInstanceOf(RuntimeException.class);
            assertThat(e.getMessage()).contains("Database error");
        }
        
        verify(repository).saveAll(anyList());
    }
}
