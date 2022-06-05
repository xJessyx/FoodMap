package com.jessy.foodmap.itinerary.add

import android.app.Activity
import android.app.DatePickerDialog
import android.content.Intent
import android.content.pm.PackageManager
import android.icu.util.Calendar
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageButton
import android.widget.ProgressBar
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.github.dhaval2404.imagepicker.ImagePicker
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageMetadata
import com.google.firebase.storage.StorageReference
import com.jessy.foodmap.MainActivity
import com.jessy.foodmap.NavigationDirections
import com.jessy.foodmap.R
import com.jessy.foodmap.databinding.FragmentAddItineraryBinding
import java.io.File


class AddItineraryFragment : BottomSheetDialogFragment() {
    lateinit var startDate: Button
    lateinit var endDate: Button
    private val REQUEST_EXTERNAL_STORAGE = 1
    private var riversRef: StorageReference? = null
    private var mStorageRef: StorageReference? = null
    private var addArticle_upload_progress: ProgressBar? = null
    private var pick_img: ImageButton? = null
    private val viewModel: AddItineraryViewModel by lazy {
        ViewModelProvider(this)[AddItineraryViewModel::class.java]
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setStyle(STYLE_NORMAL, R.style.BottomSheetDialog)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        (activity as MainActivity).hideToolBar()
        val binding = FragmentAddItineraryBinding.inflate(inflater, container, false)
        binding.lifecycleOwner = this
        startDate = binding.addItineraryEdStartDate
        endDate = binding.addItineraryEdEndDate
        binding.viewModel = viewModel
        addArticle_upload_progress = binding.addItineraryUploadProgress
        pick_img = binding.addItineraryImg
        initData()

        binding.addItineraryEdStartDate.setOnClickListener {
            setStartData()
        }

        binding.addItineraryEdEndDate.setOnClickListener {
            setEndData()
        }

        binding.addItineraryBtn.setOnClickListener {

            if ((viewModel.itineraryName.value != null) && (viewModel.itineraryStartDate.value != null) && (viewModel.itineraryEndDate.value != null)) {
                viewModel.differenceDay()
                viewModel.addItineraryItem()
                viewModel.addFireBaseJourney()
                viewModel.addItinerary.observe(viewLifecycleOwner) {
                    it?.let {
                        Toast.makeText(activity as Activity, "已新增成功!!!", Toast.LENGTH_SHORT).show()

                        findNavController().navigate(NavigationDirections.addItineraryFragmentToItineraryDetailFragment(
                            it))

                    }
                }

            } else {
                Toast.makeText(activity as Activity, "有資料尚未填寫!!!", Toast.LENGTH_SHORT).show()
            }
        }

        binding.addItineraryImg.setOnClickListener {
            checkPermission()

        }
        return binding.root
    }

    private fun setStartData() {
        val c = Calendar.getInstance()
        val year = c.get(Calendar.YEAR)
        val month = c.get(Calendar.MONTH)
        val day = c.get(Calendar.DAY_OF_MONTH)
        DatePickerDialog(activity as Activity, { _, year, month, day ->
            run {
                val format = setDateFormat(year, month, day)
                startDate.text = format
            }
        }, year, month, day).show()
    }

    private fun setEndData() {
        val c = Calendar.getInstance()
        val year = c.get(Calendar.YEAR)
        val month = c.get(Calendar.MONTH)
        val day = c.get(Calendar.DAY_OF_MONTH)
        DatePickerDialog(activity as Activity, { _, year, month, day ->
            run {
                val format = setDateFormat(year, month, day)
                endDate.text = format
            }
        }, year, month, day).show()
    }

    private fun setDateFormat(year: Int, month: Int, day: Int): String {

        val monthString = String.format("%02d", month + 1)
        val dayString = String.format("%02d", day)

        return "$year-$monthString-$dayString"
    }

    private fun initData() {
        mStorageRef = FirebaseStorage.getInstance().reference
    }

    //上傳圖片至Storage
    private fun uploadImg(path: String) {
        val file = Uri.fromFile(File(path))
        val metadata = StorageMetadata.Builder()
            .setContentDisposition("universe")
            .setContentType("image/jpg")
            .build()
        riversRef = mStorageRef?.child(file.lastPathSegment ?: "")
        val uploadTask = riversRef?.putFile(file, metadata)

        val urlTask = uploadTask!!.continueWithTask { task ->
            if (!task.isSuccessful) {
                task.exception?.let {
                    throw it
                }
            }
            riversRef!!.downloadUrl
        }.addOnCompleteListener { task ->
            if (task.isSuccessful) {
                val downloadUri = task.result
                viewModel.itineraryImage = downloadUri.toString()

            } else {
                Log.v("downloadUri error", "下載失敗")

            }
        }

        uploadTask.addOnFailureListener { exception ->
            Log.v("exception.message fail", "${exception.message}")

        }.addOnSuccessListener {
            Log.v("upload_successe", "upload_success")
        }.addOnProgressListener { taskSnapshot ->
            val progress =
                (100.0 * taskSnapshot.bytesTransferred / taskSnapshot.totalByteCount).toInt()
            addArticle_upload_progress!!.progress = progress
            if (progress >= 100) {
                addArticle_upload_progress!!.visibility = View.GONE
            }
        }

    }

    //喚醒相簿選取器
    private fun getLocalImg() {
        ImagePicker.with(this)
            .crop()                    //Crop image(Optional), Check Customization for more option
            .compress(1024)            //Final image size will be less than 1 MB(Optional)
            .maxResultSize(1080,
                1080)    //Final image resolution will be less than 1080 x 1080(Optional)
            .start()
    }

    //請求權限
    private fun checkPermission() {
        val permission = ActivityCompat.checkSelfPermission((activity as Activity),
            android.Manifest.permission.WRITE_EXTERNAL_STORAGE)
        if (permission != PackageManager.PERMISSION_GRANTED) {
            //未取得權限，向使用者要求允許權限
            ActivityCompat.requestPermissions(activity as Activity,
                arrayOf(android.Manifest.permission.WRITE_EXTERNAL_STORAGE),
                REQUEST_EXTERNAL_STORAGE)
        } else {
            getLocalImg()
        }
    }

    //權限選取回報
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray,
    ) {
        when (requestCode) {
            REQUEST_EXTERNAL_STORAGE -> {
                if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    getLocalImg()
                } else {
                    Toast.makeText(activity as Activity, "請授權權限，不然無法操作", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }

    //透過覆寫 onActivityResult 來判斷從選取器回來的相簿資料
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when (resultCode) {
            Activity.RESULT_OK -> {

                data?.data?.let { uri ->
                    Log.d("Wayne", "uri = $uri")

                    val filePath = uri.path ?: ""
                    Log.d("Wayne", "filePath = $filePath")
                    if (filePath.isNotEmpty()) {
                        var imgPath = filePath
                        //Toast.makeText(activity as Activity , imgPath, Toast.LENGTH_SHORT).show()
                        Log.v("imgPath", "imgPath =$imgPath")
                        pick_img?.let { Glide.with(activity as Activity).load(filePath).into(it) }

                        if (imgPath.isNotEmpty()) {
                            addArticle_upload_progress!!.visibility = View.VISIBLE
                            uploadImg(imgPath)
                        } else {
                            Toast.makeText(activity as Activity,
                                R.string.select_image,
                                Toast.LENGTH_SHORT).show()
                        }
                    } else {
                        Toast.makeText(activity as Activity,
                            R.string.image_error,
                            Toast.LENGTH_SHORT).show()
                    }
                }

            }
            ImagePicker.RESULT_ERROR -> Toast.makeText(activity as Activity,
                ImagePicker.getError(data),
                Toast.LENGTH_SHORT).show()
            else -> Toast.makeText(activity as Activity,
                R.string.no_select_image,
                Toast.LENGTH_SHORT)
                .show()
        }
    }

}