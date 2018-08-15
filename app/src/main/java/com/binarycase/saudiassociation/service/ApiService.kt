package com.binarycase.saudiassociation.service

import com.binarycase.saudiassociation.service.request.FavouriteRequest
import com.binarycase.saudiassociation.service.request.StaticContentRequest
import com.binarycase.saudiassociation.service.request.SubCategRequest
import com.binarycase.saudiassociation.service.request.SubCategorySearchRequest
import com.binarycase.saudiassociation.service.response.CategSubListResponse
import com.binarycase.saudiassociation.service.response.FavouriteResponse
import com.binarycase.saudiassociation.service.response.MainSectionsResponse
import com.binarycase.saudiassociation.service.response.StaticContentResponse
import retrofit2.http.GET
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {

  @GET("list/cats")
  fun getMainSections(
    @Header("Authorization") Header: String, @Query(
        "device_id"
    ) deviceId: String
  ): Call<List<MainSectionsResponse>>

  @POST("{word_list}")
  fun getSubCategList(
    @Path(value = "word_list",encoded = true)listType:String,
    @Header("Content-Type") contenType: String,
    @Header("Authorization") Header: String,
    @Body subcat: SubCategRequest
  ): Call<CategSubListResponse>

  @POST("list/search")
  fun searchInSubCategories(
    @Header("Content-Type") contenType: String,
    @Header("Authorization") Header: String,
    @Body searchSubCat: SubCategorySearchRequest
  ): Call<CategSubListResponse>

  @POST("favorites/{fav_action}")
  fun toggleFavourite(
    @Header("Authorization") Header: String,
    @Path("fav_action") favAction: String,
    @Header(
        "Content-Type"
    ) contentType: String, @Body favouriteRequest: FavouriteRequest
  ): Call<FavouriteResponse>


  @POST("load/content")
fun getStaticContent(
    @Header("Authorization") appAuth:String,
      @Header("Content-Type") contentType:String,
      @Body request :  StaticContentRequest  ) : Call<StaticContentResponse>

}