package github.syedmohdbarzgar.requestforscan

data class BarcodeResult(
    val sms: String?,
    val url: String?,
    val latitude: Double?,
    val longitude: Double?,
    val wifiSSID: String?,
    val wifiPassword: String?,
    val wifiType: String?,
    val calendarDesc: String?,
    val contactName: String?,
    val driverLicense: String?,
    val email: String?,
    val phone: String?,
    val format: String?,
    val rawValue: String?,
    val displayValue: String?
)