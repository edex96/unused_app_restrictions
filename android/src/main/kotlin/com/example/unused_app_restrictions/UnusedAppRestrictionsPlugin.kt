package com.example.unused_app_restrictions

import android.content.Context
import androidx.annotation.NonNull
import io.flutter.embedding.engine.plugins.FlutterPlugin
import io.flutter.plugin.common.MethodCall
import io.flutter.plugin.common.MethodChannel
import io.flutter.plugin.common.MethodChannel.MethodCallHandler
import io.flutter.plugin.common.MethodChannel.Result
//
import androidx.core.content.UnusedAppRestrictionsConstants.API_30
import androidx.core.content.UnusedAppRestrictionsConstants.API_30_BACKPORT
import androidx.core.content.UnusedAppRestrictionsConstants.API_31
import androidx.core.content.UnusedAppRestrictionsConstants.DISABLED
import androidx.core.content.UnusedAppRestrictionsConstants.ERROR
import androidx.core.content.UnusedAppRestrictionsConstants.FEATURE_NOT_AVAILABLE
import androidx.core.content.PackageManagerCompat
import androidx.core.content.ContextCompat
import com.google.common.util.concurrent.ListenableFuture


class UnusedAppRestrictionsPlugin : FlutterPlugin, MethodCallHandler {
    private lateinit var channel: MethodChannel
    private lateinit var applicationContext: Context

    override fun onAttachedToEngine(@NonNull binding: FlutterPlugin.FlutterPluginBinding) {
        this.applicationContext = binding.applicationContext
        channel = MethodChannel(binding.binaryMessenger, "unused_app_restrictions")
        channel.setMethodCallHandler(this)
        
    }

    override fun onMethodCall(@NonNull call: MethodCall, @NonNull result: Result) {
        if (call.method == "getUnusedAppRestrictionsStatus") {
            val future: ListenableFuture<Int> = PackageManagerCompat.getUnusedAppRestrictionsStatus(applicationContext)
            future.addListener(
                { 
                    when (future.get()) {
                        ERROR -> {
                            result.error("ERROR", "Unknown error!", null)
                        }
                        FEATURE_NOT_AVAILABLE -> {
                            result.error("UNAVAILABLE", "Unused app restriction status is not available.", null)
                        }
                        DISABLED -> {
                            result.success("DISABLED")
                        }
                        API_30_BACKPORT, API_30, API_31 -> {
                            result.success("ENABLED")
                        }
                    }
                },
                ContextCompat.getMainExecutor(applicationContext)
            )
        } else {
            result.notImplemented()
        }
    }

    override fun onDetachedFromEngine(@NonNull binding: FlutterPlugin.FlutterPluginBinding) {
        channel.setMethodCallHandler(null)
    }
}
