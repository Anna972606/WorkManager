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
import androidx.work.ExistingWorkPolicy
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkInfo
import androidx.work.WorkManager
import com.example.workmanager.ui.workmanager.ReminderWorker
import java.util.concurrent.TimeUnit

const val ONE_TIME_WORK_SCREEN_TAG = "OneTimeWorkScreen"
private const val DEFAULT_DELAY = 3000L

@Composable
fun OneTimeWorkScreen() {
    val context = LocalContext.current
    val lifecycleOwner = LocalLifecycleOwner.current
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = dimensionResource(id = R.dimen.dimen_size_16dp)),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(text = stringResource(id = R.string.note_wait_three_seconds))
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
    // create input data for worker class
    val data = Data.Builder()
        .putString(
            ReminderWorker.KEY_TITLE,
            context.getString(R.string.test_one_time_work_request_builder)
        )
        .build()

    // Create WorkRequest specifying the worker class and Initial Delay
    val request = OneTimeWorkRequestBuilder<ReminderWorker>()
        .setInitialDelay(DEFAULT_DELAY, TimeUnit.MILLISECONDS)
        .setInputData(data)
        .build()

    // Enqueue the work request using WorkManager
    WorkManager.getInstance(context).apply {
        // Only one instance of a background task with a specific name can be running at a time.
        enqueueUniqueWork(ONE_TIME_WORK_SCREEN_TAG, ExistingWorkPolicy.REPLACE, request)
        getWorkInfoByIdLiveData(request.id).observe(lifecycleOwner) { workInfo ->
            if (workInfo?.state == WorkInfo.State.SUCCEEDED) {
                Log.d(ONE_TIME_WORK_SCREEN_TAG, WorkInfo.State.SUCCEEDED.toString())
            }
        }
    }
}