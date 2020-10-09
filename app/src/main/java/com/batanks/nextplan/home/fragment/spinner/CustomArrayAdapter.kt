package com.batanks.nextplan.home.fragment.spinner

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import com.batanks.nextplan.R
import com.batanks.nextplan.swagger.model.CategoryList
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.custom_spinner_adater_item.view.*
import java.io.IOException
import java.net.HttpURLConnection
import java.net.URL

class CustomArrayAdapter(private val mContext: Context, private val modelList: List<CategoryList>)
    : ArrayAdapter<CategoryList>(mContext, 0, modelList) {

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        return createItemView(position, convertView, parent)
    }

    override fun getDropDownView(position: Int, convertView: View?, parent: ViewGroup): View {
        return createItemView(position, convertView, parent)
    }

    private fun createItemView(position: Int, convertView: View?, parent: ViewGroup): View {

        val model = getItem(position)
        val view = convertView
                ?: LayoutInflater.from(parent.context).inflate(R.layout.custom_spinner_adater_item, parent, false)

        //model?.id?.let { view.categoryIcon.setImageResource(it) }
        //view.categoryTitle.text = model?.title
        //val imageUri : Uri = Uri.parse(model?.picture)
        //var bitmap : Bitmap = MediaStore.Images.Media.getBitmap(mContext.contentResolver,imageUri)
        //var img : Bitmap? = getBitmapFromURL(model?.picture)
        //model?.picture?.let { view.categoryIcon.setImageURI(imageUri) }
        //model?.drawable =  Glide.with(mContext).load(model?.picture).into(view.categoryIcon)
        Glide.with(mContext).load(model?.picture).into(view.categoryIcon)
        view.categoryTitle.text = model?.name
        view.categoryId.text = model?.pk.toString()

        return view
    }

    /*fun getBitmapFromURL(src:String?): Bitmap? {
        try
        {
            val url = URL(src)
            val connection = url.openConnection() as HttpURLConnection
            connection.setDoInput(true)
            connection.connect()
            val input = connection.getInputStream()
            val myBitmap = BitmapFactory.decodeStream(input)
            return myBitmap
        }
        catch (e: IOException) {
            // Log exception
            return null
        }
    }*/
}