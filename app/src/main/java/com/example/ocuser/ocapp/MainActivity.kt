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

data class Note(val oto : String , val delay : Long )

class MainActivity : AppCompatActivity() {
    private lateinit var soundPool : SoundPool
    val scoreList = mutableListOf<Note>()        //楽譜
    val scaleMap = mutableMapOf<String,Int>()    //音階と音源の対応
    lateinit var do1 : Button                //ドの鍵盤
    lateinit var re1 : Button                //レの鍵盤
    lateinit var mi1 : Button                //ミの鍵盤
    lateinit var fa1 : Button                //ファの鍵盤
    lateinit var so1 : Button                //ソの鍵盤
    lateinit var ra1 : Button                //ラの鍵盤
    lateinit var si1 : Button                //シの鍵盤
    lateinit var do2 : Button               //高いドの鍵盤
    lateinit var play : Button               //再生のボタン
    lateinit var clear : Button             //クリアのボタン

    //アプリケーションが起動するときに自動で動く
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        //初期化
        init()

    }

    //ドの鍵盤
    fun btnDo1Click(){
        sound( "ド1" )
    }
    //レの鍵盤
    fun btnRe1Click(){

    }
    //ミの鍵盤
    fun btnMi1Click(){

    }
    //ファの鍵盤
    fun btnFa1Click(){

    }
    //ソの鍵盤
    fun btnSo1Click(){

    }
    //ラの鍵盤
    fun btnRa1Click(){

    }
    //シの鍵盤
    fun btnSi1Click(){

    }
    //ドの鍵盤
    fun btnDo2Click(){

    }

    //再生ボタンをクリックした時の処理
    fun makeScore(){
        //oto=音階,delay=長さ
        setScore( "ド1" , 200 )
        setScore( "レ1" , 200 )
        setScore("ミ1", 400)
        setScore( "ファ1" , 500)
        setScore("ミ1",300)
        setScore( "レ1" , 500)
        setScore( "ド1" , 800 )
        setScore( "休" , 500 )

    }

    //scoreListを再生する非同期プログラム
    fun scorePlay(){
        GlobalScope.launch( Dispatchers.Main ){
            play.isEnabled = false
            async( Dispatchers.Default ){
                scoreList.forEach{
                    if( it.oto != "休") {
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
        scaleMap.put("ド1",  soundPool.load(this,R.raw.s_do,1))
        scaleMap.put("レ1",  soundPool.load(this,R.raw.s_re,1))
        scaleMap.put("ミ1",  soundPool.load(this,R.raw.s_mi,1))
        scaleMap.put("ファ1",soundPool.load(this,R.raw.s_fa,1))
        scaleMap.put("ソ1",  soundPool.load(this,R.raw.s_so,1))
        scaleMap.put("ラ1",  soundPool.load(this,R.raw.s_ra,1))
        scaleMap.put("シ1",  soundPool.load(this,R.raw.s_si,1))
        scaleMap.put("ド2",  soundPool.load(this,R.raw.s_do2,1))

        //鍵盤にイベントを割り当てる
        do1 = findViewById(R.id.do1)
        do1.setOnClickListener{
            btnDo1Click()
                    }
        re1 = findViewById(R.id.re1)
        re1.setOnClickListener{
            btnRe1Click()
                    }
        mi1=findViewById(R.id.mi1)
        mi1.setOnClickListener{
            btnMi1Click()
                   }
        fa1=findViewById(R.id.fa1)
        fa1.setOnClickListener {
            btnFa1Click()
        }
        so1=findViewById(R.id.so1)
        so1.setOnClickListener{
            btnSo1Click()
                    }
        ra1=findViewById(R.id.ra1)
        ra1.setOnClickListener {
            btnRa1Click()
        }
        si1=findViewById(R.id.si1)
        si1.setOnClickListener{
            btnSi1Click()
        }
        do2= findViewById( R.id.do2)
        do2.setOnClickListener{
            btnDo2Click()
        }
        //楽譜を再生ボタン
        play = findViewById(R.id.play)
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