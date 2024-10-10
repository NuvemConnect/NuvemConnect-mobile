package com.nuvemconnect.app.nuvemconnect.data.network

import com.nuvemconnect.app.nuvemconnect.model.service.AccountRequest
import com.nuvemconnect.app.nuvemconnect.model.service.AccountResponse
import com.nuvemconnect.app.nuvemconnect.model.service.LoginRequest
import com.nuvemconnect.app.nuvemconnect.model.service.LoginResponse
import com.nuvemconnect.app.nuvemconnect.model.service.ResetPasswordRequest
import com.nuvemconnect.app.nuvemconnect.model.service.ResetPasswordResponse
import com.nuvemconnect.app.nuvemconnect.model.service.SendResetPasswordRequest
import com.nuvemconnect.app.nuvemconnect.model.service.SendResetPasswordResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST
import retrofit2.http.PUT

interface AuthService {
    @POST("account")
    suspend fun createAccount(
        @Body request: AccountRequest,
    ): Response<AccountResponse>

    @POST("account/login")
    suspend fun login(
        @Body request: LoginRequest,
    ): Response<LoginResponse>

    @POST("account/request-password-reset")
    suspend fun resetPasswordRequest(
        @Body request: ResetPasswordRequest,
    ): Response<ResetPasswordResponse>

    @PUT("account/reset-password")
    suspend fun sendResetPassword(
        @Body request: SendResetPasswordRequest,
    ): Response<SendResetPasswordResponse>
}
