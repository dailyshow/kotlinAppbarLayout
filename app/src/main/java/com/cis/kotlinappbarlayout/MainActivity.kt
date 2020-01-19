package com.cis.kotlinappbarlayout

import android.content.DialogInterface
import android.graphics.Color
import android.os.Bundle
import android.view.Gravity
import android.widget.ArrayAdapter
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.input.*

class MainActivity : AppCompatActivity() {
    val dataList = arrayListOf<String>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        supportActionBar?.hide()

        toolbar.title = "Appbar layout"
        // 색상은 toolbar에 직접 적용하는게 아니라 collapsingToolbarLayout에 적용해줘야 적용된다.
        // CollapsingToolbarLayout 에 id를 toolbarLayout 으로 지정했었다.
        toolbarLayout.setCollapsedTitleTextColor(Color.CYAN)
        toolbarLayout.setExpandedTitleColor(Color.WHITE)
        toolbarLayout.collapsedTitleGravity = Gravity.CENTER_HORIZONTAL
        toolbarLayout.expandedTitleGravity = Gravity.RIGHT + Gravity.TOP

        // listview 를 nestedScrollView 안에 그냥 사용하면 화면이 제대로 나타나지 않는다.
        // 그래서 xml에 fillViewport 의 값으로 true를 넣어서 제대로 다 표시되도록 하였으나,
        // data가 많아져서 스크롤이 가능해졌는데도 아래 내용까지 스크롤 되지 않는 현상이 있었음.
        // 찾아보니 nestedScrollView 안에서 listview scroll이 기본적으로는 되지 않는것 같음.
        // 아래 소스를 입력해줘야 listview의 스크롤이 가능하다.
        ViewCompat.setNestedScrollingEnabled(list1, true)

        // collapsingToolbar에 이미지를 넣어주기
        app_bar_image.setImageResource(R.drawable.img_android)

        list1.adapter = ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, dataList)

        val listener = listener()

        fab.setOnClickListener {
            val builder = AlertDialog.Builder(this)
            builder.setTitle("문자열 입력")

            val view = layoutInflater.inflate(R.layout.input, null)
            builder.setView(view)

            builder.setNegativeButton("취소", null)
            builder.setPositiveButton("확인", listener)
            builder.setCancelable(false)

            builder.show()
        }
    }

    inner class listener : DialogInterface.OnClickListener {
        override fun onClick(dialog: DialogInterface?, which: Int) {
            val alert = dialog as AlertDialog
            val str1 = alert.et.text.toString()

            dataList.add(str1)

            val adapter = list1.adapter as ArrayAdapter<String>
            adapter.notifyDataSetChanged()

        }

    }
}
