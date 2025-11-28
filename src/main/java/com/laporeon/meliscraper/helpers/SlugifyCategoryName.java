package com.laporeon.meliscraper.helpers;

import org.springframework.stereotype.Component;

import java.text.Normalizer;

@Component
public class SlugifyCategoryName {

    public static String slugify(String name) {
        return Normalizer.normalize(name, Normalizer.Form.NFD)
                         .replaceAll("\\p{InCombiningDiacriticalMarks}+", "")
                         .toLowerCase()
                         .replace(" ", "-")
                         .replaceAll("[^\\w-]+", "");
    }
}
