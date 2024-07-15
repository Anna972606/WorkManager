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
import androidx.work.PeriodicWorkRequest
import androidx.work.WorkInfo
import androidx.work.WorkManager
import com.example.workmanager.ui.workmanager.ReminderWorker
import java.util.concurrent.TimeUnit

const val PERIODIC_WORK_REQUEST_TAG = "PeriodicWorkRequestScreen"

@Composable
fun PeriodicWorkRequestScreen() {
    val context = LocalContext.current
    val lifecycleOwner = LocalLifecycleOwner.current
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = dimensionResource(id = R.dimen.dimen_size_16dp)),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(text = stringResource(id = R.string.note_periodic_work_request))
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
    val data = Data.Builder()
        .putString(ReminderWorker.KEY_TITLE, context.getString(R.string.test_periodic_work_request))
        .build()

    // Create WorkRequest specifying the worker class and repeat interval
    val workRequest = PeriodicWorkRequest.Builder(
        ReminderWorker::class.java,
        TimeUnit.SECONDS.toMillis(10),  // This will repeat every 10 seconds (in milliseconds)
        TimeUnit.MILLISECONDS
    ).setInputData(data).build()

    // Enqueue the work request using WorkManager
    WorkManager.getInstance(context).apply {
        enqueue(workRequest)
        getWorkInfoByIdLiveData(workRequest.id).observe(lifecycleOwner) { workInfo ->
            if (workInfo?.state == WorkInfo.State.SUCCEEDED) {
                Log.d(PERIODIC_WORK_REQUEST_TAG, WorkInfo.State.SUCCEEDED.toString())
            }
        }
    }
}







