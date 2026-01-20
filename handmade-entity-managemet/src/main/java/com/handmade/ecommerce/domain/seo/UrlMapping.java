package com.handmade.ecommerce.domain.seo;

import jakarta.persistence.*;
import lombok.*;
import org.chenile.jpautils.entity.BaseJpaEntity;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "hm_url_mapping")
public class UrlMapping extends BaseJpaEntity {

    @Column(name = "custom_url", length = 500, nullable = false, unique = true)
    private String customUrl;

    @Column(name = "target_path", length = 1000, nullable = false)
    private String targetPath;

    @Column(name = "mapping_type", length = 50)
    private String mappingType; // PRODUCT, CATEGORY, CMS_PAGE

    @Column(name = "is_redirect")
    private Boolean isRedirect = false;
}
