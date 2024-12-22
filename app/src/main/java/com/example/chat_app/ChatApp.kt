package com.example.chat_app

import android.app.Application
import android.util.Log
import com.example.common.Core
import com.example.common.CoreProvider
import com.example.utils.helper.Constants
import com.google.firebase.auth.FirebaseAuth
import com.zegocloud.uikit.internal.ZegoUIKitLanguage
import com.zegocloud.uikit.prebuilt.call.ZegoUIKitPrebuiltCallService
import com.zegocloud.uikit.prebuilt.call.core.invite.ZegoCallInvitationData
import com.zegocloud.uikit.prebuilt.call.event.CallEndListener
import com.zegocloud.uikit.prebuilt.call.event.ErrorEventsListener
import com.zegocloud.uikit.prebuilt.call.event.SignalPluginConnectListener
import com.zegocloud.uikit.prebuilt.call.event.ZegoCallEndReason
import com.zegocloud.uikit.prebuilt.call.invite.ZegoUIKitPrebuiltCallInvitationConfig
import com.zegocloud.uikit.prebuilt.call.invite.internal.ZegoTranslationText
import com.zegocloud.uikit.prebuilt.call.invite.internal.ZegoUIKitPrebuiltCallConfigProvider
import dagger.hilt.android.HiltAndroidApp
import im.zego.zim.enums.ZIMConnectionEvent
import im.zego.zim.enums.ZIMConnectionState
import org.json.JSONObject
import timber.log.Timber
import javax.inject.Inject

@HiltAndroidApp
class ChatApp : Application() {

    @Inject
    lateinit var coreProvider: CoreProvider

    @Inject
    lateinit var firebaseAuth: FirebaseAuth

    override fun onCreate() {
        super.onCreate()
        Core.init(coreProvider)
        firebaseAuth.currentUser?.let {
            initZegoService(
                application = this,
                appID = Constants.APP_ID,
                appSign = Constants.APP_SIGN,
                userID = it.email!!,
                userName = it.email!!
            )
        }
    }

}

private fun initZegoService(application: Application, appID: Long, appSign: String, userID: String, userName: String) {
    // Initialize Zego service
    val callInvitationConfig = ZegoUIKitPrebuiltCallInvitationConfig()
    callInvitationConfig.translationText = ZegoTranslationText(ZegoUIKitLanguage.ENGLISH)
    callInvitationConfig.provider =
        ZegoUIKitPrebuiltCallConfigProvider { invitationData: ZegoCallInvitationData? ->
            ZegoUIKitPrebuiltCallInvitationConfig.generateDefaultConfig(
                invitationData
            )
        }
    ZegoUIKitPrebuiltCallService.events.errorEventsListener =
        ErrorEventsListener { errorCode: Int, message: String ->
            Timber.d("onError() called with: errorCode = [$errorCode], message = [$message]")
        }
    ZegoUIKitPrebuiltCallService.events.invitationEvents.pluginConnectListener =
        SignalPluginConnectListener { state: ZIMConnectionState, event: ZIMConnectionEvent, extendedData: JSONObject ->
            Timber.d("onSignalPluginConnectionStateChanged() called with: state = [$state], event = [$event], extendedData = [$extendedData$]")
        }
    ZegoUIKitPrebuiltCallService.init(
        application, appID, appSign, userID, userName, callInvitationConfig
    )
    ZegoUIKitPrebuiltCallService.enableFCMPush()

    ZegoUIKitPrebuiltCallService.events.callEvents.callEndListener =
        CallEndListener { callEndReason: ZegoCallEndReason?, jsonObject: String? ->
            Log.d(
                "CallEndListener",
                "Call Ended with reason: $callEndReason and json: $jsonObject"
            )
        }
}