package com.example.headsup

import android.content.DialogInterface
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.example.headsup.databinding.ActivityUpdateDeleteCelebrityBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.lang.Exception

class UpdateDeleteCelebrity : AppCompatActivity() {

    private lateinit var binding: ActivityUpdateDeleteCelebrityBinding
   private val apiInterface by lazy { APIClient().getClient()?.create(APIInterface::class.java) }
    var celebID = 0


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityUpdateDeleteCelebrityBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val actionBar = supportActionBar
        actionBar!!.title = "Update or Delete Celebrity"
        actionBar.setDisplayHomeAsUpEnabled(true)

        try {
            celebID = intent.extras!!.getInt("celebrityID", 0) // To pass "celebrityID" (pk) from the MainActivity to this activity

        } catch (e: Exception) {
            Toast.makeText(this, "Could not find celebrity", Toast.LENGTH_SHORT).show()
        }

        binding.updateBtn.setOnClickListener { updateCelebrity() }

        binding.deleteBtn.setOnClickListener { showDeleteConfirmationDialog() }

        getCelebrityToFillTheFields()
    }

   private fun getCelebrityToFillTheFields() {
        apiInterface?.getCelebrity(celebID)?.enqueue(object : Callback<CelebritiesItem>{
            override fun onResponse(
                call: Call<CelebritiesItem>,
                response: Response<CelebritiesItem>
            ) {
                val aCelebrity = response.body()!!

                binding.deleteUpdateNameET.setText(aCelebrity.name)
                binding.deleteUpdateTaboo1ET.setText(aCelebrity.taboo1)
                binding.deleteUpdateTaboo2ET.setText(aCelebrity.taboo2)
                binding.deleteUpdateTaboo3ET.setText(aCelebrity.taboo3)
            }

            override fun onFailure(call: Call<CelebritiesItem>, t: Throwable) {
                Toast.makeText(this@UpdateDeleteCelebrity, "Couldn't get celebrity", Toast.LENGTH_LONG).show()
            }
        })
    }

   private fun updateCelebrity() {

        if (binding.deleteUpdateNameET.text.isNotEmpty() &&
            binding.deleteUpdateTaboo1ET.text.isNotEmpty() &&
            binding.deleteUpdateTaboo2ET.text.isNotEmpty() &&
            binding.deleteUpdateTaboo3ET.text.isNotEmpty()) {

            apiInterface?.updateCelebrity(
                celebID, CelebritiesItem(
                    binding.deleteUpdateNameET.text.toString(),
                    celebID,
                    binding.deleteUpdateTaboo1ET.text.toString(),
                    binding.deleteUpdateTaboo2ET.text.toString(),
                    binding.deleteUpdateTaboo3ET.text.toString()
                )
            )?.enqueue(object : Callback<CelebritiesItem> {
                override fun onResponse(
                    call: Call<CelebritiesItem>,
                    response: Response<CelebritiesItem>
                ) {
                    Toast.makeText(this@UpdateDeleteCelebrity, "Celebrity Updated!", Toast.LENGTH_LONG).show()
                }

                override fun onFailure(call: Call<CelebritiesItem>, t: Throwable) {
                    Toast.makeText(this@UpdateDeleteCelebrity, "Could not update celebrity ", Toast.LENGTH_LONG).show()
                }
            })
        } else {
            Toast.makeText(this@UpdateDeleteCelebrity, "All Fields are Required", Toast.LENGTH_LONG).show()
        }
    }

    private fun showDeleteConfirmationDialog() {
        val dialogBuilder = AlertDialog.Builder(this)
        dialogBuilder.setMessage("Are you sure you want to delete this celebrity?")
        dialogBuilder.setTitle("Delete Confirmation")
            .setPositiveButton("Yes", DialogInterface.OnClickListener{
                _, _ ->

                apiInterface?.deleteCelebrity(celebID)?.enqueue(object : Callback<Void>{
                    override fun onResponse(call: Call<Void>, response: Response<Void>) {
                        Toast.makeText(this@UpdateDeleteCelebrity, "Celebrity Deleted", Toast.LENGTH_LONG).show()
                        binding.deleteUpdateNameET.text.clear()
                        binding.deleteUpdateTaboo1ET.text.clear()
                        binding.deleteUpdateTaboo2ET.text.clear()
                        binding.deleteUpdateTaboo3ET.text.clear()

                    }

                    override fun onFailure(call: Call<Void>, t: Throwable) {
                        Toast.makeText(this@UpdateDeleteCelebrity, "Could not delete celebrity ", Toast.LENGTH_LONG).show()
                    }
                })
            })
            .setNegativeButton("Cancel", DialogInterface.OnClickListener{
                dialog, _ ->  dialog.cancel()
            })
        val alert = dialogBuilder.create()
        alert.setCancelable(false)
        alert.show()

    }
}