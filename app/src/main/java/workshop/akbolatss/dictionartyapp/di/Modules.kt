package workshop.akbolatss.dictionartyapp.di

import android.content.Context
import android.content.SharedPreferences
import androidx.room.Room
import com.squareup.moshi.Moshi
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import workshop.akbolatss.dictionartyapp.data.DictionartyRepository
import workshop.akbolatss.dictionartyapp.data.DictionartyRepositoryImpl
import workshop.akbolatss.dictionartyapp.data.source.*
import workshop.akbolatss.dictionartyapp.ui.DictionartyViewModel


private const val DB_NAME = "database"
private const val PREFERENCES_NAME = "preferences"

val dataModule = module {
    single { getRoom(androidContext()) }
    factory { get<AppDatabase>().wordDao }
    single { getMoshi() }
    single<JsonProvider> { JsonProviderImpl(androidContext()) }
    single<SharedPreferences> {
        getSharedPrefs(androidContext())
    }
    single<SharedPrefsWrapper> { SharedPrefsWrapperImpl(get()) }
    single<DictionartyRepository> {
        DictionartyRepositoryImpl(get(), get(), get(), get())
    }
}

val uiModule = module {
    viewModel {
        DictionartyViewModel(get())
    }
}

private fun getRoom(context: Context) =
    Room.databaseBuilder(
        context,
        AppDatabase::class.java,
        DB_NAME
    )
        .build()

private fun getMoshi(): Moshi = Moshi.Builder().build()

private fun getSharedPrefs(context: Context) =
    context.getSharedPreferences(PREFERENCES_NAME, Context.MODE_PRIVATE)