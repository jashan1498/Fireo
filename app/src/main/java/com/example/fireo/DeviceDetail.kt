package com.example.fireo

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.GridLayout
import com.example.fireo.Constants.Constants
import com.example.fireo.Utils.DialogHelper
import com.example.fireo.model.Device
import com.google.firebase.firestore.FirebaseFirestore
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.zxing.BarcodeFormat
import com.google.zxing.MultiFormatWriter
import com.google.zxing.WriterException
import com.google.zxing.common.BitMatrix
import com.journeyapps.barcodescanner.BarcodeEncoder
import kotlinx.android.synthetic.main.activity_device_detail.*
import kotlinx.android.synthetic.main.info_card.view.*
import org.json.JSONObject

class DeviceDetail : BaseApplication() {

    private lateinit var device: Device
    private var firebaseDatabase: FirebaseFirestore = FirebaseFirestore.getInstance()
    private val gson: Gson = GsonBuilder().create()
    private lateinit var gridLayout: GridLayout
    private lateinit var infoView: View
    private var deviceId: String = ""
    private var isStartedWithObject: Boolean = false


    companion object {
        @JvmStatic
        fun startActivityWithObject(device: Device, context: Context) {
            val activityIntent = Intent(context, DeviceDetail::class.java)
            activityIntent.putExtra(DEVICE_DETAIL_DATA, device)
            activityIntent.putExtra(IS_STARTED_WITH_OBJECT, true)
            context.startActivity(activityIntent)
        }

        @JvmStatic
        fun startActivityWithString(deviceInfo: String, context: Context) {
            val activityIntent = Intent(context, DeviceDetail::class.java)
            activityIntent.putExtra(DEVICE_CONTENT_EXTRA, deviceInfo)
            activityIntent.putExtra(IS_STARTED_WITH_OBJECT, false)
            context.startActivity(activityIntent)
        }

        const val DEVICE_DETAIL_DATA = "device_detail_data"
        const val DEVICE_CONTENT_EXTRA = "device_id_string"
        const val IS_STARTED_WITH_OBJECT = "is started with object"

    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.qr_code_gen) {
            generateQrCode()
            return true
        }
        return super.onOptionsItemSelected(item)
    }

    private fun generateQrCode() {
        val text = device.toString()
        val multiFormatWriter = MultiFormatWriter()
        try {
            val bitMatrix: BitMatrix =
                multiFormatWriter.encode(text, BarcodeFormat.QR_CODE, 500, 500)
            val barcodeEncoder = BarcodeEncoder()
            val bitmap = barcodeEncoder.createBitmap(bitMatrix)
            val dialogHelper = DialogHelper(this)
            dialogHelper.createQrDialog(bitmap)
        } catch (e: WriterException) {
            e.printStackTrace()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_device_detail)
        val toolbar = findViewById<androidx.appcompat.widget.Toolbar>(R.id.toolbar)
        setSupportActionBar(toolbar)
        gridLayout = device_info_view

        isStartedWithObject = intent.getBooleanExtra(IS_STARTED_WITH_OBJECT, false)

        if (isStartedWithObject) {
            device = intent.getParcelableExtra(DEVICE_DETAIL_DATA)
            inflateViewWithObject(device)
        } else {
            intent.getStringExtra(DEVICE_CONTENT_EXTRA)?.let {
                inflateViewWithId(deviceInfo = intent.getStringExtra(DEVICE_CONTENT_EXTRA))
            }
        }
    }

    private fun inflateViewWithId(deviceInfo: String) {
        val jsonObject = JSONObject(deviceInfo)
        val buildingId: String? = jsonObject.get("buildingId").toString()
        val deviceId: String? = jsonObject.get("deviceId").toString()
        val floor: String? = jsonObject.get("floor").toString()

        fetchDeviceInfo(deviceId, buildingId, floor)

    }

    private fun fetchDeviceInfo(deviceId: String?, buildingId: String?, floor: String?) {
        fireStore.collection(Constants.Collections.BUILDING).document(buildingId ?: "")
            .collection("floor$floor").document(deviceId ?: "").get()
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    device = task.result?.toObject(Device::class.java) ?: Device()
                    inflateViewWithObject(device)
                }
            }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.device_detail_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }


    private fun inflateViewWithObject(device: Device) {

        val jsonStr = gson.toJson(device)
        val json = JSONObject(jsonStr)
        val itr: Iterator<String> = json.keys()

        while (itr.hasNext()) {
            val str = itr.next()
            val description = json.get(str)
            infoView = makeInfoView(title = str, description = description)
            gridLayout.addView(infoView)

        }

    }

    private fun makeInfoView(title: String, description: Any): View {
        val infoView: View = LayoutInflater.from(this).inflate(R.layout.info_card, null, false)
        infoView.device_id_text.text = title
        infoView.detail.text = description.toString()
        return infoView
    }
}
