package com.example.headsup

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.headsup.databinding.ActivityMainBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.lang.Exception


class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var recyclerAdapter: CelebritiesAdapter

    private lateinit var celeb: ArrayList<CelebritiesItem>

    val apiInterface = APIClient().getClient()?.create(APIInterface::class.java)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        celeb = arrayListOf()
        recyclerAdapter = CelebritiesAdapter(celeb)
        binding.celebritiesRV.adapter = recyclerAdapter
        binding.celebritiesRV.layoutManager = LinearLayoutManager(this)

        binding.showAddCeleBtn.setOnClickListener {
            val intent = Intent(this, AddCelebrity::class.java)
            startActivity(intent)
        }

        binding.searchBtn.setOnClickListener {
            val intent = Intent(this, UpdateDeleteCelebrity::class.java)
            startActivity(intent)
            searchCelebrity()
        }

        apiInterface?.getCelebrities()?.enqueue(object : Callback<ArrayList<CelebritiesItem>>{
            override fun onResponse(
                call: Call<ArrayList<CelebritiesItem>>,
                response: Response<ArrayList<CelebritiesItem>>
            ) {
                celeb = response.body()!!
                recyclerAdapter.updateRecyclerView(celeb)
            }

            override fun onFailure(call: Call<ArrayList<CelebritiesItem>>, t: Throwable) {
                Log.d("main", "Unable to get celebrities $t")
            }

        })
    }

    private fun searchCelebrity() {

        var celebrityID = 0
        for(celebrity in celeb ) {
            if (binding.searchToDeleteUpdateET.text.toString() == celebrity.name) {
                celebrityID = celebrity.pk
                val intent = Intent(this, UpdateDeleteCelebrity::class.java)
                intent.putExtra("celebrityID", celebrityID)
                startActivity(intent)
            }
        }
    }
}