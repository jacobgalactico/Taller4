package com.example.taller4

import android.app.PendingIntent
import android.appwidget.AppWidgetManager
import android.appwidget.AppWidgetProvider
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.widget.RemoteViews
import java.text.SimpleDateFormat
import java.util.*

class MyAppWidgetProvider : AppWidgetProvider() {

    override fun onUpdate(
        context: Context,
        appWidgetManager: AppWidgetManager,
        appWidgetIds: IntArray
    ) {
        // Actualizar cada widget
        for (appWidgetId in appWidgetIds) {
            updateAppWidget(context, appWidgetManager, appWidgetId)
        }
    }

    companion object {
        private const val PREFS_NAME = "com.example.taller4.widget_prefs"
        private const val PREF_COUNT_KEY = "item_count"

        /**
         * Guarda el número de elementos en las SharedPreferences.
         */
        fun saveItemCount(context: Context, count: Int) {
            val prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
            prefs.edit().putInt(PREF_COUNT_KEY, count).apply()
        }

        /**
         * Obtiene el número de elementos desde las SharedPreferences.
         */
        fun getItemCount(context: Context): Int {
            val prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
            return prefs.getInt(PREF_COUNT_KEY, 0)
        }

        /**
         * Actualiza la vista del widget.
         */
        fun updateAppWidgetViews(context: Context): RemoteViews {
            val currentDate = SimpleDateFormat("HH:mm:ss", Locale.getDefault()).format(Date())
            val itemCount = getItemCount(context)

            val views = RemoteViews(context.packageName, R.layout.widget_layout)
            views.setTextViewText(
                R.id.widget_text,
                "Última actualización: $currentDate\nTotal de elementos: $itemCount"
            )
            return views
        }

        /**
         * Actualiza un widget específico.
         */
        private fun updateAppWidget(
            context: Context,
            appWidgetManager: AppWidgetManager,
            appWidgetId: Int
        ) {
            val views = updateAppWidgetViews(context)

            val intent = Intent(context, MyAppWidgetProvider::class.java)
            intent.action = AppWidgetManager.ACTION_APPWIDGET_UPDATE
            intent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_IDS, intArrayOf(appWidgetId))

            val pendingIntent = PendingIntent.getBroadcast(
                context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
            )
            views.setOnClickPendingIntent(R.id.widget_button, pendingIntent)

            appWidgetManager.updateAppWidget(appWidgetId, views)
        }

        /**
         * Notifica a todos los widgets para que se actualicen.
         */
        fun notifyWidgetUpdate(context: Context) {
            val appWidgetManager = AppWidgetManager.getInstance(context)
            val widgetIds = appWidgetManager.getAppWidgetIds(
                ComponentName(context, MyAppWidgetProvider::class.java)
            )
            for (widgetId in widgetIds) {
                updateAppWidget(context, appWidgetManager, widgetId)
            }
        }
    }
}
