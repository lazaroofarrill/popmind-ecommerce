package com.espoletatecnologias.common.modules.iam

import com.espoletatecnologias.common.framework.arch.ApplicationModule
import com.espoletatecnologias.common.framework.arch.Controller
import com.espoletatecnologias.common.modules.common.CommonModule
import com.espoletatecnologias.common.modules.iam.controller.AuthController
import com.espoletatecnologias.common.modules.iam.services.AuthService
import com.espoletatecnologias.common.modules.iam.services.KratosClient
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
                authService = get(),
                kratosClient = get()
            )
        }
    }

    override fun controllers(): List<Controller> =
        listOf(get<AuthController>())
}
