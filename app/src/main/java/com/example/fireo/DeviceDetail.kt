package com.example.fireo

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.GridLayout
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.fireo.model.Device
import com.google.firebase.firestore.FirebaseFirestore
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import kotlinx.android.synthetic.main.activity_device_detail.*
import kotlinx.android.synthetic.main.info_card.view.*
import org.json.JSONObject

class DeviceDetail : AppCompatActivity() {

    private lateinit var device: Device
    private var firebaseDatabase: FirebaseFirestore = FirebaseFirestore.getInstance()
    private val gson: Gson = GsonBuilder().create()
    private lateinit var gridLayout: GridLayout
    private lateinit var infoView: View
    private var deviceId: String = ""
    private var isStarttedWithObject: Boolean = false


    companion object {
        @JvmStatic
        fun startActivityWithObject(device: Device, context: Context) {
            val activityIntent = Intent(context, DeviceDetail::class.java)
            activityIntent.putExtra(DEVICE_DETAIL_DATA, device)
            activityIntent.putExtra(IS_STARTED_WITH_OBJECT, true)
            context.startActivity(activityIntent)

        }

        @JvmStatic
        fun startActivityWithString(deviceId: String, context: Context) {
            val activityIntent = Intent(context, DeviceDetail::class.java)
            activityIntent.putExtra(DEVICE_ID_EXTRA, deviceId)
            activityIntent.putExtra(IS_STARTED_WITH_OBJECT, false)
            context.startActivity(activityIntent)
        }

        const val DEVICE_DETAIL_DATA = "device_detail_data"
        const val DEVICE_ID_EXTRA = "device_id_string"
        const val IS_STARTED_WITH_OBJECT = "is started with object"

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_device_detail)
        gridLayout = device_info_view

        isStarttedWithObject = intent.getBooleanExtra(IS_STARTED_WITH_OBJECT,false)

        if (isStarttedWithObject) {
            device = intent.getParcelableExtra(DEVICE_DETAIL_DATA)
            inflateViewWithObject(device)
        } else {
            intent.getStringExtra(DEVICE_ID_EXTRA)?.let {
                inflateViewWithId(deviceId =  intent.getStringExtra(DEVICE_ID_EXTRA))
            }
        }
    }

    private fun inflateViewWithId(deviceId: String) {
        Toast.makeText(this, "booom", Toast.LENGTH_LONG).show()
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
        infoView.title.text = title
        infoView.detail.text = description.toString()
        return infoView
    }
}
