package com.github.jameshnsears.cameraoverlay

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.Settings
import android.widget.ToggleButton
import androidx.appcompat.app.AppCompatActivity
import com.github.jameshnsears.cameraoverlay.model.overlay.OverlayWindowForegroundService


class OverlayActivity : AppCompatActivity() {
    companion object {
        private const val OVERLAY_PERMISSION_REQUEST_CODE = 1
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.main_activity)
        requestOverlayPermission()

        findViewById<ToggleButton>(R.id.toggle_button).apply {
            isChecked = OverlayWindowForegroundService.isActive
            setOnCheckedChangeListener { _, isChecked ->
                if (isChecked) OverlayWindowForegroundService.start(this@OverlayActivity)
                else OverlayWindowForegroundService.stop(this@OverlayActivity)
            }
        }
    }

    private fun requestOverlayPermission() {
        if (isOverlayGranted()) return
        val intent = Intent(
            Settings.ACTION_MANAGE_OVERLAY_PERMISSION,
            Uri.parse("package:$packageName")
        )
        startActivityForResult(intent, OVERLAY_PERMISSION_REQUEST_CODE)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == OVERLAY_PERMISSION_REQUEST_CODE) {
            if (!isOverlayGranted()) {
                finish()  // Cannot continue if not granted
            }
        }
    }

    private fun isOverlayGranted() =
        Settings.canDrawOverlays(this)
}
