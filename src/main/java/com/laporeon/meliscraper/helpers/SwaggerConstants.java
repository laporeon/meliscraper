package com.laporeon.meliscraper.helpers;

public class SwaggerConstants {

        public static final String SNAPSHOT_RESPONSE_EXAMPLE = """
        {
            "id": "5fb2d3ca-3d80-40c2-a4f1-37dce426c9d2",
            "snapshotDate": "2025-12-01",
            "categories": [
                {
                    "name": "Games",
                    "slug": "games",
                    "products": [
                        {
                            "position": 1,
                            "name": "Controle joystick sem fio Sony PlayStation Dualshock 4 Wireless controller alpine green",
                            "image": "https://http2.mlstatic.com/D_Q_NP_920572-MLA99508236810_112025-P.webp",
                            "price": 281,
                            "link": "https://www.mercadolivre.com.br/controle-joystick-sem-fio-sony-playstation-dualshock-4-wireless-controller-alpine-green/p/MLB15085751"
                        },
                        {
                            "position": 2,
                            "name": "Controle DualSense Sem Fio PlayStation 5 Joystick Starlight Blue Sony CFI-ZCT1W",
                            "image": "https://http2.mlstatic.com/D_Q_NP_866848-MLA96150740853_102025-P.webp",
                            "price": 438.18,
                            "link": "https://www.mercadolivre.com.br/controle-dualsense-sem-fio-playstation-5-joystick-starlight-blue-sony-cfi-zct1w/p/MLB18910381"
                        }
                    ]
                },
                {
                    "name": "Informática",
                    "slug": "informatica",
                    "products": [
                        {
                            "position": 1,
                            "name": "Mochila Reforçada Pack Young Meimi Amores Notebook 15.6 Cor Preto",
                            "image": "https://http2.mlstatic.com/D_Q_NP_751105-MLA99906883639_112025-P.webp",
                            "price": 60.72,
                            "link": "https://www.mercadolivre.com.br/mochila-reforcada-pack-young-meimi-amores-notebook-156-cor-preto/p/MLB28665287"
                        },
                        {
                            "position": 2,
                            "name": "Controle de joystick sem fio Dualsense Ps5 azul cobalto",
                            "image": "Controle de joystick sem fio Dualsense Ps5 azul cobalto   ",
                            "price": 525.54,
                            "link": "https://www.mercadolivre.com.br/controle-de-joystick-sem-fio-dualsense-ps5-azul-cobalto/p/MLB28083897"
                        }
                    ]
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
