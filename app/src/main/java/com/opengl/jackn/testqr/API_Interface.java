package com.opengl.jackn.testqr;

import android.provider.ContactsContract;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

public interface API_Interface {
        @GET("{number}/{name}/{Time}/{attendance}")
        Call<ContactsContract.Data> API(@Query("number") String number,
                                        @Query("name") String name,
                                        @Query("Time") String Time,
                                        @Query("attendance") String attendance);

}
