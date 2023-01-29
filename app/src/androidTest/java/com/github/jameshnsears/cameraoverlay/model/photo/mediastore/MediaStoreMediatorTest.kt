package com.github.jameshnsears.cameraoverlay.model.photo.mediastore

import com.github.jameshnsears.cameraoverlay.model.photo.PhotoResourcesUtility
import java.util.Arrays
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertNull
import org.junit.Test

class MediaStoreMediatorTest : PhotoResourcesUtility() {
    @Test
    fun confirmExif() {
        val picturesInMediaStore = MediaStoreMediator.picturesInMediaStore(context)
        val eiffelTower = picturesInMediaStore[0]

        val eiffelTowerExif = MediaStoreMediator.getExifFromUri(context, eiffelTower)
        assertEquals(1631639311000, eiffelTowerExif.dateTime)
        assertNull(eiffelTowerExif.latLong)

        val towerBridge = picturesInMediaStore[2]
        val towerBridgeExif = MediaStoreMediator.getExifFromUri(context, towerBridge)
        assertEquals(1631639351000, towerBridgeExif.dateTime)

        // latitude = 51/1,30/1,99/5
        // longitude = 0/1,4/1,39327/1250
        assertEquals("[51.5055, -0.075406]", Arrays.toString(towerBridgeExif.latLong))
    }
}
