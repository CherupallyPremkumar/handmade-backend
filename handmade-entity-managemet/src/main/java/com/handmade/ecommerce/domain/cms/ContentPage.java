package com.handmade.ecommerce.domain.cms;

import jakarta.persistence.*;
import lombok.*;
import org.chenile.jpautils.entity.AbstractJpaStateEntity;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "hm_content_page")
public class ContentPage extends AbstractJpaStateEntity {

    @Column(name = "slug", length = 255, nullable = false, unique = true)
    private String slug;

    @Column(name = "title", length = 255, nullable = false)
    private String title;

    @Column(name = "content_type", length = 50)
    private String contentType; // FAQ, HELP, STATIC_PAGE

    @Column(name = "raw_content", columnDefinition = "TEXT")
    private String rawContent;

    @Column(name = "status", length = 20)
    private String status = "DRAFT";

    @Column(name = "meta_description", length = 500)
    private String metaDescription;
}
