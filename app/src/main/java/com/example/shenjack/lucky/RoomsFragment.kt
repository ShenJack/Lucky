package com.example.shenjack.lucky

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.shenjack.lucky.R.id.refresh
import com.example.shenjack.lucky.data.Response
import com.example.shenjack.lucky.data.Room
import com.example.shenjack.lucky.data.remote.apiService

import com.example.shenjack.lucky.dummy.DummyContent
import com.example.shenjack.lucky.dummy.DummyContent.DummyItem
import io.reactivex.Observer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import org.jetbrains.anko.support.v4.find
import org.jetbrains.anko.support.v4.toast
import org.jetbrains.anko.*


/**
 * A fragment representing a list of Items.
 *
 *
 * Activities containing this fragment MUST implement the [OnListFragmentInteractionListener]
 * interface.
 */
/**
 * Mandatory empty constructor for the fragment manager to instantiate the
 * fragment (e.g. upon screen orientation changes).
 */
class RoomsFragment : Fragment() {
    // TODO: Customize parameters
    private var mColumnCount = 1
    var roomList: RecyclerView? = null
    var refresh:SwipeRefreshLayout? = null
    private var mListener: OnListFragmentInteractionListener? = object : OnListFragmentInteractionListener {
        override fun onListFragmentInteraction(item: Room) {
            val intent = Intent(activity, RoomDetailActivity::class.java)
            intent.putExtra(RoomDetailActivity.ROOM_ID,item.name)
            activity.startActivity(intent)
        }
    }

    var adapter = RoomsRecyclerViewAdapter(ArrayList(), mListener)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if (arguments != null) {
            mColumnCount = arguments.getInt(ARG_COLUMN_COUNT)
        }
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val view = inflater!!.inflate(R.layout.fragment_item_list, container, false)

        return view
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        roomList = find<RecyclerView>(R.id.list)
        refresh = find<SwipeRefreshLayout>(R.id.refresh)
        roomList!!.layoutManager = LinearLayoutManager(activity)
        roomList!!.adapter = adapter
        getRooms()
        find<SwipeRefreshLayout>(R.id.refresh).setOnRefreshListener { getRooms() }
    }


    override fun onAttach(context: Context?) {
        super.onAttach(context)
    }


    override fun onDetach() {
        super.onDetach()
        mListener = null
    }

    private fun getRooms() {
        (activity as BaseActivity).showProgress(true)
        apiService.apiServiceInstance!!.getRooms()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.newThread())
                .subscribe(object : Observer<Response<List<Room>>> {
                    override fun onNext(t: Response<List<Room>>) {
                        adapter.mValues = t.data
                        adapter.notifyDataSetChanged()
                    }

                    override fun onSubscribe(d: Disposable) {

                    }

                    override fun onError(e: Throwable) {
                        refresh!!.isRefreshing = false
                        (activity as BaseActivity).showProgress(false)
                        toast("error")
                    }

                    override fun onComplete() {
                        refresh!!.isRefreshing = false
                        (activity as BaseActivity).showProgress(false)
                    }
                })
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     *
     *
     * See the Android Training lesson [Communicating with Other Fragments](http://developer.android.com/training/basics/fragments/communicating.html) for more information.
     */
    interface OnListFragmentInteractionListener {
        // TODO: Update argument type and name
        fun onListFragmentInteraction(item: Room)
    }

    companion object {

        // TODO: Customize parameter argument names
        private val ARG_COLUMN_COUNT = "column-count"

        // TODO: Customize parameter initialization
        fun newInstance(columnCount: Int): RoomsFragment {
            val fragment = RoomsFragment()
            val args = Bundle()
            args.putInt(ARG_COLUMN_COUNT, columnCount)
            fragment.arguments = args
            return fragment
        }
    }
}
