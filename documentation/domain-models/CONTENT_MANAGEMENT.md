# Content Management Module

## 🎯 Overview
The **Content Management** module handles all non-transactional content for the platform, including static pages, blog posts, banners, and media assets. It enables the platform to engage customers with rich storytelling, artisan profiles, and educational content.

## 🏗️ Domain Models

### **1. Page** (Entity)
**Purpose**: Represents static content pages.
- **Key Fields**: `slug`, `title`, `content`, `status` (DRAFT, PUBLISHED), `metaTitle`, `metaDescription`.
- **Use Cases**: About Us, FAQ, Terms & Conditions, Privacy Policy.

### **2. Banner** (Entity)
**Purpose**: Manages promotional banners and visual highlights.
- **Key Fields**: `imageUrl`, `linkUrl`, `position` (HERO, SIDEBAR, FOOTER), `startDate`, `endDate`.
- **Use Cases**: Homepage hero slider, seasonal sale banners, category headers.

### **3. BlogPost** (Entity)
**Purpose**: Manages articles, stories, and guides.
- **Key Fields**: `slug`, `title`, `content`, `authorId`, `tags`, `featuredImageUrl`.
- **Use Cases**: "Meet the Maker" stories, craft tutorials, product care guides.

### **4. Media** (Entity)
**Purpose**: Centralized asset management.
- **Key Fields**: `fileUrl`, `type` (IMAGE, VIDEO, DOCUMENT), `mimeType`, `altText`.
- **Use Cases**: Storing product images, banner images, downloadable PDF guides.

## 🚀 Key Features
- **SEO Optimization**: Built-in support for slugs, meta titles, and descriptions.
- **Scheduling**: Banners can be scheduled with start and end dates.
- **Draft/Publish Workflow**: Content can be drafted and reviewed before publishing.
- **Rich Media**: Support for various media types to enhance storytelling.

## 🔗 Integrations
- **Frontend**: API provides content for rendering dynamic pages and homepages.
- **Search**: Blog posts and pages can be indexed for site search.
