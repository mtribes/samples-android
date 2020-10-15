package com.deltatre.samples.data

object FakeData {
    val anonymous = FakeDto(
        header = Header(
            id = "0001",
            title = "",
            btnTitle = "Sign Out",
            backgroundColor = "#6F58C4",
            gradientColor = "#6200EE"
        ),
        fakeRows = listOf(
            FakeRow(
                id = "x_0001",
                type = FakeRow.Type.HERO,
                hero = FakeRow.Hero(
                    imageUrl = "https://pkw.us.astcdn.com/img/sample/1=x700.jpg"
                )
            ),
            FakeRow(
                id = "x_0002",
                type = FakeRow.Type.BANNER,
                banner = FakeRow.Banner(
                    text = "Join Us"
                )
            )
        )
    )

    val singedUser = FakeDto(
        header = Header(
            id = "0001",
            title = "Hi Olivia",
            btnTitle = "Sign In",
            backgroundColor = "#6F58C4",
            gradientColor = "#6200EE"
        ),
        fakeRows = listOf(
            FakeRow(
                id = "x_0001",
                type = FakeRow.Type.HERO,
                hero = FakeRow.Hero(
                    imageUrl = "https://pkw.us.astcdn.com/img/sample/2=x700.jpg"
                )
            ),
            FakeRow(
                id = "x_0002",
                type = FakeRow.Type.BANNER,
                banner = FakeRow.Banner(
                    text = "Play Now"
                )
            )
        )
    )
}