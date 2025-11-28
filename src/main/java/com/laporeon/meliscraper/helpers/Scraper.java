package com.laporeon.meliscraper.helpers;

import com.laporeon.meliscraper.dtos.CategoryDTO;
import lombok.SneakyThrows;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class Scraper {

    public static final String APP_URL = "https://www.mercadolivre.com.br/mais-vendidos";
    public static final String CATEGORIES_SECTION_CLASS = ".dynamic__carousel";
    public static final String CATEGORY_TITLE_CLASS = ".dynamic-carousel__container--with-link > h2";

    @SneakyThrows
    public List<CategoryDTO> scrapeCategories() {
        Document doc = Jsoup.connect(APP_URL).get();
        Elements sections = doc.select(CATEGORIES_SECTION_CLASS);

        return sections.stream()
                       .flatMap(section -> section.select(CATEGORY_TITLE_CLASS).stream())
                       .map(element -> new CategoryDTO(
                               element.text(),
                               SlugifyCategoryName.slugify(element.text())
                       ))
                       .toList();
    }
}
