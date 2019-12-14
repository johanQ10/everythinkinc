package com.jlbit.everythinkinc.adapter

import android.annotation.SuppressLint
import android.view.*
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.jlbit.everythinkinc.MainActivity
import com.jlbit.everythinkinc.R
import com.jlbit.everythinkinc.client.Request
import com.jlbit.everythinkinc.fragment.DetailFragment
import com.jlbit.everythinkinc.model.User
import kotlinx.android.synthetic.main.item_user.view.*
import org.jetbrains.anko.backgroundColor

class UserListAdapter(private val users: List<User>,
                      private val mainActivity: MainActivity,
                      private val request: Request) : RecyclerView.Adapter<UserListAdapter.ViewHolder>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.item_user, parent, false)
        return ViewHolder(v)
    }

    override fun getItemCount(): Int = users.size

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        Glide
            .with(mainActivity)
            .load(users[position].picture.medium)
            .centerCrop()
            .thumbnail(0.1f)
            .placeholder(R.drawable.load)
            .error(R.drawable.error)
            .into(holder.imageItem)

        holder.textName.text = "${users[position].name.title} ${users[position].name.first} ${users[position].name.last}"
        holder.textGender.text = users[position].gender

        holder.itemView.setOnClickListener {
            holder.itemView.backgroundColor = mainActivity.resources.getColor(R.color.grayLight)

            mainActivity
                .supportFragmentManager
                .beginTransaction()
                .setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_left)
                .replace(R.id.frame_layout, DetailFragment().getInstance(users[position])!!)
                .addToBackStack(null)
                .commit()
        }
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        var imageItem: ImageView = itemView.image_item
        var textName: TextView = itemView.text_name
        var textGender: TextView = itemView.text_gender
    }
}