package com.sunrise.clinic.service;

import com.sunrise.clinic.dao.AppointmentDAO;
import com.sunrise.clinic.dao.DentistDAO;
import com.sunrise.clinic.dao.PatientDAO;
import com.sunrise.clinic.dao.TreatmentDAO;
import com.sunrise.clinic.exception.ClinicException;
import com.sunrise.clinic.model.Dentist;
import com.sunrise.clinic.model.Treatment;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AppointmentServiceTest {

    @Mock private AppointmentDAO appointmentDAO;
    @Mock private PatientDAO patientDAO;
    @Mock private DentistDAO dentistDAO;
    @Mock private TreatmentDAO treatmentDAO;

    @Test
    void rejectsDoubleBooking() {
        Dentist dentist = new Dentist();
        dentist.setDentistId(1);
        dentist.setName("Dr. Nimal Perera");
        Treatment treatment = new Treatment();
        treatment.setTreatmentId(2);
        treatment.setTypeName("Filling");
        treatment.setCost(new BigDecimal("8000.00"));

        when(dentistDAO.findById(1)).thenReturn(dentist);
        when(treatmentDAO.findById(2)).thenReturn(treatment);
        when(appointmentDAO.isSlotTaken(anyInt(), any(), any())).thenReturn(true);

        AppointmentService service = new AppointmentService(appointmentDAO, patientDAO, dentistDAO, treatmentDAO);
        LocalDate date = LocalDate.now().plusDays(1);
        if (date.getDayOfWeek().getValue() == 7) {
            date = date.plusDays(1);
        }
        LocalDate finalDate = date;
        assertThrows(ClinicException.class, () -> service.register(
                "Kamal Perera",
                "12 Galle Road, Colombo",
                "0771234567",
                "1",
                "2",
                finalDate.toString(),
                "10:30",
                1
        ));
    }
}
