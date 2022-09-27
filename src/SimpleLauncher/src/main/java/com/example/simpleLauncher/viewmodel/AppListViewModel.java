package com.example.simpleLauncher.viewmodel;

import android.app.Activity;
import android.app.WallpaperManager;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.widget.Toast;

import androidx.activity.result.ActivityResult;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.simpleLauncher.model.AppListProvider;
import com.example.simpleLauncher.model.AppModel;
import com.example.simpleLauncher.model.MainProvider;
import com.example.simpleLauncher.util.OrderTypeAppModel;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

public class AppListViewModel extends ViewModel {

    // ATTRIBUTES

    private MutableLiveData<List<AppModel>> appListLiveData;

    // CONSTRUCTOR

    public AppListViewModel() {
        appListLiveData = new MutableLiveData<>();
    }

    // METHODS

    public LiveData<List<AppModel>> getAppList() {
        return appListLiveData;
    }

    public void updateAppList(OrderTypeAppModel orderType) {
        appListLiveData.setValue(AppListProvider.requestApps(orderType));
    }

    public void updateShortcutAppList(OrderTypeAppModel orderType) {
        if(orderType == OrderTypeAppModel.ORDER_BY_SHORTCUT_AND_NAME) {
            appListLiveData.setValue(MainProvider.requestShortcutApps());
        }
        else {
            appListLiveData.setValue(AppListProvider.requestApps(orderType));
        }
    }

    public void launchApp(AppModel app, Activity activity) {
        try {
            Intent launchAppIntent = activity.getApplicationContext().getPackageManager().getLaunchIntentForPackage(app.getPackageName());
            if (launchAppIntent != null)
                activity.getApplicationContext().startActivity(launchAppIntent);
        } catch(Exception e) {
            System.out.println(app);
            e.printStackTrace();
        }
    }

    public void clickShortcut(AppModel app) {
        if(app.getPosition() != - 1) AppListProvider.removeShortcut(app);
        else AppListProvider.addShortcut(app);
    }

    public void swapAppsButton(AppModel app1, AppModel app2) {
        if(app1 != null && app2 != null) {
            AppListProvider.swapApps(app1, app2);
        }
    }

    public void setWallpaper(Activity activity, ActivityResult result) {
        Intent data = result.getData();
        if(data != null) {
            Uri imageUri = data.getData();

            if(imageUri != null) {
                try {
                    InputStream inputStream = activity.getContentResolver().openInputStream(imageUri);
                    Bitmap imageBitmap = BitmapFactory.decodeStream(inputStream);

                    WallpaperManager manager = WallpaperManager.getInstance(activity.getApplicationContext());
                    try{
                        manager.setBitmap(imageBitmap);
                        Toast.makeText(activity, "Fondo de pantalla actualizado", Toast.LENGTH_SHORT).show();
                    } catch (IOException e) {
                        Toast.makeText(activity, "Error al cambiar el fondo de pantalla", Toast.LENGTH_SHORT).show();
                    }

                } catch (Exception e){
                    Toast.makeText(activity, "Se ha producido un error al cargar la foto", Toast.LENGTH_SHORT).show();
                }
            }
        }
    }

}
