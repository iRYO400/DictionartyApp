package workshop.akbolatss.dictionartyapp.di

import android.content.Context
import androidx.room.Room
import com.squareup.moshi.Moshi
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import workshop.akbolatss.dictionartyapp.data.DictionartyRepositoryImpl
import workshop.akbolatss.dictionartyapp.data.source.AppDatabase
import workshop.akbolatss.dictionartyapp.data.source.JsonParser
import workshop.akbolatss.dictionartyapp.data.source.JsonParserImpl
import workshop.akbolatss.dictionartyapp.domain.DictionartyRepository
import workshop.akbolatss.dictionartyapp.domain.InstantiateDatabaseUseCase
import workshop.akbolatss.dictionartyapp.domain.InstantiateDatabaseUseCaseImpl
import workshop.akbolatss.dictionartyapp.domain.QueryWordUseCase
import workshop.akbolatss.dictionartyapp.domain.QueryWordUseCaseImpl
import workshop.akbolatss.dictionartyapp.ui.DictionartyViewModel


private const val DB_NAME = "database"

val dataModule = module {
    single { getRoom(androidContext()) }
    factory { get<AppDatabase>().wordDao }
    single { getMoshi() }
    single<JsonParser> { JsonParserImpl(androidContext(), get()) }
    single<DictionartyRepository> {
        DictionartyRepositoryImpl(get())
    }
    factory<InstantiateDatabaseUseCase> { InstantiateDatabaseUseCaseImpl(get(), get()) }
    factory<QueryWordUseCase> { QueryWordUseCaseImpl(get()) }
}

val uiModule = module {
    viewModel {
        DictionartyViewModel(get(), get())
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