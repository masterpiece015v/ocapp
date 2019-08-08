package com.example.ocuser.ocapp

import android.media.AudioAttributes
import android.media.SoundPool
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.constraint.ConstraintLayout
import android.support.constraint.ConstraintSet
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.Toast
import android.widget.ToggleButton
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

data class Note(val oto : String , val delay : Long )

class MainActivity : AppCompatActivity() {
    private lateinit var soundPool : SoundPool
    val scoreList = mutableListOf<Note>()        //楽譜
    val scaleMap = mutableMapOf<String,Int>()    //音階と音源の対応
    lateinit var const_set : ConstraintSet      //制約のセット
    var w_margin : Int = 0                      //ひとつ前のid
    var nowTgl = "tgl04"
    var measure = 0 //１小節分の計算
    var measure_cnt = 0
    //View
    lateinit var btn_do1 : Button                //ドの鍵盤
    lateinit var btn_re1 : Button                //レの鍵盤
    lateinit var btn_mi1 : Button                //ミの鍵盤
    lateinit var btn_fa1 : Button                //ファの鍵盤
    lateinit var btn_so1 : Button                //ソの鍵盤
    lateinit var btn_ra1 : Button                //ラの鍵盤
    lateinit var btn_si1 : Button                //シの鍵盤
    lateinit var btn_do2 : Button               //高いドの鍵盤
    lateinit var btn_kyufu : Button             //休符ボタン
    lateinit var btn_play : Button               //再生のボタン
    lateinit var btn_clear : Button             //クリアのボタン
    lateinit var lay_score : ConstraintLayout   //楽譜
    lateinit var tgl00 : ToggleButton           //トグルボタン
    lateinit var tgl02 : ToggleButton
    lateinit var tgl04 : ToggleButton
    lateinit var tgl08 : ToggleButton
    lateinit var tgl16 : ToggleButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        //初期化
        init()
    }

    //ドの鍵盤
    fun btnDo1(){
        sound("ド1")
    }
    //レの鍵盤
    fun btnRe1(){
        sound( "レ1")
    }
    //ミの鍵盤
    fun btnMi1(){
        sound( "ミ1")
    }
    //ファの鍵盤
    fun btnFa1(){
        sound( "ファ1")
    }
    //ソの鍵盤
    fun btnSo1(){
        sound( "ソ1")
    }
    //ラの鍵盤
    fun btnRa1(){
        sound( "ラ1")
    }
    //シの鍵盤
    fun btnSi1(){
        sound( "シ1")

    }
    //ドの鍵盤
    fun btnDo2(){
        sound( "ド2")
    }

    //再生ボタンをクリックした時の処理
    fun btnPlayClick(){
        setScore( "ド1" , 800 )
        setScore( "レ1" , 400 )
    }

    //scoreListを再生する非同期プログラム
    fun scorePlay(){
        GlobalScope.launch( Dispatchers.Main ){
            btn_play.isEnabled = false
            async( Dispatchers.Default ){
                scoreList.forEach{
                    if( it.oto != "休") {
                        sound(it.oto)
                    }
                    Thread.sleep( it.delay )
                }
                return@async
            }.await()
            btn_play.isEnabled = true
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
        //トグルボタンの設定
        tgl00 = findViewById(R.id.tgl00)
        tgl02 = findViewById(R.id.tgl02)
        tgl04 = findViewById(R.id.tgl04)
        tgl08 = findViewById(R.id.tgl08)
        tgl16 = findViewById(R.id.tgl16)
        tgl00.setOnCheckedChangeListener { compoundButton, b ->
            if(b) {
                tglChange("tgl00")
            }
        }
        tgl02.setOnCheckedChangeListener { compoundButton, b ->
            if(b) {
                tglChange("tgl02")
            }
        }
        tgl04.setOnCheckedChangeListener{ compoundButton, b ->
            if(b) {
                tglChange("tgl04")
            }
        }
        tgl08.setOnCheckedChangeListener{ compoundButton, b ->
            if(b) {
                tglChange("tgl08")
            }
        }
        tgl16.setOnCheckedChangeListener{ compoundButton, b ->
            if(b) {
                tglChange("tgl16")
            }
        }
        //鍵盤にイベントを割り当てる
        btn_do1 = findViewById(R.id.btn_do1)
        btn_do1.setOnClickListener{
            btnDo1()
            addLayScore( createImageView(getOnpuId()), 88)
        }
        btn_re1 = findViewById(R.id.btn_re1)
        btn_re1.setOnClickListener{
            btnRe1()
            addLayScore( createImageView(getOnpuId()), 78)
        }
        btn_mi1=findViewById(R.id.btn_mi1)
        btn_mi1.setOnClickListener{
            btnMi1()
            addLayScore( createImageView(getOnpuId()), 68)
        }
        btn_fa1=findViewById(R.id.btn_fa1)
        btn_fa1.setOnClickListener{
            btnFa1()
            addLayScore( createImageView(getOnpuId()), 58)
        }
        btn_so1=findViewById(R.id.btn_so1)
        btn_so1.setOnClickListener{
            btnSo1()
            addLayScore( createImageView(getOnpuId()), 48)
        }
        btn_ra1=findViewById(R.id.btn_ra1)
        btn_ra1.setOnClickListener {
            btnRa1()
            addLayScore( createImageView(getOnpuId()), 38)
        }
        btn_si1=findViewById(R.id.btn_si1)
        btn_si1.setOnClickListener{
            btnSi1()
            addLayScore( createImageView(getOnpuId()), 28)
        }
        btn_do2= findViewById( R.id.btn_do2)
        btn_do2.setOnClickListener{
            btnDo2()
            addLayScore( createImageView(getOnpuId()), 18)
        }
        btn_kyufu = findViewById(R.id.btn_kyufu)
        btn_kyufu.setOnClickListener {
            addLayScore( createImageView(getKyufuId()),48)
        }

        //楽譜を再生ボタン
        btn_play = findViewById(R.id.btn_play)
        btn_play.setOnClickListener{
            btnPlayClick()
            scorePlay()
        }
        //クリアボタン
        btn_clear = findViewById(R.id.btn_clear)
        btn_clear.setOnClickListener{
            btnClearClick()
        }
        //楽譜のレイアウト
        lay_score = findViewById(R.id.lay_score)
        //コンストレイントセット
        const_set = ConstraintSet()
    }

    //クリアボタンをクリックしたときのイベントメソッド
    fun btnClearClick(){
        lay_score.removeAllViews()
        measure_cnt = 0
        measure = 0
        w_margin = 0
    }

    //tglにしたがって音符のIDを取得する
    fun getOnpuId():Int{
        when(nowTgl){
            "tgl00"->{ return R.drawable.onpu02 }
            "tgl02"->{ return R.drawable.onpu02 }
            "tgl04"->{ return R.drawable.onpu04 }
            "tgl08"->{ return R.drawable.onpu08 }
            "tgl16"->{ return R.drawable.onpu16 }
        }
        return 0
    }
    fun getKyufuId():Int{
        when(nowTgl){
            "tgl00"->{return R.drawable.kyufu00 }
            "tgl02"->{return R.drawable.kyufu02 }
            "tgl04"->{return R.drawable.kyufu04 }
            "tgl08"->{return R.drawable.kyufu08 }
            "tgl16"->{return R.drawable.kyufu16 }
        }
        return 0
    }
    //tglにしたがって音符間の距離を取得
    fun getLeftDist():Int{
        when(nowTgl){
            "tgl00"->{ return 160 }
            "tgl02"->{ return 120 }
            "tgl04"->{ return 80 }
            "tgl08"->{ return 40 }
            "tgl16"->{ return 20 }
        }
        return 0
    }
    //トグルボタンを切り替える
    fun tglChange(tglname : String){
        nowTgl = tglname
        if(tglname!="tgl00"){ tgl00.isChecked = false }
        if(tglname!="tgl02"){ tgl02.isChecked = false }
        if(tglname!="tgl04"){ tgl04.isChecked = false }
        if(tglname!="tgl08"){ tgl08.isChecked = false }
        if(tglname!="tgl16"){ tgl16.isChecked = false }
    }
    //音を設定する
    fun setScore( oto : String , delay : Long){
        scoreList.add( Note( oto , delay ))
    }
    //音を出す
    fun sound( oto :String ){
        soundPool.play( scaleMap[oto]!!,1.0f,1.0f,0,0,1.0f)
    }
    //画像を作る
    fun createImageView( id : Int ): ImageView {
        val img = ImageView( this )
        img.setImageResource( id )
        img.id = View.generateViewId()
        return img
    }
    //1小節チェック
    fun checkMeasure():Int{
        val tglCost = mapOf("tgl00" to 0,"tgl02" to 8 , "tgl04" to 4,"tgl08" to 2,"tgl16" to 1)
        //-1:少ない 0:丁度 1:超えている
        if(tglCost[nowTgl]==0){
            return if(measure==0) 0 else 1
        }else{
            if(measure+tglCost[nowTgl]!!>16){
                return 1
            }else if(measure+tglCost[nowTgl]!! == 16){
                return 0
            }else{
                return -1
            }
        }
        return 0
    }
    //音符をlay_scoreに追加する
    fun addLayScore( img : ImageView , h_margin : Int ){
        val check = checkMeasure()
        when(check){
            -1->{
                //１小節の最初又は途中
                const_set.clone(lay_score)
                lay_score.addView(img)
                const_set.constrainHeight(img.id, 80)
                const_set.constrainWidth(img.id, 45)

                //最初の音符はLEFT_MARGIN=10
                if (measure == 0 && measure_cnt==0) {
                    w_margin = 40
                } else if(measure == 0 && measure_cnt > 0 ) {
                } else {
                    w_margin += getLeftDist()
                }
                const_set.connect(img.id, ConstraintSet.LEFT, ConstraintSet.PARENT_ID, ConstraintSet.LEFT, w_margin)
                const_set.connect(img.id, ConstraintSet.TOP, ConstraintSet.PARENT_ID, ConstraintSet.TOP, h_margin)
                const_set.applyTo(lay_score)
                when(nowTgl){
                    "tgl00"->{measure=0}
                    "tgl02"->{measure+=8}
                    "tgl04"->{measure+=4}
                    "tgl08"->{measure+=2}
                    "tgl16"->{measure+=1}
                }
                Log.d("debug","${check},${measure},${measure_cnt},${w_margin}")
            }
            0->{
                //１小節の最後
                const_set.clone(lay_score)
                lay_score.addView(img)
                const_set.constrainHeight(img.id, 80)
                const_set.constrainWidth(img.id, 45)

                w_margin += getLeftDist()

                const_set.connect(img.id, ConstraintSet.LEFT, ConstraintSet.PARENT_ID, ConstraintSet.LEFT, w_margin)
                const_set.connect(img.id, ConstraintSet.TOP, ConstraintSet.PARENT_ID, ConstraintSet.TOP, h_margin)
                const_set.applyTo(lay_score)
                measure = 0
                measure_cnt+=1

                Log.d("debug","${check},${measure},${measure_cnt},${w_margin}")

                w_margin = measure_cnt*340+40
            }
            1->{
                Toast.makeText(this,"数が合いません",Toast.LENGTH_SHORT).show()
            }

        }

    }
}