package com.deltatre.samples.data

data class FakeUser(
    val id: String,
    val name: String,
    val subscription: String
)

// fake data transfer object from the backend
data class FakeDto(
    val header: Header,
    val fakeRows: List<FakeRow>
)

data class Header(
    val id: String,
    val title: String = "",
    val btnTitle: String = "",
    val isVisible: Boolean = true,
    val backgroundColor: String,
    val gradientColor: String
)

// represent an item in the list
data class FakeRow(
    val id: String,
    val type: Type,
    val isVisible: Boolean = true,
    val hero: Hero? = null,
    val banner: Banner? = null,
) {
    enum class Type {
        HERO,
        BANNER
    }

    data class Hero(
        val imageUrl: String
    )

    data class Banner(
        val text: String
    )
}

// fake data representation
object FakeData {
    val anonymous = FakeDto(
        header = Header(
            id = "0001",
            title = "Welcome",
            btnTitle = "Sign In",
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
            btnTitle = "Sign Out",
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