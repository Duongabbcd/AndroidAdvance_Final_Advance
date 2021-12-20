package com.example.androidadvance_final_lequangdao.fragment

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.os.IBinder
import android.util.Log
import androidx.lifecycle.lifecycleScope
import com.example.androidadvance_final_lequangdao.utils.Constant.ACTION
import com.example.androidadvance_final_lequangdao.utils.Constant.LOCATION
import com.example.androidadvance_final_lequangdao.utils.Constant.OPERATOR
import com.example.androidadvance_final_lequangdao.databinding.FragmentClientBinding
import com.example.lequangdao.IMyServer
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


class ClientFragment : BaseFragment<FragmentClientBinding>() {
    private var serverService: IMyServer? = null

    private var isBound = false

    // Init connectionService (can extend ServiceConnection instead of Init)
    private val connection = object : ServiceConnection {
        override fun onServiceConnected(name: ComponentName?, service: IBinder?) {
            isBound = true
            serverService = IMyServer.Stub.asInterface(service)
        }

        override fun onServiceDisconnected(name: ComponentName?) {
            isBound = false
            serverService = null
        }
    }


    // BindService to Server
    private fun connectToRemoteService(){
        val intent = Intent(ACTION)
        context?.bindService(convertImplicitIntentToExplicitIntent(intent, requireContext()),connection,Context.BIND_AUTO_CREATE)
    }

    // Convert Implicit to Explicit Intent
    private fun convertImplicitIntentToExplicitIntent(implicitIntent: Intent, context: Context) : Intent{
        val pm = context.packageManager
        val resolveInfoList = pm.queryIntentServices(implicitIntent, 0)
        val explicitIntent = Intent(implicitIntent)
        if (resolveInfoList == null || resolveInfoList.size != 1) {
            return explicitIntent
        }
        val serviceInfo = resolveInfoList[0]
        val component =
            ComponentName(serviceInfo.serviceInfo.packageName, serviceInfo.serviceInfo.name)
        explicitIntent.component = component
        return explicitIntent
    }


    // Connect to Service in Server
    override fun onStart() {
        super.onStart()
        connectToRemoteService()
    }


    // Get Result from Server
    override fun initAction() {
        binding.btnOperation.setOnClickListener {
            lifecycleScope.launch(Dispatchers.IO) {
                val result = serverService?.operation()
                Log.d(ACTION,result.toString())
                withContext(Dispatchers.Main) {
                    showDialog(OPERATOR, result.toString())
                }
            }

        }
        binding.btnCurrentLocation.setOnClickListener {
            lifecycleScope.launch(Dispatchers.IO) {
                val result = serverService?.ocateMe()
                withContext(Dispatchers.Main) {
                    showDialog(LOCATION, result ?: "")
                }
            }
        }
    }




    //unbind at onStop
    override fun onStop() {
        super.onStop()
        if (isBound) {
            isBound = false
            context?.unbindService(connection)
        }
    }

    override fun getViewBinding(): FragmentClientBinding =
        FragmentClientBinding.inflate(layoutInflater)

}