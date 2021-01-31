package no.roedt.frivilligsystem.hypersys

import javax.enterprise.context.ApplicationScoped

@ApplicationScoped
class HypersysService(
    val hypersysProxy: HypersysProxy,
    val hypersysSystemTokenVerifier: HypersysSystemTokenVerifier,
    val hypersysLoginBean: HypersysLoginBean
) {

    fun login(loginRequest: LoginRequest): Token = hypersysLoginBean.login(loginRequest)


    private fun getSystemToken() = hypersysSystemTokenVerifier.assertGyldigSystemToken()
}