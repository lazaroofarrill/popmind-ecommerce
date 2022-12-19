package com.espoletatecnologias.api.modules.iam

import com.espoletatecnologias.api.framework.arch.ApplicationModule
import com.espoletatecnologias.api.framework.arch.Controller
import com.espoletatecnologias.api.modules.common.CommonModule
import com.espoletatecnologias.api.modules.iam.controller.AuthController
import com.espoletatecnologias.api.modules.iam.services.IAMService
import com.espoletatecnologias.api.modules.iam.services.KratosClient
import com.espoletatecnologias.api.modules.iam.services.RegistrationService
import org.koin.core.component.get
import org.koin.dsl.module

class IAMModule : ApplicationModule() {
    override fun imports() = listOf(
        CommonModule::class
    )

    override val module = module {
        single { IAMService() }
        single { RegistrationService(kratosClient = get()) }
        single { KratosClient(httpClient = get()) }
        single {
            AuthController(
                iamService = get(),
                registrationService = get()
            )
        }
    }

    override fun controllers(): List<Controller> =
        listOf(get<AuthController>())
}
