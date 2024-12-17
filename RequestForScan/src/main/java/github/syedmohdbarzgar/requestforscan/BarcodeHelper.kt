package github.syedmohdbarzgar.requestforscan

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import androidx.activity.result.ActivityResultLauncher

class BarcodeHelper(
    private val context: Context,
    private val scanBarcodeLauncher: ActivityResultLauncher<Intent>
) {
    companion object {
        const val EXTRA_SMS = "syedmohdbarzgar.scanner.EXTRA_SMS"
        const val EXTRA_URL = "syedmohdbarzgar.scanner.EXTRA_URL"
        const val EXTRA_LATITUDE = "syedmohdbarzgar.scanner.EXTRA_LATITUDE"
        const val EXTRA_LONGITUDE = "syedmohdbarzgar.scanner.EXTRA_LONGITUDE"
        const val EXTRA_WIFI_SSID = "syedmohdbarzgar.scanner.EXTRA_WIFI_SSID"
        const val EXTRA_WIFI_PASSWORD = "syedmohdbarzgar.scanner.EXTRA_WIFI_PASSWORD"
        const val EXTRA_WIFI_TYPE = "syedmohdbarzgar.scanner.EXTRA_WIFI_TYPE"
        const val EXTRA_CALENDAR_DESC = "syedmohdbarzgar.scanner.EXTRA_CALENDAR_DESC"
        const val EXTRA_CONTACT_NAME = "syedmohdbarzgar.scanner.EXTRA_CONTACT_NAME"
        const val EXTRA_DRIVER_LICENSE = "syedmohdbarzgar.scanner.EXTRA_DRIVER_LICENSE"
        const val EXTRA_EMAIL = "syedmohdbarzgar.scanner.EXTRA_EMAIL"
        const val EXTRA_PHONE = "syedmohdbarzgar.scanner.EXTRA_PHONE"
        const val EXTRA_FORMAT = "syedmohdbarzgar.scanner.EXTRA_FORMAT"
        const val EXTRA_RAW_VALUE = "syedmohdbarzgar.scanner.EXTRA_RAW_VALUE"
        const val EXTRA_DISPLAY_VALUE = "syedmohdbarzgar.scanner.EXTRA_DISPLAY_VALUE"
        const val SCANNER_PACKAGE = "syedmohdbarzgar.scanner"
    }

    // Define the ScanStatusCallback interface
    interface ScanStatusCallback {
        fun onScanStarted()
        fun onScanSuccess(result: BarcodeResult)
        fun onScanError(errorMessage: String)
    }

    // Start the scan process
    fun startScan(callback: ScanStatusCallback) {
        if (isScannerAppInstalled()) {
            callback.onScanStarted() // Indicating scan has started
            val intent = Intent("$SCANNER_PACKAGE.ACTION_SCAN_BARCODE").apply {
                type = "text/plain" // MIME type
                `package` = SCANNER_PACKAGE
            }
            scanBarcodeLauncher.launch(intent)
        } else {
            callback.onScanError("Scanner app is not installed.") // Handle missing app
        }
    }

    // Check if the scanner app is installed
    private fun isScannerAppInstalled(): Boolean {
        return try {
            context.packageManager.getPackageInfo(SCANNER_PACKAGE, 0)
            true
        } catch (e: PackageManager.NameNotFoundException) {
            false
        }
    }

    // Handle the result of the scan
    fun handleScanResult(resultCode: Int, data: Intent?, callback: ScanStatusCallback) {
        if (resultCode == Activity.RESULT_OK && data != null) {
            try {
                // Extract the result from the intent
                val result = BarcodeResult(
                    sms = data.getStringExtra(EXTRA_SMS),
                    url = data.getStringExtra(EXTRA_URL),
                    latitude = data.getDoubleExtra(EXTRA_LATITUDE, 0.0),
                    longitude = data.getDoubleExtra(EXTRA_LONGITUDE, 0.0),
                    wifiSSID = data.getStringExtra(EXTRA_WIFI_SSID),
                    wifiPassword = data.getStringExtra(EXTRA_WIFI_PASSWORD),
                    wifiType = data.getStringExtra(EXTRA_WIFI_TYPE),
                    calendarDesc = data.getStringExtra(EXTRA_CALENDAR_DESC),
                    contactName = data.getStringExtra(EXTRA_CONTACT_NAME),
                    driverLicense = data.getStringExtra(EXTRA_DRIVER_LICENSE),
                    email = data.getStringExtra(EXTRA_EMAIL),
                    phone = data.getStringExtra(EXTRA_PHONE),
                    format = data.getStringExtra(EXTRA_FORMAT),
                    rawValue = data.getStringExtra(EXTRA_RAW_VALUE),
                    displayValue = data.getStringExtra(EXTRA_DISPLAY_VALUE)
                )
                callback.onScanSuccess(result) // If scan is successful
            } catch (e: Exception) {
                callback.onScanError("Error parsing scan result: ${e.message}") // Handle errors during result parsing
            }
        } else {
            callback.onScanError("Scan failed or canceled.") // If scan was canceled or failed
        }
    }
}
