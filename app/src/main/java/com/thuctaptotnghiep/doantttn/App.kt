package com.thuctaptotnghiep.doantttn

import android.app.Application
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.thuctaptotnghiep.doantttn.di.DaggerDataComponent
import com.thuctaptotnghiep.doantttn.di.DataComponent
import com.thuctaptotnghiep.doantttn.di.DatabaseModel
import com.thuctaptotnghiep.doantttn.utils.Constant

class App : Application() {
    private lateinit var dataComponent: DataComponent

    override fun onCreate() {
        super.onCreate()
        dataComponent =
            DaggerDataComponent.builder().databaseModel(DatabaseModel(applicationContext)).build()
        createChannelNotification()
    }

    fun getDataComponent(): DataComponent {
        return dataComponent
    }

    fun createChannelNotification(){
        if(Build.VERSION.SDK_INT > Build.VERSION_CODES.O){
            val channel1 = NotificationChannel(
                Constant.CHANNEL_ID1,
                "Channel 1",
                NotificationManager.IMPORTANCE_HIGH
            )
            channel1.description = "Channel with priority high"
            val channel2 = NotificationChannel(
                Constant.CHANNEL_ID2,
                "Channel 2",
                NotificationManager.IMPORTANCE_LOW
            )
            channel2.description = "Channel with priority low"

            val manager:NotificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            manager.createNotificationChannel(channel1)
            manager.createNotificationChannel(channel2)
        }
    }

    companion object{
        fun pushNotification(context: Context,title:String,content:String,icon:Int,color:Int){
            val notification = NotificationCompat.Builder(context,Constant.CHANNEL_ID1).apply {
                setSmallIcon(icon)
                setContentTitle(title)
                setContentText(content)
                setAutoCancel(true)
                setColor(color)
            }.build()
            with(NotificationManagerCompat.from(context)){
                notify(Constant.ID_NOTIFICATION,notification)
            }
        }
    }
}