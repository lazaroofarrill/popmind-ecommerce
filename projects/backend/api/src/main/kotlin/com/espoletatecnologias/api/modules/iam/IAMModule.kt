package com.espoletatecnologias.api.modules.iam

import com.espoletatecnologias.api.framework.arch.ApplicationModule
import com.espoletatecnologias.api.framework.arch.Controller
import com.espoletatecnologias.api.modules.common.CommonModule
import com.espoletatecnologias.api.modules.iam.controller.AuthController
import com.espoletatecnologias.api.modules.iam.services.KratosClient
import com.espoletatecnologias.api.modules.iam.services.AuthService
import org.koin.core.component.get
import org.koin.dsl.module

class IAMModule : ApplicationModule() {
    override fun imports() = listOf(
        CommonModule::class
    )

    override val module = module {
        single { AuthService(kratosClient = get()) }
        single { KratosClient(httpClient = get()) }
        single {
            AuthController(
                authService = get()
            )
        }
    }

    override fun controllers(): List<Controller> =
        listOf(get<AuthController>())
}
