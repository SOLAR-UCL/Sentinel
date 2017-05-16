package br.ufpr.inf.gres.sentinel.main.cli.converter;

import com.beust.jcommander.IStringConverter;
import com.google.common.base.CharMatcher;
import java.io.File;

/**
 * @author Giovani Guizzo
 */
public class SeparatorConverter implements IStringConverter<String> {

    @Override
    public String convert(String value) {
        return CharMatcher.anyOf("\\/").replaceFrom(value, File.separator);
    }
}
