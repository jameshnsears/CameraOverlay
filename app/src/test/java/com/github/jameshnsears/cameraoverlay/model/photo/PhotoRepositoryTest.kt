package com.github.jameshnsears.cameraoverlay.model.photo

import org.junit.jupiter.api.Test

class PhotoRepositoryTest {
    @Test
    fun getCards() {
        val photoRepository = PhotoRepositoryDouble()

        assert(photoRepository.getCards() == "card double")
    }
}
