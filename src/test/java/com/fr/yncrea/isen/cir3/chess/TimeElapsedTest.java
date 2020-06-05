package com.fr.yncrea.isen.cir3.chess;

import com.fr.yncrea.isen.cir3.chess.services.ChessGameService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PowerMockIgnore;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import static org.powermock.api.mockito.PowerMockito.when;
import static org.assertj.core.api.Assertions.assertThat;

@RunWith(PowerMockRunner.class)
@PrepareForTest(ChessGameService.class)
@PowerMockIgnore({"com.sun.org.apache.xerces.*", "javax.xml.*", "org.xml.*", "javax.management.*"})
public class TimeElapsedTest {
    @Test
    public void getTimeElapsedTest() {
        ChessGameService service = new ChessGameService();

        PowerMockito.mockStatic(System.class);
        when(System.currentTimeMillis()).thenReturn(1000000L);

        assertThat(service.getTimeElapsed(900000L)).isEqualTo(100);
        assertThat(service.getTimeElapsed(null)).isEqualTo(0);
    }
}
