package ru.kode.tools.opengate.features.gates.data.mappers

import ru.kode.tools.opengate.foundation.core.Mapper
import ru.kode.tools.opengate.foundation.data.GatesListResponse
import ru.kode.tools.opengate.features.gates.domain.Gate

internal class GatesResponseMapper : Mapper<GatesListResponse.Success, Gate> {
    override fun map(item: GatesListResponse.Success): Gate {
        return Gate(
            id = item.id,
            key = item.key,
            name = item.name,
            isAvailable = item.state == 1
        )
    }
}