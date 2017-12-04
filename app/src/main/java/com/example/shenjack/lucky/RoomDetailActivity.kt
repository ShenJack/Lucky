package com.example.shenjack.lucky

import android.databinding.DataBindingUtil
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.widget.Button
import android.widget.EditText
import com.example.shenjack.lucky.data.Message
import com.example.shenjack.lucky.data.Response
import com.example.shenjack.lucky.data.remote.Api
import com.example.shenjack.lucky.data.remote.apiService
import com.example.shenjack.lucky.databinding.MessageBinding
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import io.reactivex.Observer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_room_detail.*
import okhttp3.Request
import okhttp3.WebSocket
import okhttp3.WebSocketListener
import okio.ByteString
import org.jetbrains.anko.find
import org.jetbrains.anko.sdk25.coroutines.onClick
import org.jetbrains.anko.toast

class RoomDetailActivity : BaseActivity() {
    var room_id: String? = null
    var text: EditText? = null
    var sendButton: Button? = null
    var chatSocket: WebSocket? = null
    var gson: Gson = Gson()

    companion object {
        val ROOM_ID: String? = "room_id"
    }

    override fun getLayoutId(): Int {
        return R.layout.activity_room_detail
    }

    override fun initViews() {
        super.initViews()
        room_id = intent.getStringExtra(ROOM_ID)
        text = find<EditText>(R.id.text)
        sendButton = find<Button>(R.id.send)
        sendButton!!.onClick {
            var outMessage: Message = Message(text!!.text.toString(), "Test")
            sendMessage(outMessage)
        }


        title = room_id
        getMessages()
        connectWebSocket()
    }

    private fun sendMessage(outMessage: Message) {
        chatSocket!!.send(gson.toJson(outMessage))
    }

    private fun getMessages() {
        showProgress(true)
        apiService.apiServiceInstance!!.enterRoom(room_id!!)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.newThread())
                .subscribe(object : Observer<Response<List<Message>>> {
                    override fun onSubscribe(d: Disposable) {
                    }

                    override fun onError(e: Throwable) {
                        showProgress(false)
                        toast(e.message!!.toString())
                        Handler().postDelayed({ finish() }, 150)
                    }

                    override fun onNext(t: Response<List<Message>>) {
                        addMessages(t.data)
                    }

                    override fun onComplete() {
                        showProgress(false)
                    }

                })
    }

    private fun addMessages(data: List<Message>?) {
        for (message in data!!) {
            var binding: MessageBinding? = null

            binding = DataBindingUtil.inflate<MessageBinding>(LayoutInflater.from(this), R.layout.message, null, false)
            binding!!.message = message
            messages.addView(binding.root)
        }
        scroll.fullScroll(View.FOCUS_DOWN)

    }

    private fun connectWebSocket() {
        val wsUrl = Api.BASE_WS_URL + "room/" + room_id
        val request = Request.Builder()
                .url(wsUrl)
                .build()
        chatSocket = apiService.okHttpClienInstance!!.newWebSocket(request, object : WebSocketListener() {
            override fun onOpen(webSocket: WebSocket?, response: okhttp3.Response?) {
                super.onOpen(webSocket, response)
            }

            override fun onFailure(webSocket: WebSocket?, t: Throwable?, response: okhttp3.Response?) {
                super.onFailure(webSocket, t, response)
            }

            override fun onClosing(webSocket: WebSocket?, code: Int, reason: String?) {
                super.onClosing(webSocket, code, reason)
            }

            override fun onMessage(webSocket: WebSocket?, text: String?) {
                val massage = gson.fromJson(text, Message::class.java)
                runOnUiThread { addMessage(massage) }
            }

            override fun onMessage(webSocket: WebSocket?, bytes: ByteString?) {
                super.onMessage(webSocket, bytes)
            }

            override fun onClosed(webSocket: WebSocket?, code: Int, reason: String?) {
                super.onClosed(webSocket, code, reason)
            }
        })
    }

    private fun addMessage(message: Message?) {
        var binding: MessageBinding? = null

        try {
            binding = DataBindingUtil.inflate<MessageBinding>(LayoutInflater.from(this), R.layout.message, null, false)
        } catch (e: Error) {

        }

        binding!!.message = message
        messages.addView(binding.root)
        scroll.fullScroll(View.FOCUS_DOWN)
    }
}
