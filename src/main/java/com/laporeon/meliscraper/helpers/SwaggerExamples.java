package com.laporeon.meliscraper.helpers;

public class SwaggerExamples {

    // SNAPSHOTS
    public static final String SNAPSHOT_SUMMARY_PAGE_RESPONSE = """
            {
              "content": [
                {
                  "id": "b3717cfb-9592-4f2f-9dad-1d562ee57ece",
                  "snapshotDate": "2025-12-02"
                },
                {
                  "id": "4f1d52c5-d5f5-4853-847b-2571c79251bd",
                  "snapshotDate": "2026-01-06"
                },
                {
                  "id": "4f30aee5-43dc-45f1-8f10-a6504aa386cf",
                  "snapshotDate": "2026-01-07"
                }
              ],
              "pageNumber": 0,
              "pageSize": 10,
              "totalPages": 1,
              "totalElements": 3,
              "numberOfElements": 3,
              "isFirstPage": true,
              "isLastPage": true,
              "isEmpty": false,
              "isSorted": true,
              "isUnsorted": false
            }
            """;

    public static final String SNAPSHOT_DETAIL_RESPONSE = """
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
                                "image": "https://http2.mlstatic.com/D_Q_NP_869028-MLA9993527_112025-P.webp",
                                "price": 525.54,
                                "link": "https://www.mercadolivre.com.br/controle-de-joystick-sem-fio-dualsense-ps5-azul-cobalto/p/MLB28083897"
                            }
                        ]
                    }
                ]
            }
            """;

    public static final String SNAPSHOT_NOT_FOUND_ERROR_MESSAGE = """
            {
                "status": 404,
                "message": "No snapshot found for date '2025-12-02'",
                "timestamp": "2025-12-02T16:20:26.685371366Z"
            }
            """;

    // CATEGORIES

    public static final String CATEGORY_SUMMARY_PAGE_RESPONSE = """
            {
              "content": [
                {
                  "name": "Beleza e Cuidado Pessoal",
                  "slug": "beleza-e-cuidado-pessoal"
                },
                {
                  "name": "Calçados, Roupas e Bolsas",
                  "slug": "calcados-roupas-e-bolsas"
                },
                {
                  "name": "Eletrodomésticos",
                  "slug": "eletrodomesticos"
                },
                {
                  "name": "Eletrônicos, Áudio e Vídeo",
                  "slug": "eletronicos-audio-e-video"
                },
                {
                  "name": "Esportes e Fitness",
                  "slug": "esportes-e-fitness"
                },
                {
                  "name": "Games",
                  "slug": "games"
                },
                {
                  "name": "Informática",
                  "slug": "informatica"
                }
              ],
              "pageNumber": 0,
              "pageSize": 10,
              "totalPages": 1,
              "totalElements": 7,
              "numberOfElements": 7,
              "isFirstPage": true,
              "isLastPage": true,
              "isEmpty": false,
              "isSorted": true,
              "isUnsorted": false
            }
            """;

    public static final String CATEGORY_NOT_FOUND_ERROR_MESSAGE = """
            {
                "status": 404,
                "message": "Category 'dispositivos' not found. Check for available categories on: /categories",
                "timestamp": "2025-12-02T16:20:26.685371366Z"
            }
            """;

    public static final String CATEGORY_PRODUCTS_PAGE_RESPONSE = """
            {
              "content": [
                {
                  "name": "5 Lampada Led Halopim G9 12w 88 Led Lustre Arandela Cor da luz Branco-quente 12W Goldensky",
                  "price": 43.03,
                  "link": "https://www.mercadolivre.com.br/5-lampada-led-halopim-g9-12w-88-led-lustre-arandela-cor-da-luz-branco-quente-12w-goldensky/p/MLB27183943",
                  "snapshotDate": "2025-12-02"
                },
                {
                  "name": "6x Lâmpada Halopin Led G9 5w 52 Leds Para Lustres E Arandela Cor da luz Branco-frio Goldensky",
                  "price": 27.48,
                  "link": "https://www.mercadolivre.com.br/6x-lmpada-halopin-led-g9-5w-52-leds-para-lustres-e-arandela-cor-da-luz-branco-frio-goldensky/p/MLB27622803",
                  "snapshotDate": "2026-01-06"
                },
                {
                  "name": "Aspirador de Pó e Água WAP GTW 10 1400W de Potência com Bocal de Sopro",
                  "price": 299.9,
                  "link": "https://www.mercadolivre.com.br/aspirador-de-po-e-agua-wap-gtw-10-1400w-de-potncia-com-bocal-de-sopro/p/MLB8923631",
                  "snapshotDate": "2026-01-06"
                },
                {
                  "name": "Aspirador De Pó e Água Wap Gtw 10 Compacto 1400w 10 Litros Amarelo/Preto",
                  "price": 239.9,
                  "link": "https://www.mercadolivre.com.br/aspirador-de-po-e-agua-wap-gtw-10-compacto-1400w-10-litros-amarelopreto/p/MLB8923630",
                  "snapshotDate": "2026-01-07"
                },
                {
                  "name": "Aspirador De Pó e Água Wap Gtw 10 Compacto 1400w 10 Litros Amarelo/Preto",
                  "price": 249.9,
                  "link": "https://www.mercadolivre.com.br/aspirador-de-po-e-agua-wap-gtw-10-compacto-1400w-10-litros-amarelopreto/p/MLB8923630",
                  "snapshotDate": "2025-12-02"
                },
                {
                  "name": "Aspirador De Pó Vertical E Portátil Wap High Speed Plus 1350w 1,2 Litros Filtro Hepa Tecnologia Cyclone 3 Em 1",
                  "price": 126,
                  "link": "https://www.mercadolivre.com.br/aspirador-de-po-vertical-e-portatil-wap-high-speed-plus-1350w-12-litros-filtro-hepa-tecnologia-cyclone-3-em-1/p/MLB23414010",
                  "snapshotDate": "2026-01-07"
                },
                {
                  "name": "Cafeteira Nescafé Dolce Gusto Mini Me Vermelha e Preta",
                  "price": 439.9,
                  "link": "https://www.mercadolivre.com.br/cafeteira-nescafe-dolce-gusto-mini-me-vermelha-e-preta/p/MLB15154784",
                  "snapshotDate": "2025-12-02"
                },
                {
                  "name": "Chaleira Eletrica Atacama 1,8l - Unitermi",
                  "price": 49.49,
                  "link": "https://www.mercadolivre.com.br/chaleira-eletrica-atacama-18l-unitermi/p/MLB13409956",
                  "snapshotDate": "2026-01-07"
                },
                {
                  "name": "Climatizador FAN portátil umidificador FYF branco",
                  "price": 49.69,
                  "link": "https://www.mercadolivre.com.br/climatizador-fan-portatil-umidificador-fyf-branco/p/MLB27965580",
                  "snapshotDate": "2026-01-07"
                },
                {
                  "name": "Electrolux Efficient Esi10 a vapor e ferro seco, cor azul",
                  "price": 104.76,
                  "link": "https://www.mercadolivre.com.br/electrolux-efficient-esi10-a-vapor-e-ferro-seco-cor-azul/p/MLB25396633",
                  "snapshotDate": "2026-01-06"
                }
              ],
              "pageNumber": 0,
              "pageSize": 10,
              "totalPages": 5,
              "totalElements": 50,
              "numberOfElements": 10,
              "isFirstPage": true,
              "isLastPage": false,
              "isEmpty": false,
              "isSorted": true,
              "isUnsorted": false
            }
            """;

    // COMMON

    public static final String ERROR_TOO_MANY_REQUESTS = """
            {
                "status": 429,
                "message": "Too many requests. Please try again later.",
                "timestamp": "2025-12-02T16:20:26.685371366Z"
            }
            """;

    public static final String ERROR_INTERNAL_SERVER = """
            {
                "status": 500,
                "message": "An unexpected error occurred",
                "timestamp": "2025-12-02T16:20:26.685371366Z"
            }
            """;

}
