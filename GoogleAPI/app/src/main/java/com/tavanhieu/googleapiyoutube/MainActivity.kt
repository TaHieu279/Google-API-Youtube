package com.tavanhieu.googleapiyoutube

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ListView
import android.widget.Toast
import com.android.volley.Request
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley

class MainActivity : AppCompatActivity() {
    lateinit var lstDanhSachVideo: ListView
    var arrVideo = ArrayList<Video>()
    lateinit var adapter: VideoAdapter
    companion object {
        //Lấy từ GG Consle API:
        val API_KEY = "AIzaSyDQVJeo-vh_iHbO-9EXwqw14LnZnblBpts"
        //ID của listVideo trên Youtube
        val SoureListVideo = "PLWBrqglnjNl3DzS2RHds5KlanGqQ1uLNQ"
    }
    //Link lấy JSON:
    val urlGetJson = "https://www.googleapis.com/youtube/v3/playlistItems?part=snippet&playlistId=$SoureListVideo&key=$API_KEY&maxResults=50"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        lstDanhSachVideo = findViewById(R.id.lstDanhSachVideo)
        adapter = VideoAdapter(this, R.layout.raw_video, arrVideo)
        lstDanhSachVideo.adapter = adapter

        getJsonUrl(urlGetJson)

        lstDanhSachVideo.setOnItemClickListener { parent, view, position, id ->
            val intent = Intent(this, PlayVideoActivity::class.java)
            intent.putExtra("videoId", arrVideo[position].idplay)
            startActivity(intent)
        }
    }

    fun getJsonUrl(url: String) {
        val requestQueue = Volley.newRequestQueue(this)
        val jsonObjectRequest = JsonObjectRequest(Request.Method.GET, url, null, {
            //Lấy mảng JSON:
            val jsonItems = it.getJSONArray("items")

            for(i in 0 until jsonItems.length()){

                //Lấy ra từng đối tượng json trong mảng:
                val jsonItem = jsonItems.getJSONObject(i)
                val jsonSnippet = jsonItem.getJSONObject("snippet")
                val title = jsonSnippet.getString("title")

                //Lấy ra thumnails(Ảnh):
                val jsonThumnails = jsonSnippet.getJSONObject("thumbnails")
                val jsonThumnailsMedium = jsonThumnails.getJSONObject("medium")
                val thumnails = jsonThumnailsMedium.getString("url")

                //Lấy ra resourceID(Video Id để play):
                val jsonResourceId = jsonSnippet.getJSONObject("resourceId")
                val videoId = jsonResourceId.getString("videoId")

                arrVideo.add(Video(thumnails, title, videoId))
            }
            adapter.notifyDataSetChanged()
        }, {
            Toast.makeText(this, "Error Volley!!!", Toast.LENGTH_SHORT).show()
        })
        requestQueue.add(jsonObjectRequest)
    }
}