package ru.kode.tools.opengate.modules.auth.data.mappers

import ru.kode.tools.opengate.core.Mapper
import ru.kode.tools.opengate.data.SignInResponse
import ru.kode.tools.opengate.modules.auth.domain.Barrier

internal class SignInResponseMapper : Mapper<SignInResponse.Success, Barrier> {
    override fun map(item: SignInResponse.Success): Barrier {
        return Barrier(
            id = item.id,
            key = item.key,
            name = item.name,
            isAvailable = item.state == 1
        )
    }
}