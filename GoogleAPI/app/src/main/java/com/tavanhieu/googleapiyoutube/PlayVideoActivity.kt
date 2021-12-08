package com.tavanhieu.googleapiyoutube

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import com.google.android.youtube.player.YouTubeBaseActivity
import com.google.android.youtube.player.YouTubeInitializationResult
import com.google.android.youtube.player.YouTubePlayer
import com.google.android.youtube.player.YouTubePlayerView

class PlayVideoActivity : YouTubeBaseActivity(), YouTubePlayer.OnInitializedListener {
    lateinit var youTubePlayerView: YouTubePlayerView
    var videoId = ""
    private val REQUEST_CODE_YOUTUBE = 123

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_play_video)

        videoId = intent.getStringExtra("videoId").toString()
        youTubePlayerView = findViewById(R.id.youtubePlayVideo)

        //Khởi tạo video để chạy:
        youTubePlayerView.initialize(MainActivity.API_KEY, this)
    }

    //Khởi tạo thành công sẽ vào đây chạy:
    override fun onInitializationSuccess(
        p0: YouTubePlayer.Provider?,
        p1: YouTubePlayer?,
        p2: Boolean
    ) {
        p1?.loadVideo(videoId)
        p1?.setFullscreen(true)
    }

    //Khởi tạo thất bại thì vào đây:
    override fun onInitializationFailure(
        p0: YouTubePlayer.Provider?,
        p1: YouTubeInitializationResult?
    ) {
        if(p1?.isUserRecoverableError == true) {
            //Nếu lỗi play thì trả về mã code để thử chạy lại:
            p1.getErrorDialog(this, REQUEST_CODE_YOUTUBE)
        } else {
            Toast.makeText(this, "Error Play Video", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        //Nếu xảy ra lỗi thì vào đây chạy lại video lần nữa:
        if(REQUEST_CODE_YOUTUBE == requestCode) {
            youTubePlayerView.initialize(MainActivity.API_KEY, this)
        }
        super.onActivityResult(requestCode, resultCode, data)
    }
}