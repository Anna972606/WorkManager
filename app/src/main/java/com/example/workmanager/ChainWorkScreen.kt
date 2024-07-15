package com.example.workmanager

import android.content.Context
import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.LifecycleOwner
import androidx.work.Data
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkInfo
import androidx.work.WorkManager
import com.example.workmanager.ui.workmanager.ReminderWorker
import java.util.concurrent.TimeUnit


const val CHAIN_WORK_SCREEN_TAG = "ChainWorkScreen"
private const val DEFAULT_DELAY = 3000L

@Composable
fun ChainWorkScreen() {
    val context = LocalContext.current
    val lifecycleOwner = LocalLifecycleOwner.current
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = dimensionResource(id = R.dimen.dimen_size_16dp)),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(text = stringResource(id = R.string.note_chain_work_screen))
        Button(
            onClick = { scheduleReminder(context, lifecycleOwner) }
        ) {
            Text(
                text = stringResource(id = R.string.start)
            )
        }
    }
}

private fun scheduleReminder(context: Context, lifecycleOwner: LifecycleOwner) {
    val data1 = Data.Builder()
        .putString(ReminderWorker.KEY_TITLE, context.getString(R.string.test_chain_work_screen1))
        .build()
    val data2 = Data.Builder()
        .putString(ReminderWorker.KEY_TITLE, context.getString(R.string.test_chain_work_screen2))
        .build()

    // Create WorkRequest specifying the worker class and Initial Delay
    val request1 = OneTimeWorkRequestBuilder<ReminderWorker>()
        .setInitialDelay(DEFAULT_DELAY, TimeUnit.MILLISECONDS)
        .setInputData(data1)
        .build()
    val request2 = OneTimeWorkRequestBuilder<ReminderWorker>()
        .setInitialDelay(DEFAULT_DELAY, TimeUnit.MILLISECONDS)
        .setInputData(data2)
        .build()

    // Enqueue the work request using WorkManager
    WorkManager.getInstance(context).apply {
        beginWith(request1).then(request2).enqueue()
        listOf(request1, request2).forEach {
            getWorkInfoByIdLiveData(it.id).observe(lifecycleOwner) { workInfo ->
                if (workInfo?.state == WorkInfo.State.SUCCEEDED) {
                    Log.d(ONE_TIME_WORK_SCREEN_TAG, WorkInfo.State.SUCCEEDED.toString())
                }
            }
        }
    }
}
