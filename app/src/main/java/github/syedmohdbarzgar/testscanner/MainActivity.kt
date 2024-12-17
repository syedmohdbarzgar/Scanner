package github.syedmohdbarzgar.testscanner

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import github.syedmohdbarzgar.requestforscan.BarcodeHelper
import github.syedmohdbarzgar.requestforscan.BarcodeResult
import github.syedmohdbarzgar.testscanner.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var barcodeHelper: BarcodeHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Set up edge-to-edge insets
        ViewCompat.setOnApplyWindowInsetsListener(binding.main) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }


        // Define a common callback for both scan initiation and result handling
        val scanStatusCallback = object : BarcodeHelper.ScanStatusCallback {
            override fun onScanStarted() {
                println("Scan Started")
            }

            override fun onScanSuccess(result: BarcodeResult) {
                binding.textView.text = result.displayValue
            }

            override fun onScanError(errorMessage: String) {
                println("Scan Error: $errorMessage")
                binding.textView.text = errorMessage
            }
        }

        // Register for activity result
        val scanLauncher =
            registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
                barcodeHelper.handleScanResult(result.resultCode, result.data, scanStatusCallback)
            }

        // Initialize BarcodeHelper with the correct context
        barcodeHelper = BarcodeHelper(this, scanLauncher)

        binding.button.setOnClickListener {
            // Start scanning
            barcodeHelper.startScan(scanStatusCallback)
        }
    }
}
