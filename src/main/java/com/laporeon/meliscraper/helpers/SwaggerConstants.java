package com.laporeon.meliscraper.helpers;

public class SwaggerConstants {

        public static final String SNAPSHOT_RESPONSE_EXAMPLE = """
        {
            "id": "5fb2d3ca-3d80-40c2-a4f1-37dce426c9d2",
            "snapshotDate": "2025-12-01",
            "categories": [
                {
                    "name": "Games",
                    "slug": "games"
                },
                {
                    "name": "Informática",
                    "slug": "informatica"
                },
                {
                    "name": "Esportes e Fitness",
                    "slug": "esportes-e-fitness"
                }
            ]
        }
        """;

        public static final String INTERNAL_ERROR_MESSAGE = """
        {
            "status": 500,
            "type": "INTERNAL_SERVER_ERROR",
            "message": "An unexpected error occurred",
            "timestamp": "2025-10-29T15:19:52.121160501Z"
        }
        """;
}
