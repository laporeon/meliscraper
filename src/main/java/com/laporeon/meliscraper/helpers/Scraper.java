package com.laporeon.meliscraper.helpers;

import lombok.SneakyThrows;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
public class Scraper {

    public static final String APP_URL = "https://www.mercadolivre.com.br/mais-vendidos";
    public static final String CATEGORIES_SECTION_CLASS = ".dynamic__carousel";
    public static final String CATEGORY_TITLE_CLASS = ".dynamic-carousel__container--with-link > h2";

    @SneakyThrows
    public static List<Map<String, String>> scrapeCategories() {
        Document doc = Jsoup.connect(APP_URL).get();
        List<Element> sections = doc.select(CATEGORIES_SECTION_CLASS);

        List<Map<String, String>> categories = new ArrayList<>();

        for (Element section : sections) {
            String title = section.select(CATEGORY_TITLE_CLASS).text();
            String slug = SlugifyCategoryName.slugify(title);

            Map<String, String> category = new HashMap<>();
            category.put("title", title);
            category.put("slug", slug);

            categories.add(category);
        }

        return categories;
    }
}