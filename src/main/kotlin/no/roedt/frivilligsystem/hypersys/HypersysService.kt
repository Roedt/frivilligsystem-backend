package no.roedt.frivilligsystem.hypersys

import no.roedt.frivilligsystem.hypersys.externalModel.Organisasjonsledd
import no.roedt.frivilligsystem.hypersys.externalModel.Organs
import no.roedt.frivilligsystem.hypersys.externalModel.SingleOrgan
import org.eclipse.microprofile.jwt.JsonWebToken
import javax.enterprise.context.ApplicationScoped
import javax.inject.Inject

interface HypersysService {
    fun login(loginRequest: LoginRequest): Token
}

@ApplicationScoped
class HypersysServiceBean(
    val hypersysProxy: HypersysProxy,
    val hypersysSystemTokenVerifier: HypersysSystemTokenVerifier,
    val hypersysLoginBean: HypersysLoginBean
) : HypersysService {

    override fun login(loginRequest: LoginRequest): Token = hypersysLoginBean.login(loginRequest)


    private fun getSystemToken() = hypersysSystemTokenVerifier.assertGyldigSystemToken()
}