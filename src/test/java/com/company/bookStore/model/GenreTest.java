package com.company.bookStore.model;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class GenreTest {

    private Genre genreNonFictionSelfHelp, genreNonFictionSelfHelpAnother;

    @BeforeAll
    public void setup() {

        Genre genreNonFiction;

        genreNonFiction = new Genre(1L, "Non fiction");
        genreNonFictionSelfHelp = new Genre(2L, "Self-Help");
        genreNonFictionSelfHelp.setParent(genreNonFiction);

        Genre genreNonFictionAnother;
        genreNonFictionAnother = new Genre(1L, "Non fiction");

        genreNonFictionSelfHelpAnother = new Genre(2L, "Self-Help");
        genreNonFictionSelfHelpAnother.setParent(genreNonFictionAnother);
    }

    @Test
    void testEquals() {
        Assertions.assertEquals(genreNonFictionSelfHelp, genreNonFictionSelfHelpAnother);

    }

    @Test
    void testHashCode() {
        Assertions.assertEquals(genreNonFictionSelfHelp.hashCode(),
                genreNonFictionSelfHelpAnother.hashCode());
    }
}