package com.example.proyecto_smr

import android.util.Log
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


object RetrofitInstance {
    // URL base del servidor al que se van a hacer las solicitudes API. En este caso, se usa un servidor local en un emulador de Android.
    private const val BASE_URL = "http://10.0.2.2:8080/"

    // Creación de un cliente OkHttp personalizado que incluye un interceptor para registrar las solicitudes y respuestas HTTP.
    private val client: OkHttpClient by lazy {
        // Se configura un interceptor de logging para registrar los mensajes HTTP.
        val logging = HttpLoggingInterceptor { message ->
            // El interceptor captura y registra los mensajes HTTP en los logs de Android
            Log.d("HTTP", message)
        }

        // Se establece el nivel de logging para registrar el cuerpo completo de las solicitudes y respuestas
        logging.setLevel(HttpLoggingInterceptor.Level.BODY)

        // Construcción del cliente OkHttp con el interceptor de logging
        OkHttpClient.Builder()
            .addInterceptor(logging) // Se agrega el interceptor de logging al cliente
            .build() // Se construye el cliente OkHttp
    }

    // Creación perezosa (lazy) del servicio API que usará Retrofit para interactuar con el servidor.
    val apiService: TareaApi by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL) // Se define la URL base para todas las solicitudes a la API
            .addConverterFactory(GsonConverterFactory.create()) // Se agrega un convertidor para convertir los objetos JSON en objetos Kotlin (usando Gson)
            .client(client) // Se configura Retrofit para usar el cliente OkHttp personalizado creado anteriormente
            .build() // Se construye la instancia de Retrofit
            .create(TareaApi::class.java) // Se crea la instancia de la interfaz `TareaApi`, que contiene los métodos de la API
    }
}

/*object RetrofitInstance {
    // Se crea una instancia de Retrofit perezosa (lazy) para no inicializarla hasta que se necesite
    private val retrofit by lazy {
        Retrofit.Builder()
            .baseUrl("http://10.0.2.2:8080") // URL base para las solicitudes API (servidor local)
            .addConverterFactory(GsonConverterFactory.create()) // Conversor para convertir las respuestas JSON en objetos Kotlin
            .build() // Construye la instancia de Retrofit
    }

    // Instancia perezosa de la interfaz API (TareaApi), donde se definen las solicitudes HTTP
    val api: TareaApi by lazy {
        retrofit.create(TareaApi::class.java)
    }
}
*/