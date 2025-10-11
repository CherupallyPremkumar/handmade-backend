package com.homebase.admin.service;

import com.homebase.admin.dto.TagDTO;
import com.homebase.admin.entity.Tag;
import com.homebase.admin.config.TenantContext;
import com.homebase.admin.repository.TagRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class TagService {

    private final TagRepository tagRepository;

    public TagService(TagRepository tagRepository) {
        this.tagRepository = tagRepository;
    }

    public List<TagDTO> getAllTags() {
        String tenantId = TenantContext.getCurrentTenant();
        return tagRepository.findByTenantId(tenantId).stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    @Transactional
    public TagDTO createTag(TagDTO tagDTO) {
        Tag tag = convertToEntity(tagDTO);
        Tag saved = tagRepository.save(tag);
        return convertToDTO(saved);
    }

    @Transactional
    public void deleteTag(String id) {
        String tenantId = TenantContext.getCurrentTenant();
        Tag tag = tagRepository.findByIdAndTenantId(id, tenantId)
                .orElseThrow(() -> new RuntimeException("Tag not found"));
        tagRepository.delete(tag);
    }

    private TagDTO convertToDTO(Tag tag) {
        TagDTO dto = new TagDTO();
        dto.setId(String.valueOf(tag.getId()));
        dto.setName(tag.getName());
        dto.setSlug(tag.getSlug());
        return dto;
    }

    private Tag convertToEntity(TagDTO dto) {
        Tag tag = new Tag();
        tag.setName(dto.getName());
        tag.setSlug(dto.getSlug());
        return tag;
    }
}
