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
    lateinit var btn_do : Button                //ドの鍵盤
    lateinit var btn_re : Button                //レの鍵盤
    lateinit var btn_mi : Button                //ミの鍵盤
    lateinit var btn_fa : Button                //ファの鍵盤
    lateinit var btn_so : Button                //ソの鍵盤
    lateinit var btn_ra : Button                //ラの鍵盤
    lateinit var btn_si : Button                //シの鍵盤
    lateinit var btn_play :Button               //再生のボタン
    lateinit var btn_clear : Button             //クリアのボタン

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        //初期化
        init()
        //楽譜を作る
        makeScore()
    }

    //ドの鍵盤
    fun btnDo(){
        sound("ド")
    }
    //レの鍵盤
    fun btnRe(){

    }
    //ミの鍵盤
    fun btnMi(){

    }
    //ファの鍵盤
    fun btnFa(){

    }
    //ソの鍵盤
    fun btnSo(){

    }
    //ラの鍵盤
    fun btnRa(){
    }
    //シの鍵盤
    fun btnSi(){

    }
    //楽譜を作る
    fun makeScore(){
        setScore("ド" , 400 )

    }

    //問題を再生するイベントプログラム
    fun btnPlayClick(){
        GlobalScope.launch( Dispatchers.Main ){
            scoreList.forEach{
                async( Dispatchers.Default ){
                    if( it.oto != "休") {
                        sound(it.oto)
                    }
                    Thread.sleep( it.delay )
                    return@async
                }.await()
            }
        }
    }
    //初期化処理
    fun init(){
        //音源の準備
        val audioAttributes = AudioAttributes.Builder().setUsage(AudioAttributes.USAGE_GAME).setContentType(AudioAttributes.CONTENT_TYPE_MUSIC).build()
        soundPool = SoundPool.Builder().setAudioAttributes(audioAttributes).setMaxStreams(2).build()
        scaleMap.put("ド",soundPool.load(this,R.raw.s_do,1))
        scaleMap.put("レ",soundPool.load(this,R.raw.s_re,1))
        scaleMap.put("ミ",soundPool.load(this,R.raw.s_mi,1))
        scaleMap.put("ファ",soundPool.load(this,R.raw.s_fa,1))
        scaleMap.put("ソ",soundPool.load(this,R.raw.s_so,1))
        scaleMap.put("ラ",soundPool.load(this,R.raw.s_ra,1))
        scaleMap.put("シ",soundPool.load(this,R.raw.s_si,1) )

        //鍵盤にイベントを割り当てる
        btn_do = findViewById(R.id.btn_do)
        btn_do.setOnClickListener{
            btnDo()
        }
        btn_re = findViewById(R.id.btn_re)
        btn_re.setOnClickListener{
            btnRe()
        }
        btn_mi=findViewById(R.id.btn_mi)
        btn_mi.setOnClickListener{
            btnMi()
        }
        btn_fa=findViewById(R.id.btn_fa)
        btn_fa.setOnClickListener{
            btnFa()
        }
        btn_so=findViewById(R.id.btn_so)
        btn_so.setOnClickListener{
            btnSo()
        }
        btn_ra=findViewById(R.id.btn_ra)
        btn_ra.setOnClickListener {
            btnRa()
        }
        btn_si=findViewById(R.id.btn_si)
        btn_si.setOnClickListener{
            btnSi()
        }
        //楽譜を再生ボタン
        btn_play = findViewById(R.id.btn_play)
        btn_play.setOnClickListener{
            btnPlayClick()
        }
        //クリアボタン
        //btn_clear = findViewById<Button>(R.id.btn_clear)
        //btn_clear.setOnClickListener(Btn_AnswerClear_ClickListener())

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
