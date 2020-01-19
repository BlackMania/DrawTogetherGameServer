package com.apihandler;

import org.json.JSONObject;
import org.junit.Test;

public class GameHistoryAPITest {
    @Test
    public void test()
    {
        GameHistoryAPI api = new GameHistoryAPI();
        api.uploadGameHistory(new JSONObject("{\n" +
                "    \"gameData\": [\n" +
                "        {\n" +
                "            \"gameId\": \"1a431cbf-1674-11ea-833f-02004c4f4f50\",\n" +
                "            \"players\": [\n" +
                "                \"Second\",\n" +
                "                \"test\"\n" +
                "            ],\n" +
                "            \"rounds\": [\n" +
                "                {\n" +
                "                    \"drawdata\": [\n" +
                "                        {\n" +
                "                            \"strokestyle\": \"black\",\n" +
                "                            \"prevy\": 2,\n" +
                "                            \"prevx\": 2,\n" +
                "                            \"currx\": 2,\n" +
                "                            \"curry\": 2,\n" +
                "                            \"linewidth\": 2,\n" +
                "                            \"timestamp\": \"2019-12-06 03:12:08.0\"\n" +
                "                        },\n" +
                "                        {\n" +
                "                            \"strokestyle\": \"black\",\n" +
                "                            \"prevy\": 2,\n" +
                "                            \"prevx\": 2,\n" +
                "                            \"currx\": 2,\n" +
                "                            \"curry\": 2,\n" +
                "                            \"linewidth\": 2,\n" +
                "                            \"timestamp\": \"2019-12-06 03:12:08.0\"\n" +
                "                        },\n" +
                "                        {\n" +
                "                            \"strokestyle\": \"black\",\n" +
                "                            \"prevy\": 2,\n" +
                "                            \"prevx\": 2,\n" +
                "                            \"currx\": 2,\n" +
                "                            \"curry\": 2,\n" +
                "                            \"linewidth\": 2,\n" +
                "                            \"timestamp\": \"2019-12-06 03:12:08.0\"\n" +
                "                        },\n" +
                "                        {\n" +
                "                            \"strokestyle\": \"black\",\n" +
                "                            \"prevy\": 2,\n" +
                "                            \"prevx\": 2,\n" +
                "                            \"currx\": 2,\n" +
                "                            \"curry\": 2,\n" +
                "                            \"linewidth\": 2,\n" +
                "                            \"timestamp\": \"2019-12-06 03:12:08.0\"\n" +
                "                        },\n" +
                "                        {\n" +
                "                            \"strokestyle\": \"black\",\n" +
                "                            \"prevy\": 2,\n" +
                "                            \"prevx\": 2,\n" +
                "                            \"currx\": 2,\n" +
                "                            \"curry\": 2,\n" +
                "                            \"linewidth\": 2,\n" +
                "                            \"timestamp\": \"2019-12-06 03:12:08.0\"\n" +
                "                        },\n" +
                "                        {\n" +
                "                            \"strokestyle\": \"black\",\n" +
                "                            \"prevy\": 2,\n" +
                "                            \"prevx\": 2,\n" +
                "                            \"currx\": 2,\n" +
                "                            \"curry\": 2,\n" +
                "                            \"linewidth\": 2,\n" +
                "                            \"timestamp\": \"2019-12-06 03:12:08.0\"\n" +
                "                        },\n" +
                "                        {\n" +
                "                            \"strokestyle\": \"black\",\n" +
                "                            \"prevy\": 2,\n" +
                "                            \"prevx\": 2,\n" +
                "                            \"currx\": 2,\n" +
                "                            \"curry\": 2,\n" +
                "                            \"linewidth\": 2,\n" +
                "                            \"timestamp\": \"2019-12-06 03:12:08.0\"\n" +
                "                        },\n" +
                "                        {\n" +
                "                            \"strokestyle\": \"black\",\n" +
                "                            \"prevy\": 2,\n" +
                "                            \"prevx\": 2,\n" +
                "                            \"currx\": 2,\n" +
                "                            \"curry\": 2,\n" +
                "                            \"linewidth\": 2,\n" +
                "                            \"timestamp\": \"2019-12-06 03:12:08.0\"\n" +
                "                        },\n" +
                "                        {\n" +
                "                            \"strokestyle\": \"black\",\n" +
                "                            \"prevy\": 2,\n" +
                "                            \"prevx\": 2,\n" +
                "                            \"currx\": 2,\n" +
                "                            \"curry\": 2,\n" +
                "                            \"linewidth\": 2,\n" +
                "                            \"timestamp\": \"2019-12-06 03:12:08.0\"\n" +
                "                        },\n" +
                "                        {\n" +
                "                            \"strokestyle\": \"black\",\n" +
                "                            \"prevy\": 2,\n" +
                "                            \"prevx\": 2,\n" +
                "                            \"currx\": 2,\n" +
                "                            \"curry\": 2,\n" +
                "                            \"linewidth\": 2,\n" +
                "                            \"timestamp\": \"2019-12-06 03:12:08.0\"\n" +
                "                        },\n" +
                "                        {\n" +
                "                            \"strokestyle\": \"black\",\n" +
                "                            \"prevy\": 2,\n" +
                "                            \"prevx\": 2,\n" +
                "                            \"currx\": 2,\n" +
                "                            \"curry\": 2,\n" +
                "                            \"linewidth\": 2,\n" +
                "                            \"timestamp\": \"2019-12-06 03:12:08.0\"\n" +
                "                        },\n" +
                "                        {\n" +
                "                            \"strokestyle\": \"black\",\n" +
                "                            \"prevy\": 2,\n" +
                "                            \"prevx\": 2,\n" +
                "                            \"currx\": 2,\n" +
                "                            \"curry\": 2,\n" +
                "                            \"linewidth\": 2,\n" +
                "                            \"timestamp\": \"2019-12-06 03:12:08.0\"\n" +
                "                        },\n" +
                "                        {\n" +
                "                            \"strokestyle\": \"black\",\n" +
                "                            \"prevy\": 2,\n" +
                "                            \"prevx\": 2,\n" +
                "                            \"currx\": 2,\n" +
                "                            \"curry\": 2,\n" +
                "                            \"linewidth\": 2,\n" +
                "                            \"timestamp\": \"2019-12-06 03:12:08.0\"\n" +
                "                        },\n" +
                "                        {\n" +
                "                            \"strokestyle\": \"black\",\n" +
                "                            \"prevy\": 2,\n" +
                "                            \"prevx\": 2,\n" +
                "                            \"currx\": 2,\n" +
                "                            \"curry\": 2,\n" +
                "                            \"linewidth\": 2,\n" +
                "                            \"timestamp\": \"2019-12-06 03:12:08.0\"\n" +
                "                        },\n" +
                "                        {\n" +
                "                            \"strokestyle\": \"black\",\n" +
                "                            \"prevy\": 2,\n" +
                "                            \"prevx\": 2,\n" +
                "                            \"currx\": 2,\n" +
                "                            \"curry\": 2,\n" +
                "                            \"linewidth\": 2,\n" +
                "                            \"timestamp\": \"2019-12-06 03:12:08.0\"\n" +
                "                        }\n" +
                "                    ],\n" +
                "                    \"round\": 1\n" +
                "                },\n" +
                "                {\n" +
                "                    \"drawdata\": [],\n" +
                "                    \"round\": 2\n" +
                "                }\n" +
                "            ]\n" +
                "        },\n" +
                "        {\n" +
                "            \"gameId\": \"9c6ea6bd-1812-11ea-833f-02004c4f4f50\",\n" +
                "            \"players\": [\n" +
                "                \"Third\",\n" +
                "                \"test\"\n" +
                "            ],\n" +
                "            \"rounds\": [\n" +
                "                {\n" +
                "                    \"drawdata\": [],\n" +
                "                    \"round\": 3\n" +
                "                }\n" +
                "            ]\n" +
                "        }\n" +
                "    ]\n" +
                "}"));
    }
}
