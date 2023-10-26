package workshop.akbolatss.dictionartyapp

import android.app.Application
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import workshop.akbolatss.dictionartyapp.di.dataModule
import workshop.akbolatss.dictionartyapp.di.uiModule

class App : Application() {

    override fun onCreate() {
        super.onCreate()
        codeCacheDir.setReadOnly()
        startKoin {
            androidContext(this@App)

            modules(
                dataModule,
                uiModule
            )
        }
    }
}