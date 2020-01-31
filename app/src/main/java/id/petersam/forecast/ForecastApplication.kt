package id.petersam.forecast

import android.app.Application
import id.petersam.forecast.data.WeatherApiService
import id.petersam.forecast.data.db.ForecastDatabase
import id.petersam.forecast.data.network.ConnectivityInterceptor
import id.petersam.forecast.data.network.ConnectivityInterceptorImpl
import id.petersam.forecast.data.network.WeatherNetworkDataSource
import id.petersam.forecast.data.network.WeatherNetworkDataSourceImpl
import id.petersam.forecast.data.repository.ForecastRepository
import id.petersam.forecast.data.repository.ForecastRepositoryImpl
import org.kodein.di.Kodein
import org.kodein.di.KodeinAware
import org.kodein.di.android.x.androidXModule
import org.kodein.di.generic.bind
import org.kodein.di.generic.instance
import org.kodein.di.generic.singleton

class ForecastApplication : Application(), KodeinAware {
    override val kodein = Kodein.lazy {
        import(androidXModule(this@ForecastApplication))

        bind() from singleton { ForecastDatabase(instance()) }
        bind() from singleton { instance<ForecastDatabase>().currentWeatherDao() }
        bind<ConnectivityInterceptor>() with singleton { ConnectivityInterceptorImpl(instance()) }
        bind() from singleton { WeatherApiService(instance()) }
        bind<WeatherNetworkDataSource>() with singleton { WeatherNetworkDataSourceImpl(instance()) }
        bind<ForecastRepository>() with singleton { ForecastRepositoryImpl(instance(), instance()) }
    }
}