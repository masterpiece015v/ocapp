package com.example.ocuser.ocapp

import android.media.AudioAttributes
import android.media.SoundPool
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import kotlinx.android.synthetic.main.activity_main.*

data class Note(val oto : String , val delay : Long )

class MainActivity : AppCompatActivity() {
    private lateinit var soundPool : SoundPool
    val scoreList = mutableListOf<Note>()        //楽譜
    val scaleMap = mutableMapOf<String,Int>()    //音階と音源の対応

    //アプリケーションが起動するときに自動で動く
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        //初期化
        init()
        //タイトルを表示する
        
        //鍵盤にイベントを割り当てる


    }

    //再生ボタンをクリックした時の処理
    fun makeScore(){
        //oto=音階,delay=長さ
        setScore( "do1" , 200 )
        setScore( "re1" , 200 )
        setScore( "mi1", 400)
        setScore( "fa1" , 500)
        setScore( "mi1",300)
        setScore( "re1" , 500)
        setScore( "do1" , 800 )
        setScore( "-" , 500 )

    }

    //scoreListを再生する非同期プログラム
    fun scorePlay(){
        GlobalScope.launch( Dispatchers.Main ){
            play.isEnabled = false
            async( Dispatchers.Default ){
                scoreList.forEach{
                    if( it.oto != "-") {
                        sound(it.oto)
                    }
                    Thread.sleep( it.delay )
                }
                return@async
            }.await()
            play.isEnabled = true
        }
    }

    //初期化処理
    fun init(){
        //音源の準備
        val audioAttributes = AudioAttributes.Builder().setUsage(AudioAttributes.USAGE_GAME).setContentType(AudioAttributes.CONTENT_TYPE_MUSIC).build()
        soundPool = SoundPool.Builder().setAudioAttributes(audioAttributes).setMaxStreams(2).build()
        scaleMap.put("do1",  soundPool.load(this,R.raw.s_do,1))
        scaleMap.put("dore1",  soundPool.load(this,R.raw.s_dore1,1))
        scaleMap.put("re1",  soundPool.load(this,R.raw.s_re,1))
        scaleMap.put("remi1",  soundPool.load(this,R.raw.s_remi1,1))
        scaleMap.put("mi1",  soundPool.load(this,R.raw.s_mi,1))
        scaleMap.put("fa1",soundPool.load(this,R.raw.s_fa,1))
        scaleMap.put("faso1",  soundPool.load(this,R.raw.s_faso1,1))
        scaleMap.put("so1",  soundPool.load(this,R.raw.s_so,1))
        scaleMap.put("sora1",  soundPool.load(this,R.raw.s_sora1,1))
        scaleMap.put("ra1",  soundPool.load(this,R.raw.s_ra,1))
        scaleMap.put("rasi1",  soundPool.load(this,R.raw.s_rasi1,1))
        scaleMap.put("si1",  soundPool.load(this,R.raw.s_si,1))
        scaleMap.put("do2",  soundPool.load(this,R.raw.s_do2,1))

        //楽譜を再生ボタン
        play.setOnClickListener{
            makeScore()
            scorePlay()
        }
    }

    //音を設定する
    fun setScore( oto : String , delay : Long){
        scoreList.add( Note( oto , delay ))
    }

    //音を出す
    fun sound( oto :String ){
        soundPool.play( scaleMap[oto]!!,1.0f,1.0f,0,0,1.0f)
    }

}