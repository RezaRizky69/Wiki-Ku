package com.eja.wiki_ku.activities

import android.app.ProgressDialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.eja.wiki_ku.R
import com.eja.wiki_ku.networking.ApiClient.getApiClient
import com.eja.wiki_ku.networking.ApiInterface
import kotlinx.android.synthetic.main.activity_main.*
import org.json.JSONException
import org.json.JSONObject
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {

    var strInputTeks: String = ""
    var strResult: String = ""
    var strKunciWiki: String = ""
    var progressDialog: ProgressDialog? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        progressDialog = ProgressDialog(this)
        progressDialog?.setTitle("Mohon Tunggu...")
        progressDialog?.setCancelable(false)
        progressDialog?.setMessage("Sedang menampilkan data")

        imageClear.visibility = View.GONE
        linearHasil.visibility = View.GONE

        imageClear.setOnClickListener {
            inputJudul.text.clear()
            tvKunciWiki.text = ""
            linearHasil.visibility = View.GONE
            imageClear.visibility = View.GONE
        }

        btnPencarian.setOnClickListener {
            strInputTeks = inputJudul.text.toString()
            if (strInputTeks.isEmpty()) {
                Toast.makeText(this@MainActivity, "Masukkan kata kunci!", Toast.LENGTH_SHORT).show()
            } else {
                getWiki(strInputTeks)
            }
        }
    }

    fun getWiki(strInputTeks: String) {
        progressDialog?.show()
        val apiInterface = getApiClient().create(ApiInterface::class.java)
        val call = apiInterface.getWiki(strInputTeks)
        call.enqueue(object : Callback<String> {
            override fun onResponse(call: Call<String>, response: Response<String>) {
                if (response.isSuccessful && response.body() != null) {
                    progressDialog?.dismiss()
                    try {
                        val responseObject = JSONObject(response.body())
                        strResult = responseObject.getString("result")

                        tvKunciWiki.text = strResult

                        linearHasil.visibility = View.VISIBLE
                        imageClear.visibility = View.VISIBLE
                    } catch (e: JSONException) {
                        e.printStackTrace()
                        Toast.makeText(
                            this@MainActivity,
                            e.message.toString(), Toast.LENGTH_SHORT
                        ).show()
                    }
                }

            }


            override fun onFailure(call: Call<String>, t: Throwable) {
                progressDialog?.dismiss()
                Toast.makeText(this@MainActivity, t.message.toString(), Toast.LENGTH_SHORT).show()
            }
        })
    }

}