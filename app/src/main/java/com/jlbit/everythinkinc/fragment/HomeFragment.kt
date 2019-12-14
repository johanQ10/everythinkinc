package com.jlbit.everythinkinc.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.jlbit.everythinkinc.MainActivity
import com.jlbit.everythinkinc.R
import com.jlbit.everythinkinc.adapter.UserListAdapter
import com.jlbit.everythinkinc.client.Request
import com.jlbit.everythinkinc.model.Data
import com.jlbit.everythinkinc.model.User
import kotlinx.android.synthetic.main.fragment_home.view.*
import org.jetbrains.anko.support.v4.longToast
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class HomeFragment : Fragment() {
    private lateinit var mainActivity: MainActivity
    private lateinit var request: Request
    private lateinit var users: List<User>

    private lateinit var recyclerView: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mainActivity = activity as MainActivity
        request = Request(context!!)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val v = inflater.inflate(R.layout.fragment_home, container, false)

        mainActivity.actionBar.title = getString(R.string.users_list)
        recyclerView = v.recycler_view

        val dividerItemDecoration = DividerItemDecoration(activity, DividerItemDecoration.VERTICAL)
        dividerItemDecoration.setDrawable(activity?.resources!!.getDrawable(R.drawable.divider))

        recyclerView.addItemDecoration(dividerItemDecoration)
        recyclerView.setHasFixedSize(true)
        recyclerView.layoutManager = LinearLayoutManager(context)

        return v
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        getUsers()
    }

    private fun getUsers(){
        val call = request.retrofit().getUsers()

        call.enqueue(object : Callback<Data?> {
            override fun onResponse(call: Call<Data?>, response: Response<Data?>) {
                if(response.isSuccessful){

                    users = response.body()?.results!!
                    recyclerView.adapter = UserListAdapter(users, mainActivity, request)

                } else
                    longToast("${response.code()}: ${response.message()}")
            }
            override fun onFailure(call: Call<Data?>, t: Throwable) {
                longToast(t.message.toString())
            }
        })
    }
}
