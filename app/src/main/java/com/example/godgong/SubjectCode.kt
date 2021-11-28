package com.example.samplerecyclerview

import android.hardware.SensorManager.getOrientation
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.Button
import android.widget.Spinner
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.load.ImageHeaderParserUtils.getOrientation
import com.example.godgong.R
import com.example.godgong.SubjectInform
import com.google.firebase.firestore.FirebaseFirestore
import javax.security.auth.Subject


class MainActivity : AppCompatActivity() {
    var firestore : FirebaseFirestore? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_subject)
        val recyclerView = findViewById<View>(R.id.recyclerview) as RecyclerView
        // 파이어스토어 인스턴스 초기화
        firestore = FirebaseFirestore.getInstance()

        recyclerView.setAdapter(RecyclerViewAdapter())
        val dividerItemDecoration = DividerItemDecoration(recyclerView.getContext(),
                LinearLayoutManager.this)
        recyclerView.addItemDecoration(dividerItemDecoration)


        // 검색 옵션 변수
        var searchOption = "name"
        val spinner = findViewById<View>(R.id.spinner) as Spinner
        // 스피너 옵션에 따른 동작
        spinner.onItemSelectedListener = object: AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {
            }

            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                when (spinner.getItemAtPosition(position)) {
                    "이름" -> {
                        searchOption = "name"
                    }
                    "번호" -> {
                        searchOption = "phoneNumber"
                    }
                }
            }
        }
        val searchBtn = findViewById<View>(R.id.searchBtn) as Button
        // 검색 옵션에 따라 검색
        searchBtn.setOnClickListener {

            (recyclerView.setAdapter(RecyclerViewAdapter())
            val dividerItemDecoration = DividerItemDecoration(recyclerView.getContext(),
                    LinearLayoutManager.this)).search(searchWord.text.toString(), searchOption)
        }
    }

    inner class RecyclerViewAdapter : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
        // Person 클래스 ArrayList 생성성
        var telephoneBook : ArrayList<SubjectInform> = arrayListOf()

        init {  // telephoneBook의 문서를 불러온 뒤 Person으로 변환해 ArrayList에 담음
            firestore?.collection("telephoneBook")?.addSnapshotListener { querySnapshot, firebaseFirestoreException ->
                // ArrayList 비워줌
                telephoneBook.clear()

                for (snapshot in querySnapshot!!.documents) {
                    var item = snapshot.toObject(SubjectInform.class)
                    telephoneBook.add(item!!)
                }
                notifyDataSetChanged()
            }
        }

        // xml파일을 inflate하여 ViewHolder를 생성
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
            var view = LayoutInflater.from(parent.context).inflate(R.layout.item, parent, false)
            return ViewHolder(view)
        }

        inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        }

        // onCreateViewHolder에서 만든 view와 실제 데이터를 연결
        override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
            var viewHolder = (holder as ViewHolder).itemView

            (viewHolder.name).text = telephoneBook[position].name
            viewHolder.phoneNumber.text = telephoneBook[position].phoneNumber
        }

        // 리사이클러뷰의 아이템 총 개수 반환
        override fun getItemCount(): Int {
            return telephoneBook.size
        }

        // 파이어스토어에서 데이터를 불러와서 검색어가 있는지 판단
        fun search(searchWord : String, option : String) {
            firestore?.collection("telephoneBook")?.addSnapshotListener { querySnapshot, firebaseFirestoreException ->
                // ArrayList 비워줌
                telephoneBook.clear()

                for (snapshot in querySnapshot!!.documents) {
                    if (snapshot.getString(option)!!.contains(searchWord)) {
                        var item = snapshot.toObject(SubjectInform.class)
                        telephoneBook.add(item)
                    }
                }
                notifyDataSetChanged()
            }
        }
    }
}