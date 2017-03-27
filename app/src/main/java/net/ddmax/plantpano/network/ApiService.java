package net.ddmax.plantpano.network;

import net.ddmax.plantpano.entity.Image;
import net.ddmax.plantpano.entity.ImageList;

import java.util.List;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

/**
 * @author ddMax
 * @since 2017-03-06 07:30 PM.
 * 说明：API服务接口类
 */

public interface ApiService {

    /**
     * Fetch all public images
     */
    @GET("images?where={\"is_pub\": true}")
    Call<ImageList> getAllPubImage();

    /**
     * Image upload
     */
    @Multipart
    @POST("images")
    Call<Image> upload(
        @Part("name") RequestBody imageName,
        @Part("is_pub") RequestBody isPub,
        @Part MultipartBody.Part file
    );
}
