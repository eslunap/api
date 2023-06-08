package med.voll.api.medico;

import med.voll.api.direccion.DatosDireccion;

public record DatosRespuestaMedico(
        Long id,
        String nombre,
        String telefono,
        String email,
        String especialidad,
        String documento,
        DatosDireccion direccion
) {
}
