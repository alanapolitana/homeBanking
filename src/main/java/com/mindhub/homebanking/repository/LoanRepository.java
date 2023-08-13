package com.mindhub.homebanking.repository;

import com.mindhub.homebanking.models.Loan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.stereotype.Repository;

@RepositoryRestResource
public interface LoanRepository extends JpaRepository<Loan, Long> {
}

/*

    Parece que estás mostrando una respuesta en formato JSON que proviene de una solicitud a la URL http://localhost:8080/rest/loans. Esta respuesta indica que los préstamos se han creado correctamente en tu base de datos y están siendo devueltos correctamente por el punto de acceso REST.

        El fragmento que proporcionaste es una representación JSON de los préstamos creados y almacenados en la base de datos. Cada préstamo tiene un nombre, un monto máximo y una lista de pagos asociada. Además, se incluyen enlaces que permiten acceder a los detalles de cada préstamo.

        En resumen, parece que has logrado crear y almacenar los préstamos en la base de datos y luego recuperarlos a través de la API REST que has configurado.*/
