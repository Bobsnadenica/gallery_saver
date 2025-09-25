package carnegietechnologies.gallery_saver

import android.content.Context
import androidx.annotation.NonNull
import io.flutter.embedding.engine.plugins.FlutterPlugin
import io.flutter.plugin.common.MethodCall
import io.flutter.plugin.common.MethodChannel
import io.flutter.plugin.common.MethodChannel.MethodCallHandler
import io.flutter.plugin.common.MethodChannel.Result

class GallerySaverPlugin : FlutterPlugin, MethodCallHandler {

    private lateinit var channel: MethodChannel
    private var context: Context? = null
    private var gallerySaver: GallerySaver? = null

    override fun onAttachedToEngine(binding: FlutterPlugin.FlutterPluginBinding) {
        context = binding.applicationContext
        channel = MethodChannel(binding.binaryMessenger, "gallery_saver")
        channel.setMethodCallHandler(this)
        gallerySaver = GallerySaver(context!!)
    }

    override fun onMethodCall(call: MethodCall, result: Result) {
        when (call.method) {
            "saveImage" -> gallerySaver?.checkPermissionAndSaveFile(call, result, MediaType.image)
            "saveVideo" -> gallerySaver?.checkPermissionAndSaveFile(call, result, MediaType.video)
            else -> result.notImplemented()
        }
    }

    override fun onDetachedFromEngine(@NonNull binding: FlutterPlugin.FlutterPluginBinding) {
        channel.setMethodCallHandler(null)
    }
}
