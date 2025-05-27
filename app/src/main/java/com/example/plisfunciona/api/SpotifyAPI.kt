package com.example.plisfunciona.api

import retrofit2.http.*
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import okhttp3.OkHttpClient
import okhttp3.Credentials
import okhttp3.Interceptor
import okhttp3.Response
import javax.inject.Inject
import javax.inject.Singleton
import com.example.plisfunciona.modelo.*

/** --- MODELOS PARA AUTENTICACIÓN --- **/
data class TokenResponse(
    val access_token: String,
    val token_type: String,
    val expires_in: Int
)

interface SpotifyAuthApi {
    @FormUrlEncoded
    @POST("api/token")
    suspend fun getToken(
        @Field("grant_type") grantType: String = "client_credentials"
    ): TokenResponse
}

/** --- SERVICIO PRINCIPAL DE SPOTIFY --- **/
interface SpotifyService {
    @GET("me/playlists")
    suspend fun getUserPlaylists(): PlaylistResponse

    @GET("search")
    suspend fun search(
        @Query("q") query: String,
        @Query("type") type: String = "track,artist,playlist",
        @Query("limit") limit: Int = 20
    ): SearchResponse

    @GET("artists/{id}")
    suspend fun getArtist(@Path("id") id: String): Artist

    @GET("tracks/{id}")
    suspend fun getTrack(@Path("id") id: String): Track

    @GET("playlists/{id}")
    suspend fun getPlaylist(@Path("id") id: String): Playlist

    @GET("artists/{id}/top-tracks")
    suspend fun getArtistTopTracks(
        @Path("id") id: String,
        @Query("market") market: String = "ES"
    ): TracksResponse

    @GET("browse/featured-playlists")
    suspend fun getRecommendedPlaylists(): PlaylistResponse

    @GET("me/player/recently-played")
    suspend fun getRecentTracks(): TracksResponse
}

/** --- SERVICIO DE AUTENTICACIÓN --- **/
@Singleton
class SpotifyAuthService @Inject constructor() {
    private var cachedToken: String? = null
    private var tokenExpireTime: Long = 0

    private val clientId = "3126ad31559446c088af30b9b4f59801"
    private val clientSecret = "2fbe626dc4fd47a29d36fa220949447f"

    private val retrofit = Retrofit.Builder()
        .baseUrl("https://accounts.spotify.com/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    private val authApi = retrofit.create(SpotifyAuthApi::class.java)

    suspend fun fetchToken(): String {
        val basicAuth = Credentials.basic(clientId, clientSecret)
        val response = authApi.getToken()
        cachedToken = response.access_token
        tokenExpireTime = System.currentTimeMillis() + (response.expires_in * 1000)
        return cachedToken!!
    }

    fun getTokenSync(): String? = cachedToken

    fun isTokenExpired(): Boolean = System.currentTimeMillis() > tokenExpireTime
}

/** --- API PRINCIPAL --- **/
class SpotifyAPI @Inject constructor(
    private val authService: SpotifyAuthService
) {
    private val BASE_URL = "https://api.spotify.com/v1/"

    private val okHttpClient = OkHttpClient.Builder()
        .addInterceptor { chain ->
            val token = authService.getTokenSync()
            val request = chain.request().newBuilder()
                .addHeader("Authorization", "Bearer $token")
                .build()
            chain.proceed(request)
        }
        .build()

    private val spotifyService = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .client(okHttpClient)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
        .create(SpotifyService::class.java)

    // Métodos para acceder a los endpoints
    suspend fun getUserPlaylists() = spotifyService.getUserPlaylists()
    suspend fun search(query: String, type: String = "track,artist,playlist", limit: Int = 20) =
        spotifyService.search(query, type, limit)
    suspend fun getArtist(id: String) = spotifyService.getArtist(id)
    suspend fun getTrack(id: String) = spotifyService.getTrack(id)
    suspend fun getPlaylist(id: String) = spotifyService.getPlaylist(id)
    suspend fun getArtistTopTracks(id: String) = spotifyService.getArtistTopTracks(id)

    suspend fun getFeaturedPlaylists() = spotifyService.getRecommendedPlaylists()
    suspend fun getRecentTracks() = spotifyService.getRecentTracks()
    suspend fun  getRecommendedPlaylists() = spotifyService.getRecommendedPlaylists()
}