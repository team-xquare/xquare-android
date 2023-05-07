package com.xquare.xquare_android.feature.alarm

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.media.RingtoneManager
import android.os.Build
import android.util.Log
import androidx.core.app.NotificationCompat
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import com.xquare.xquare_android.MainActivity
import com.xquare.xquare_android.R

class MyFirebaseMessagingService : FirebaseMessagingService() {
    private val TAG = "FirebaseService"


    // 토큰 생성
    override fun onNewToken(token: String) {
        Log.d(TAG, "Refreshed token: $token")

        // 토큰 값 따로 저장
        val pref = this.getSharedPreferences("token", Context.MODE_PRIVATE)
        val editor = pref.edit()
        editor.putString("token",token).apply()
        editor.commit()

        Log.i("로그", "토큰 저장 성공적")
    }


    // 메시지 수신
    override fun onMessageReceived(remoteMessage: RemoteMessage) {
        Log.d(TAG, "From: " + remoteMessage.from)

        if(remoteMessage.data.isNotEmpty()){
            Log.i("바디", remoteMessage.data["body"].toString())
            Log.i("타이틀",remoteMessage.data["title"].toString())
            sendNotification(remoteMessage)
        }
        else {
            Log.i("수신에러 : ", "data가 비어있습니다. 메시지를 수신하지 못했습니다.")
            Log.i("data값 :",remoteMessage.data.toString())
        }
    }


    // 알림 생성 (아이콘, 알림 소리 등)
    private fun sendNotification(remoteMessage: RemoteMessage){
        // RemoteCode, ID를 고유값으로 지정하여 알림이 개별 표시 되도록 함
        val uniId: Int = (System.currentTimeMillis() / 7).toInt()


        val intent = Intent(this, MainActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP) // Activity Stack을 경로만 남김, A-B-C-D-B => A-B
        val pendingIntent = PendingIntent.getActivity(
            this,
            uniId,
            intent,
            PendingIntent.FLAG_ONE_SHOT or PendingIntent.FLAG_IMMUTABLE
        )

        // 알림 채널 이름
        val channelId = getString(R.string.firebase_notification_channel_id)

        // 알림 소리
        val soundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)

        // 알림에 대한 UI 정보와 작업을 지정
        val notificationBuilder = NotificationCompat.Builder(this, channelId)
            .setSmallIcon(R.mipmap.ic_launcher)     // 아이콘 설정
            .setContentTitle(remoteMessage.data["title"].toString())     // 제목
            .setContentText(remoteMessage.data["body"].toString())     // 메시지 내용
            .setAutoCancel(true)
            .setSound(soundUri)     // 알림 소리
            .setContentIntent(pendingIntent)       // 알림 실행 시 Intent

        val notificationManager =
            getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        // 오레오 버전 이후에는 채널이 필요
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            val channel = NotificationChannel(channelId, "Notice", NotificationManager.IMPORTANCE_DEFAULT)
            notificationManager.createNotificationChannel(channel)
        }

        // 알림 생성
        notificationManager.notify(uniId, notificationBuilder.build())

    }
}
