package com.example.vacationplannertool;

import org.junit.Test;
import org.junit.runner.RunWith;


import static org.junit.Assert.*;



import com.example.vacationplannertool.entity.Excursion;
import com.example.vacationplannertool.entity.Vacation;


/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
    @Test
    public void validateVacation() {
        Vacation v = new Vacation();
        v.setVacName("Dummy Vacation");
        v.setVacHotel("Dummy Hotel");
        v.setVacStartDate("2026/05/19");
        v.setVacEndDate("2026/05/21");
        assertEquals("Dummy Vacation", v.getVacName());
        assertEquals("Dummy Hotel", v.getVacHotel());
        assertEquals("2026/05/19", v.getVacStartDate());
        assertEquals("2026/05/21", v.getVacEndDate());
    }

    @Test
    public void validateExcursion() {
        Excursion e = new Excursion();
        e.setExcName("Dummy Excursion");
        e.setExcDate("2026/05/19");
        assertEquals("Dummy Excursion", e.getExcName());
        assertEquals("2026/05/19", e.getExcDate());
    }

}

