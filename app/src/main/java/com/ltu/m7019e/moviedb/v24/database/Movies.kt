package com.ltu.m7019e.moviedb.v24.database

import com.ltu.m7019e.moviedb.v24.model.Movie

class Movies {
    fun getMovies(): List<Movie> {
        return listOf<Movie>(
            Movie(
                693134,
                "Dune: Part Two",
                "/1pdfLvkbY9ohJlCjQH2CZjjYVvJ.jpg",
                "/xOMo8BRK7PfcJv9JCnx7s5hj0PX.jpg",
                "2024-02-27",
                "Follow the mythic journey of Paul Atreides as he unites with Chani and the Fremen while on a path of revenge against the conspirators who destroyed his family. Facing a choice between the love of his life and the fate of the known universe, Paul endeavors to prevent a terrible future only he can foresee.",
                false,
                "190000000",
                listOf(("Science Fiction"),
                    ("Adventure")),
                "https://www.dunemovie.com",
                "tt15239678",
                "en"
            ),
            Movie(
                1011985,
                "Kung Fu Panda 4",
                "/xoYc0RYKSc3xC4S9OpPZxKocKtj.jpg",
                "/uDosHOFFWtF5YteBRygHALFqLw2.jpg",
                "2024-03-02",
                "Po is gearing up to become the spiritual leader of his Valley of Peace, but also needs someone to take his place as Dragon Warrior. As such, he will train a new kung fu practitioner for the spot and will encounter a villain called the Chameleon who conjures villains from the past.",
                false,
                "85000000",
                listOf(("Animation"),
                    ("Action"),
                    ("Family"),
                    ("Comedy"),
                    ("Fantasy")
                ),
                "https://www.dreamworks.com/movies/kung-fu-panda-4",
                "tt21692408",
                "en"
            ),
            Movie(
                823464,
                "Godzilla x Kong: The New Empire",
                "/tMefBSflR6PGQLv7WvFPpKLZkyk.jpg",
                "/j3Z3XktmWB1VhsS8iXNcrR86PXi.jpg",
                "2024-03-27",
                "Following their explosive showdown, Godzilla and Kong must reunite against a colossal undiscovered threat hidden within our world, challenging their very existence – and our own.",
                false,
                "140956385",
                listOf(("Action"),
                    ("Science Fiction"),
                    ("Adventure"),
                    ("Fantasy")
                ),
                "https://www.godzillaxkongmovie.com",
                "tt14539740",
                "en"
            ),

            Movie(
                601796,
                "외계+인 1부",
                "/8QVDXDiOGHRcAD4oM6MXjE0osSj.jpg",
                "/7ZP8HtgOIDaBs12krXgUIygqEsy.jpg",
                "2022-07-20",
                "Gurus in the late Goryeo dynasty try to obtain a fabled, holy sword, and humans in 2022 hunt down an alien prisoner that is locked in a human's body. The two parties cross paths when a time-traveling portal opens up.",
                false,
                "24500000",
                listOf(("Science Fiction"),
                    ("Action"),
                    ("Fantasy"),
                    ("Adventure")
                ),
                "",
                "tt20168564",
                "ko"
            ),
            Movie(
                359410,
                "Road House",
                "/bXi6IQiQDHD00JFio5ZSZOeRSBh.jpg",
                "/oe7mWkvYhK4PLRNAVSvonzyUXNy.jpg",
                "2024-03-08",
                "Ex-UFC fighter Dalton takes a job as a bouncer at a Florida Keys roadhouse, only to discover that this paradise is not all it seems.",
                false,
                "85000000",
                listOf(("Action"),
                    ("Thriller")
                ),
                "https://www.amazon.com/gp/video/detail/B0CH5YQPZQ",
                "tt3359350",
                "en"
            ),
        )
    }
}