package com.jessy.foodmap.home.addArticles

import android.app.Activity
import android.content.ContentResolver
import android.content.ContentValues.TAG
import android.content.Intent
import android.content.pm.PackageManager
import android.database.Cursor
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore.MediaColumns
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.ProgressBar
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.github.dhaval2404.imagepicker.ImagePicker
import com.google.android.gms.common.api.Status
import com.google.android.libraries.places.api.Places
import com.google.android.libraries.places.api.model.AutocompleteSessionToken
import com.google.android.libraries.places.api.model.Place
import com.google.android.libraries.places.api.net.PlacesClient
import com.google.android.libraries.places.widget.AutocompleteSupportFragment
import com.google.android.libraries.places.widget.listener.PlaceSelectionListener
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageMetadata
import com.google.firebase.storage.StorageReference
import com.jessy.foodmap.MainActivity
import com.jessy.foodmap.NavigationDirections
import com.jessy.foodmap.R
import com.jessy.foodmap.databinding.FragmentAddArticleBinding
import java.io.File


class AddArticleFragment : Fragment() {
    private val viewModel: AddArticleViewModel by lazy {
        ViewModelProvider(this).get(AddArticleViewModel::class.java)
    }
    private lateinit var placesClient: PlacesClient
    lateinit var autocompleteSessionToken: AutocompleteSessionToken
   val REQUEST_EXTERNAL_STORAGE = 1
    private var imgPath: String = ""
    private var riversRef: StorageReference? = null
    private var mStorageRef: StorageReference? = null
    var addArticle_upload_progress: ProgressBar? =null
    var pick_img: ImageButton ?=null


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        (activity as MainActivity).hideToolBar()
        val binding = FragmentAddArticleBinding.inflate(inflater, container, false)
        binding.lifecycleOwner = viewLifecycleOwner
        binding.viewModel = viewModel
        addArticle_upload_progress = binding.addArticleUploadProgress
        pick_img = binding.addArticleImg

        // Initialize the AutocompleteSupportFragment.
        val autocompleteFragment =
            childFragmentManager.findFragmentById(R.id.addArticle_autocomplete)
                    as AutocompleteSupportFragment
        initData()
        initPlaces()
        // Specify the types of place data to return.
        autocompleteFragment.setPlaceFields(listOf(Place.Field.ID, Place.Field.NAME))

        // Set up a PlaceSelectionListener to handle the response.
        autocompleteFragment.setOnPlaceSelectedListener(object : PlaceSelectionListener {
            override fun onPlaceSelected(place: Place) {
                // TODO: Get info about the selected place.
                Log.i(TAG, "Place: ${place.name}, ${place.id}")
                viewModel.articlePlaceName =  place.name
            }

            override fun onError(status: Status) {
                // TODO: Handle the error.
                Log.i(TAG, "An error occurred: $status")
            }
        })

        binding.addArticleImg.setOnClickListener {
            checkPermission()

        }

        binding.addArticleBtn.setOnClickListener {

            if( (viewModel.articleTitle.value != null) &&( viewModel.articleConent.value !=null) &&(viewModel.articlePlaceName != null)) {
                viewModel.addArticleItem()
                viewModel.addFireBaseArticle()
                viewModel.addArticle.observe(viewLifecycleOwner){
                    it?.let {
                        Toast.makeText(activity as Activity, "已新增成功!!!", Toast.LENGTH_SHORT).show()
                        findNavController().navigate(NavigationDirections.addArticleFragmentHomeFragment())

                    }
                }
            } else {
                Toast.makeText(activity as Activity, "有資料尚未填寫!!!", Toast.LENGTH_SHORT).show()
            }
        }

        return binding.root
    }

    private fun initPlaces() {
        val info = (activity as MainActivity).applicationContext.packageManager
            .getApplicationInfo(
                (activity as MainActivity).packageName,
                PackageManager.GET_META_DATA
            )

        val key = info.metaData[resources.getString(R.string.map_api_key_name)].toString()
        Places.initialize(requireContext(), key)

//        Places.initialize(requireActivity().getApplicationContext(), BuildConfig.MAPS_API_KEY)
        placesClient = Places.createClient(activity as Activity)
        autocompleteSessionToken = AutocompleteSessionToken.newInstance()
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
                viewModel.articleImage = downloadUri.toString()
                Log.v("downloadUri", "$downloadUri")

            } else {
                Log.v("downloadUri error", "下載失敗")

            }
        }

        uploadTask?.addOnFailureListener { exception ->
                Log.v("exception.message fail","${exception.message}")

        }?.addOnSuccessListener {
            Log.v("upload_successe","upload_success")
        }?.addOnProgressListener { taskSnapshot ->
            val progress = (100.0 * taskSnapshot.bytesTransferred / taskSnapshot.totalByteCount).toInt()
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
            .maxResultSize(1080, 1080)    //Final image resolution will be less than 1080 x 1080(Optional)
            .start()
    }

    //請求權限
    private fun checkPermission() {
        val permission = ActivityCompat.checkSelfPermission((activity as Activity), android.Manifest.permission.WRITE_EXTERNAL_STORAGE)
        if (permission != PackageManager.PERMISSION_GRANTED) {
            //未取得權限，向使用者要求允許權限
            ActivityCompat.requestPermissions(activity as Activity, arrayOf(android.Manifest.permission.WRITE_EXTERNAL_STORAGE), REQUEST_EXTERNAL_STORAGE)
        } else {
            getLocalImg()
        }
    }

    //權限選取回報
    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
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
                        // Toast.makeText(activity as Activity , imgPath, Toast.LENGTH_SHORT).show()
                        Log.v("imgPath","imgPath =$imgPath")
                        pick_img?.let { Glide.with(activity as Activity).load(filePath).into(it) }

                        if (imgPath.isNotEmpty()) {
                            addArticle_upload_progress!!.visibility = View.VISIBLE
                            uploadImg(imgPath)
                        } else {
                            Toast.makeText(activity as Activity, "請選取照片", Toast.LENGTH_SHORT).show()
                        }
                    } else {
                        Toast.makeText(activity as Activity,"讀取圖片失敗", Toast.LENGTH_SHORT).show()
                    }
                }
            }
            ImagePicker.RESULT_ERROR -> Toast.makeText(activity as Activity, ImagePicker.getError(data), Toast.LENGTH_SHORT).show()
            else -> Toast.makeText(activity as Activity, "Task Cancelled", Toast.LENGTH_SHORT).show()
        }
    }



}

fun Fragment.getFilePathFromContentUri(
    selectedVideoUri: Uri
): String {
    var filePath = ""
    val filePathColumn = arrayOf(MediaColumns.DATA)
    val cursor: Cursor? =
        requireContext().contentResolver.query(selectedVideoUri, filePathColumn, null, null, null)
    cursor?.let {

        cursor.moveToFirst()
        val columnIndex: Int = cursor.getColumnIndex(filePathColumn[0])
        filePath = cursor.getString(columnIndex)
        cursor.close()
    }
    return filePath
}