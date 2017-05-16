package br.ufpr.inf.gres.sentinel.main.cli.converter;

import java.io.File;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Created by giovani on 21/02/17.
 */
public class SeparatorConverterTest {

    @Test
    public void convert() throws Exception {
        SeparatorConverter converter = new SeparatorConverter();
        assertEquals("test" + File.separator + "test" + File.separator + "test", converter.convert("test/test\\test"));
        assertEquals("test" + File.separator + "test" + File.separator + "test", converter.convert("test\\test\\test"));
        assertEquals("test" + File.separator + "test" + File.separator + "test", converter.convert("test/test/test"));
        assertEquals("test", converter.convert("test"));
    }

}
