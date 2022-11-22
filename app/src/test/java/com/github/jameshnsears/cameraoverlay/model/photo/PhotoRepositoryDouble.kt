package com.github.jameshnsears.cameraoverlay.model.photo

class PhotoRepositoryDouble : PhotoRepository {
    // https://blog.cleancoder.com/uncle-bob/2014/05/14/TheLittleMocker.html
    override fun getCards(): String {
        return "card double"
    }
}
