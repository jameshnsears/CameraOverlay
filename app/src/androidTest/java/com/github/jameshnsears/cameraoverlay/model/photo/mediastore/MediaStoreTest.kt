package com.github.jameshnsears.cameraoverlay.model.photo.mediastore

import com.github.jameshnsears.cameraoverlay.model.photo.PhotoResourcesUtility
import com.github.jameshnsears.cameraoverlay.common.EmulatorCompatibilityHelper
import java.util.Arrays
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertNull
import junit.framework.TestCase.assertTrue
import junit.framework.TestCase.fail
import org.junit.Test

class MediaStoreTest : PhotoResourcesUtility() {
    @Test
    fun confirmExif() {
        if (!EmulatorCompatibilityHelper.canTestButRunInEmulatorQ()) {
            fail()
        }

        if (MediaStoreMediator.picturesInMediaStore(context).size != 3) {
            copyImageResourcesToExternalStorage()
        }

        val picturesInMediaStore = MediaStoreMediator.picturesInMediaStore(context)
        assertTrue(picturesInMediaStore.isNotEmpty())

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
