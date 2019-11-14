package com.catherine.kotlinboilerplate

import com.catherine.kotlinboilerplate.data.Album

class TestUtils {
    companion object {
        fun createRandomAlbums(num: Int = 1): List<Album> {
            val list = ArrayList<Album>()

            for (i in 0 until num) {
                list.add(
                    Album(
                        i + 1, // "autoGenerate" id starts from 1
                        "title1234567890title1234567890",
                        "artist1234567890artist1234567890",
                        null,
                        null,
                        null
                    )
                )
            }
            return list
        }


        val mockAlbums = "[\n" +
                "  {\n" +
                "    \"title\": \"Taylor Swift\",\n" +
                "    \"artist\": \"Taylor Swift\",\n" +
                "    \"url\": \"https://www.amazon.com/Taylor-Swift/dp/B0014I4KH6\",\n" +
                "    \"image\": \"https://images-na.ssl-images-amazon.com/images/I/61McsadO1OL.jpg\",\n" +
                "    \"thumbnail_image\": \"https://i.imgur.com/K3KJ3w4h.jpg\"\n" +
                "  }\n" +
                "]"
    }
}