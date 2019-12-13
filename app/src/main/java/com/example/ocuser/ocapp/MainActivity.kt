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
    val scoreList = mutableListOf<Note>()            //楽譜
    val scaleMap = mutableMapOf<String,Int>()       //音階と音源の対応
    lateinit var do1 : Button               //ドの鍵盤
    lateinit var dore1 : Button             //
    lateinit var re1 : Button               //レの鍵盤
    lateinit var remi1 : Button             //
    lateinit var mi1 : Button               //ミの鍵盤
    lateinit var fa1 : Button               //ファの鍵盤
    lateinit var faso1 : Button             //
    lateinit var so1 : Button               //ソの鍵盤
    lateinit var sora1 : Button             //
    lateinit var ra1 : Button               //ラの鍵盤
    lateinit var rasi1 : Button             //
    lateinit var si1 : Button               //シの鍵盤
    lateinit var do2 : Button               //高いドの鍵盤
    lateinit var dore2 : Button             //
    lateinit var re2 : Button               //
    lateinit var remi2 : Button             //
    lateinit var mi2 : Button               //
    lateinit var fa2 : Button               //
    lateinit var faso2 : Button             //
    lateinit var so2: Button                //
    lateinit var sora2 : Button             //
    lateinit var ra2:Button                 //
    lateinit var rasi2:Button               //
    lateinit var si2:Button                 //
    lateinit var play : Button              //再生のボタン
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
        sound( "do1" )
    }
    //ドレの間の鍵盤
    fun btnDoRe1Click(){

    }
    //レの鍵盤
    fun btnRe1Click(){

    }
    //レミの間の鍵盤
    fun btnReMi1Click(){

    }
    //ミの鍵盤
    fun btnMi1Click(){

    }
    //ファの鍵盤
    fun btnFa1Click(){

    }
    //ファソの間の鍵盤
    fun btnFaSo1Click(){

    }
    //ソの鍵盤
    fun btnSo1Click(){

    }
    //ソラの鍵盤
    fun btnSoRa1Click(){

    }
    //ラの鍵盤
    fun btnRa1Click(){

    }
    //ラシの間の鍵盤
    fun btnRaSi1Click(){

    }
    //シの鍵盤
    fun btnSi1Click(){

    }
    //ドの鍵盤
    fun btnDo2Click(){

    }
    //ドレの間の鍵盤
    fun btnDoRe2Click(){

    }
    //レの鍵盤
    fun btnRe2Click(){

    }
    //レミの間の鍵盤
    fun btnReMi2Click(){

    }
    //ミの鍵盤
    fun btnMi2Click(){

    }
    //ファの鍵盤
    fun btnFa2Click(){

    }
    //ファソの間の鍵盤
    fun btnFaSo2Click(){

    }
    //ソの鍵盤
    fun btnSo2Click(){

    }
    //ソラの鍵盤
    fun btnSoRa2Click(){

    }
    //ラの鍵盤
    fun btnRa2Click(){

    }
    //ラシの間の鍵盤
    fun btnRaSi2Click(){

    }
    //シの鍵盤
    fun btnSi2Click(){

    }

    //再生ボタンをクリックした時の処理
    fun makeScore(){
        //oto=音階,delay=長さ
        setScore( "re1" , 300 )
        setScore( "so1" , 250 )
        setScore("so1", 400)
        setScore( "ra1" , 100)
        setScore("so1", 150 )
        setScore("fa1",450 )
        setScore( "mi1" , 500)
        setScore( "mi1" , 100 )
        setScore( "-" , 100 )

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
        scaleMap.put("do1",  soundPool.load(this,R.raw.do1,1))
        scaleMap.put("dore1", soundPool.load(this,R.raw.dore1,1))
        scaleMap.put("re1",  soundPool.load(this,R.raw.re1,1))
        scaleMap.put("remi1",  soundPool.load(this,R.raw.remi1,1))
        scaleMap.put("mi1",  soundPool.load(this,R.raw.mi1,1))
        scaleMap.put("fa1",soundPool.load(this,R.raw.fa1,1))
        scaleMap.put("faso1",soundPool.load(this,R.raw.faso1,1))
        scaleMap.put("so1",  soundPool.load(this,R.raw.so1,1))
        scaleMap.put("sora1",  soundPool.load(this,R.raw.sora1,1))
        scaleMap.put("ra1",  soundPool.load(this,R.raw.ra1,1))
        scaleMap.put("rasi1",  soundPool.load(this,R.raw.rasi1,1))
        scaleMap.put("si1",  soundPool.load(this,R.raw.si1,1))
        scaleMap.put("do2",  soundPool.load(this,R.raw.do2,1))
        scaleMap.put("dore2",  soundPool.load(this,R.raw.dore2,1))
        scaleMap.put("re2",  soundPool.load(this,R.raw.re2,1))
        scaleMap.put("remi2",  soundPool.load(this,R.raw.remi2,1))
        scaleMap.put("mi2",  soundPool.load(this,R.raw.mi2,1))
        scaleMap.put("fa2",  soundPool.load(this,R.raw.fa2,1))
        scaleMap.put("faso2",  soundPool.load(this,R.raw.faso2,1))
        scaleMap.put("so2",  soundPool.load(this,R.raw.so2,1))
        scaleMap.put("sora2",  soundPool.load(this,R.raw.sora2,1))
        scaleMap.put("ra2",  soundPool.load(this,R.raw.ra2,1))
        scaleMap.put("rasi2",  soundPool.load(this,R.raw.rasi2,1))
        scaleMap.put("si2",  soundPool.load(this,R.raw.si2,1))
        scaleMap.put("do3",  soundPool.load(this,R.raw.do3,1))


        //鍵盤にイベントを割り当てる
        do1 = findViewById(R.id.do1)
        do1.setOnClickListener{
            btnDo1Click()
        }
        dore1 = findViewById(R.id.dore1)
        dore1.setOnClickListener{
            btnDoRe1Click()
        }
        re1 = findViewById(R.id.re1)
        re1.setOnClickListener{
            btnRe1Click()
        }
        remi1 = findViewById(R.id.remi1)
        remi1.setOnClickListener{
            btnReMi1Click()
        }
        mi1=findViewById(R.id.mi1)
        mi1.setOnClickListener{
            btnMi1Click()
        }
        fa1=findViewById(R.id.fa1)
        fa1.setOnClickListener {
            btnFa1Click()
        }
        faso1 = findViewById(R.id.faso1)
        faso1.setOnClickListener{
            btnFaSo1Click()
        }
        so1=findViewById(R.id.so1)
        so1.setOnClickListener{
            btnSo1Click()
        }
        sora1 = findViewById(R.id.sora1)
        sora1.setOnClickListener{
            btnSoRa1Click()
        }
        ra1=findViewById(R.id.ra1)
        ra1.setOnClickListener {
            btnRa1Click()
        }
        rasi1 = findViewById(R.id.rasi1)
        rasi1.setOnClickListener{
            btnRaSi1Click()
        }
        si1=findViewById(R.id.si1)
        si1.setOnClickListener{
            btnSi1Click()
        }
        do2= findViewById( R.id.do2)
        do2.setOnClickListener{
            btnDo2Click()
        }
        re2 = findViewById(R.id.re2)
        re2.setOnClickListener{
            btnRe2Click()
        }
        remi2 = findViewById(R.id.remi2)
        remi2.setOnClickListener{
            btnReMi2Click()
        }
        mi2=findViewById(R.id.mi2)
        mi2.setOnClickListener{
            btnMi2Click()
        }
        fa2=findViewById(R.id.fa2)
        fa2.setOnClickListener {
            btnFa2Click()
        }
        faso2 = findViewById(R.id.faso2)
        faso2.setOnClickListener{
            btnFaSo2Click()
        }
        so2=findViewById(R.id.so2)
        so2.setOnClickListener{
            btnSo2Click()
        }
        sora2 = findViewById(R.id.sora2)
        sora2.setOnClickListener{
            btnSoRa2Click()
        }
        ra2=findViewById(R.id.ra2)
        ra2.setOnClickListener {
            btnRa2Click()
        }
        rasi2 = findViewById(R.id.rasi2)
        rasi2.setOnClickListener{
            btnRaSi2Click()
        }
        si2=findViewById(R.id.si2)
        si2.setOnClickListener{
            btnSi2Click()
        }

        //楽譜を再生ボタン
        play = findViewById(R.id.play)
        play.setOnClickListener{
            scoreList.clear()
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