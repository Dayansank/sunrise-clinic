package com.sunrise.clinic.service;

import com.sunrise.clinic.dao.ClinicSettingsDAO;
import com.sunrise.clinic.dao.DentistDAO;
import com.sunrise.clinic.dao.TreatmentDAO;
import com.sunrise.clinic.exception.ClinicException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class CatalogServiceTest {

    @Mock
    private DentistDAO dentistDAO;
    @Mock
    private TreatmentDAO treatmentDAO;
    @Mock
    private ClinicSettingsDAO settingsDAO;

    @Test
    void addsDentistToDatabase() {
        new CatalogService(dentistDAO, treatmentDAO, settingsDAO)
                .addDentist("Dr. Ruwan Perera", "Periodontics");
        verify(dentistDAO).insert("Dr. Ruwan Perera", "Periodontics");
    }

    @Test
    void refusesDeleteWhenDentistHasAppointments() {
        when(dentistDAO.countAppointments(3)).thenReturn(2);
        CatalogService service = new CatalogService(dentistDAO, treatmentDAO, settingsDAO);
        assertThrows(ClinicException.class, () -> service.deleteDentist(3));
        verify(dentistDAO, never()).deleteById(3);
    }

    @Test
    void savesConsultationFee() {
        new CatalogService(dentistDAO, treatmentDAO, settingsDAO).saveConsultationFee("2000.00");
        verify(settingsDAO).saveConsultationFee(new BigDecimal("2000.00"));
    }
}
