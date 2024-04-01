package ru.katkov.android.eureka.game

import android.content.Context
import android.graphics.drawable.Drawable
import android.graphics.drawable.GradientDrawable
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.GridView
import android.widget.ImageView
import androidx.core.content.ContextCompat
import ru.katkov.android.eureka.R
import ru.katkov.android.eureka.game.model.Eureka
import kotlin.math.ceil

class GridViewAdapter(
    private val drawableId: Int,
    private val context: Context,
    private val items: MutableList<Eureka.EurekaColor>,
    private val itemHeight: Int = -1
) : BaseAdapter() {

    private val rows: Int = ceil(items.size.toDouble() / 4).toInt()

    override fun getCount(): Int {
        return items.size
    }

    fun getItems(): List<Eureka.EurekaColor>{
        return items
    }

    override fun getItem(p0: Int): Any {
        return items[p0]
    }

    override fun getItemId(p0: Int): Long {
        return 0
    }

    fun setItem(i: Int, c: Eureka.EurekaColor) {
        items[i] = c
        notifyDataSetChanged()
    }

    fun clear() {
        items.replaceAll { Eureka.EurekaColor.GREY }
        notifyDataSetChanged()
    }


    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val imageView: ImageView
        if (convertView == null) {
            imageView = ImageView(context)
            val d: Drawable = ContextCompat.getDrawable(context, drawableId)!!
            imageView.setImageDrawable(d)
        } else imageView = convertView as ImageView
        if (itemHeight == -1) {
            imageView.minimumHeight =
                (parent.measuredHeight - (parent as GridView).verticalSpacing * (rows - 1)) / rows
        } else imageView.minimumHeight = itemHeight
        (imageView.drawable as GradientDrawable).setColor(items[position].color.toArgb())
        return imageView
    }
}