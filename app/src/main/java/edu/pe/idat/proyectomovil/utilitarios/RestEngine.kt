package edu.pe.idat.proyectomovil.utilitarios

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RestEngine {

    companion object{
        fun getRestEngine(): Retrofit{
            val interceptor = HttpLoggingInterceptor()
            interceptor.setLevel(HttpLoggingInterceptor.Level.BODY)

            val retrofit = Retrofit.Builder()
                .baseUrl("http://10.0.2.2:8087/proyrestaurante/")
                .addConverterFactory(GsonConverterFactory.create())
                .client(getClient())
                .build()

            return retrofit
        }

        private fun getClient(): OkHttpClient {
            return OkHttpClient.Builder()
                .addInterceptor(HeaderInterceptor())
                .build()
        }
    }
}