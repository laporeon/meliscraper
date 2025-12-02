package com.laporeon.meliscraper.helpers;

import com.laporeon.meliscraper.dtos.CategoryDTO;
import com.laporeon.meliscraper.dtos.ProductDTO;
import lombok.SneakyThrows;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

@Component
public class Scraper {

    public static final String APP_URL = "https://www.mercadolivre.com.br/mais-vendidos";
    public static final String CATEGORIES_SECTION_CLASS = ".dynamic__carousel";
    public static final String CATEGORY_TITLE_CLASS = ".dynamic-carousel__container--with-link > h2";
    public static final String PRODUCT_CARD_CLASS = ".dynamic-carousel__item-container";
    public static final String PRODUCT_NAME_CLASS = ".dynamic-carousel__title";
    public static final String PRODUCT_IMAGE_CLASS = ".dynamic-carousel__img";
    public static final String PRODUCT_MAIN_PRICE_CLASS = ".dynamic-carousel__price > span";
    public static final String PRODUCT_DECIMAL_PRICE_CLASS = ".dynamic-carousel__price-decimals";
    public static final String PRODUCT_LINK_CLASS = ".dynamic-carousel__item-container > a";

    @SneakyThrows
    public static List<CategoryDTO> scrape() {
        Document doc = Jsoup.connect(APP_URL).get();
        List<Element> sections = doc.select(CATEGORIES_SECTION_CLASS);

        return sections.stream()
                       .map(section -> {
                           String title = section.select(CATEGORY_TITLE_CLASS).text();
                           String slug = SlugifyCategoryName.slugify(title);

                           Elements productElements = section.select(PRODUCT_CARD_CLASS);
                           List<ProductDTO> products = productElements.stream()
                                                                      .map(product -> new ProductDTO(
                                                                              productElements.indexOf(product) + 1,
                                                                              product.select(PRODUCT_NAME_CLASS).text(),
                                                                              extractImage(product),
                                                                              extractPrice(product),
                                                                              product.select(PRODUCT_LINK_CLASS).attr("href")
                                                                      ))
                                                                      .toList();

                           return new CategoryDTO(title, slug, products);
                       })
                       .toList();
    }

    private static String extractImage(Element product) {
        Element img = product.select(PRODUCT_IMAGE_CLASS).first();

        if(!img.attr("data-src").isEmpty()) {
            return img.attr("data-src");
        }

        return img.attr("src");

    }

    private static BigDecimal extractPrice(Element product) {
        String main = product.select(PRODUCT_MAIN_PRICE_CLASS)
                             .text()
                             .replaceAll("[^\\d]", "");

        Elements decimalElements = product.select(PRODUCT_DECIMAL_PRICE_CLASS);
        String decimal = decimalElements.isEmpty() ? "00" : decimalElements.first().text().replaceAll("[^\\d]", "");

        String fullPrice = main + decimal;
        return new BigDecimal(fullPrice).divide(BigDecimal.TEN.pow(2), 2, RoundingMode.HALF_UP);
    }

}