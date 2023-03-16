package tech.streamlinehealth.esims.helpers;

import android.content.Context;
import android.content.Intent;
import android.os.Build;

import androidx.annotation.NonNull;
import androidx.work.Worker;
import androidx.work.WorkerParameters;

public class FetchPatientActionsWorker extends Worker {
    private final Context context;

    public FetchPatientActionsWorker(@NonNull Context context, @NonNull WorkerParameters workerParams) {
        super(context, workerParams);
        this.context = context;
    }

    @NonNull
    @Override
    public Result doWork() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            context.startForegroundService(new Intent(context, FetchPatientActionsService.class));
        } else {
            context.startService(new Intent(context, FetchPatientActionsService.class));
        }
        return Result.success();
    }
}
