package com.example.headsup

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.example.headsup.databinding.ActivityAddCelebrityBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class AddCelebrity : AppCompatActivity() {
    private lateinit var binding: ActivityAddCelebrityBinding

  private val apiInterface = APIClient().getClient()?.create(APIInterface::class.java)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddCelebrityBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val actionBar = supportActionBar
//        if(actionBar != null) {
//            actionBar.title = "Add Celebrity"
//        }
        actionBar!!.title = "Add Celebrity"
        actionBar.setDisplayHomeAsUpEnabled(true)

        binding.addCelebrityBtn.setOnClickListener {
            apiInterface!!.addCelebrity(
                CelebritiesItem(
                    binding.addCelebrityNameET.text.toString(),
                    0,
                    binding.addTaboo1ET.text.toString(),
                    binding.addTaboo2ET.text.toString(),
                    binding.addTaboo3ET.text.toString()

                )
            ).enqueue(object : Callback<CelebritiesItem> {
                override fun onResponse(
                    call: Call<CelebritiesItem>,
                    response: Response<CelebritiesItem>
                ) {
                  Toast.makeText(this@AddCelebrity, "Celebrity Added!", Toast.LENGTH_LONG).show()
                }

                override fun onFailure(call: Call<CelebritiesItem>, t: Throwable) {
                    Log.d("add", "Could not Post data")
                    Toast.makeText(this@AddCelebrity, "Failed to add celebrity ", Toast.LENGTH_LONG).show()
                }

            })
            binding.addCelebrityNameET.text.clear()
            binding.addTaboo1ET.text.clear()
            binding.addTaboo2ET.text.clear()
            binding.addTaboo3ET.text.clear()
        }

    }
}