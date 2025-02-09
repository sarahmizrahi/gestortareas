package com.example.proyecto_smr.hilt

/*
@Module //indica que esta clase es un módulo de Hilt
@InstallIn(SingletonComponent::class)   //hace que las dependencias duren toda la vida de la app
object NetworkModule {

    @Provides
    @Singleton
    fun provideRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl("http://10.0.2.2:8080") // para acceder al backend en Spring Boot desde aqui
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Provides
    @Singleton
    fun provideTareaApi(retrofit: Retrofit): TareaApi {
        return retrofit.create(TareaApi::class.java)
    }
}
*/