package com.xquare.xquare_android.feature.alarm

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.media.RingtoneManager
import android.os.Build
import android.util.Log
import androidx.core.app.NotificationCompat
import androidx.work.OneTimeWorkRequest
import androidx.work.WorkManager
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.messaging.FirebaseMessaging
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import com.xquare.xquare_android.MainActivity
import com.xquare.xquare_android.R

@Suppress("DEPRECATION")
class MyFirebaseMessagingService : FirebaseMessagingService() {
     val TAG = "MSG_Firebase"

    // 토큰 생성
    override fun onNewToken(token: String) {
        super.onNewToken(token)

        FirebaseMessaging.getInstance().token.addOnCompleteListener(OnCompleteListener { task ->
            if (!task.isSuccessful){
                Log.w(TAG, "getInstanceId failed", task.exception)
                return@OnCompleteListener
            }

            val token = task.result
            Log.d(TAG, "Token: $token")
        })
    }

    // 메시지 수신되면 호출
    override fun onMessageReceived(remoteMessage: RemoteMessage) {
        Log.i(TAG, remoteMessage.toString());

        // 서버에서 직접 보냈을 때
        if(remoteMessage.notification != null){
            sendNotification(remoteMessage.notification?.title, remoteMessage.notification?.body!!)
            if (true) {
                scheduleJob();
            } else {
                handleNow();
            }
        }
        // 다른 기기에서 서버로 보냈을 때
        else if(remoteMessage.data.isNotEmpty()){
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
                sendMessageNotification(remoteMessage.data )
            }
            else{
                sendNotification(remoteMessage.notification?.title, remoteMessage.notification?.body!!)
            }
            if (true) {
                scheduleJob();
            } else {
                handleNow();
            }
        }
    }


    private fun handleNow() {
        Log.d(TAG, "Short lived task is done.")
    }


    private fun scheduleJob() {
        val work = OneTimeWorkRequest.Builder(MyWorker::class.java)
            .build()
        WorkManager.getInstance().beginWith(work).enqueue()
    }

    // Firebase Cloud Messaging Server 가 대기중인 메세지를 삭제 시 호출
    override fun onDeletedMessages() {
        super.onDeletedMessages()
    }

    // 메세지가 서버로 전송 성공 했을때 호출
    override fun onMessageSent(p0: String) {
        super.onMessageSent(p0)
        Log.e(TAG, "sending success ")
    }

    // 메세지가 서버로 전송 실패 했을때 호출
    override fun onSendError(p0: String, p1: Exception) {
        super.onSendError(p0, p1)
    }


    fun sendNotification(title: String?, body: String) {
        val uniId: Int = (System.currentTimeMillis() / 7).toInt()
        val intent = Intent(this, MainActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP) // 액티비티 중복 생성 방지
        val pendingIntent = PendingIntent.getActivity(
            this, 0, intent,
            PendingIntent.FLAG_ONE_SHOT or PendingIntent.FLAG_IMMUTABLE
        ) // 일회성

        val channelId = getString(R.string.firebase_notification_channel_id)
        val defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION) // 소리

        val notificationBuilder = NotificationCompat.Builder(this,channelId)
            .setSmallIcon(R.mipmap.ic_launcher)     // 아이콘 설정
            .setContentTitle(title)     // 제목
            .setContentText(body)     // 메시지 내용
            .setAutoCancel(true)
            .setSound(defaultSoundUri)     // 알림 소리
            .setContentIntent(pendingIntent)       // 알림 실행 시 Intent
            .setDefaults(Notification.DEFAULT_SOUND)

        val notificationManager =
            getSystemService(NOTIFICATION_SERVICE) as NotificationManager

        // 오레오 버전 이후에는 채널이 필요
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            val channel = NotificationChannel(channelId, "Notice", NotificationManager.IMPORTANCE_HIGH)
            notificationManager.createNotificationChannel(channel)
            channel.apply {
                setShowBadge(false)
            }
        }
        notificationManager.notify(uniId, notificationBuilder.build())
    }
    private fun sendMessageNotification( Message : Map<String, String>){
        val uniId: Int = (System.currentTimeMillis() / 7).toInt()
        val title = Message["title"]!!
        val body = Message["body"]!!
        val intent = Intent(this, MainActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
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
            .setContentTitle(title)     // 제목
            .setContentText(body)     // 메시지 내용
            .setChannelId(channelId)
            .setAutoCancel(true)
            .setSound(soundUri)     // 알림 소리
            .setContentIntent(pendingIntent)       // 알림 실행 시 Intent
            .setDefaults(Notification.DEFAULT_SOUND)



        val notificationManager =
            getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        // 오레오 버전 이후에는 채널이 필요
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            val channel = NotificationChannel(channelId, "Notice", NotificationManager.IMPORTANCE_HIGH)
            notificationManager.createNotificationChannel(channel)
            channel.apply {
                setShowBadge(false) // 뱃지 사용안함
            }
        }
        notificationManager.notify(uniId, notificationBuilder.build()) // 여기서 uniID을 0으로 설정하면 상태바에 최신의 하나의 알림만 보이게 된다.

    }
}


    internal class MyWorker(appContext: Context, workerParams: WorkerParameters) : Worker(appContext, workerParams) {
        override fun doWork(): Result {
            // TODO(developer): add long running task here.
            return Result.success()
        }
    }

